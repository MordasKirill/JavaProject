package net.epam.study.controller.command.impl.order;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.Order;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
import net.epam.study.service.PaymentService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewOrder implements Command {

    private static final String ATTR_EMAIL = "email";
    private static final String ATTR_FULL_NAME = "fullName";
    private static final String ATTR_ADDRESS = "address";
    private static final String ATTR_PHONE = "phone";
    private static final String ATTR_CITY = "city";
    private static final String ATTR_METHOD = "method";
    private static final String ATTR_METHOD_ONLINE = "online";
    private static final String ATTR_ORDER_ID = "orderID";

    private static final String ATTR_EMAIL_SESSION = "emailSession";
    private static final String ATTR_FULL_NAME_SESSION = "fullNameSession";
    private static final String ATTR_ADDRESS_SESSION = "addressSession";
    private static final String ATTR_PHONE_SESSION = "phoneSession";
    private static final String ATTR_CITY_SESSION = "citySession";
    private static final String ATTR_ERR_MSG_EMAIL = "errMsgEmail";
    private static final String ATTR_ERR_MSG_FULL_NAME = "errMsgFullName";
    private static final String ATTR_ERR_MSG_PHONE = "errMsgPhone";
    private static final String ATTR_ERR_MSG_CITY = "errMsgCity";

    private static final String ATTR_ORDER = "order";
    private static final String ATTR_TOTAL = "total";
    private static final String ATTR_SIZE = "size";

    private static final String STATUS_PROCESSING = "processing";
    private static final String STATUS_UPON_RECEIPT = "uponReceipt";

    private static final String ATTR_ERROR = "error";
    private static final String ATTR_ERROR_MSG = "local.error.orderEmpty";
    private static final String ATTR_ERROR_ORDER_MSG = "Save order fail!";

    private static final Logger log = Logger.getLogger(SaveNewOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        OrderService orderService = serviceProvider.getOrderService();
        PaymentService paymentService = serviceProvider.getPaymentService();
        String email = request.getParameter(ATTR_EMAIL).trim();
        String fullName = request.getParameter(ATTR_FULL_NAME).trim();
        String address = request.getParameter(ATTR_ADDRESS);
        String phone = request.getParameter(ATTR_PHONE).trim();
        String city = request.getParameter(ATTR_CITY).trim();
        String paymentMethod = request.getParameter(ATTR_METHOD);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user == null) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
        try {
            if (validationService.getErrorMsg(email, Constants.EMAIL_PATTERN, Constants.EMAIL_ERROR) == null
                    && validationService.getErrorMsg(fullName, Constants.FULL_NAME_PATTERN, Constants.FULL_NAME_ERROR) == null
                    && validationService.getErrorMsg(phone, Constants.PHONE_PATTERN, Constants.PHONE_ERROR) == null
                    && validationService.cityErrorMsg(city) == null) {
                if (OrderProvider.getInstance().getOrder().get(user.getId()).size() == 0) {
                    request.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                    request.setAttribute(ATTR_ORDER, OrderProvider.getInstance().getOrder().get(user.getId()));
                    request.setAttribute(ATTR_TOTAL, orderService.applyDiscount(orderService.getTotal(user.getId()), orderService.getDiscount(user.getId())));
                    request.setAttribute(ATTR_SIZE, OrderProvider.getInstance().getOrder().size());
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
                    requestDispatcher.forward(request, response);
                } else {
                    int orderId = orderService.createOrder(new Order(fullName, address, email, phone, orderService.orderToString(OrderProvider.getInstance().getOrder(), user.getId())));
                    session.setAttribute(ATTR_ORDER_ID, orderId);
                    if (paymentMethod.equals(ATTR_METHOD_ONLINE)) {
                        paymentService.doPayment(user.getId(), orderId, orderService.applyDiscount(orderService.getTotal(user.getId()), orderService.getDiscount(user.getId())), STATUS_PROCESSING);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_PAYMENT_INDEX);
                        requestDispatcher.forward(request, response);
                    } else {
                        paymentService.doPayment(user.getId(), orderId, orderService.applyDiscount(orderService.getTotal(user.getId()), orderService.getDiscount(user.getId())), STATUS_UPON_RECEIPT);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BILL_INDEX);
                        requestDispatcher.forward(request, response);
                    }
                }
            } else {
                session.setAttribute(ATTR_EMAIL_SESSION, email);
                session.setAttribute(ATTR_FULL_NAME_SESSION, fullName);
                session.setAttribute(ATTR_ADDRESS_SESSION, address);
                session.setAttribute(ATTR_PHONE_SESSION, phone);
                session.setAttribute(ATTR_CITY_SESSION, city);
                request.setAttribute(ATTR_ERR_MSG_EMAIL, validationService.getErrorMsg(email, Constants.EMAIL_PATTERN, Constants.EMAIL_ERROR));
                request.setAttribute(ATTR_ERR_MSG_FULL_NAME, validationService.getErrorMsg(fullName, Constants.FULL_NAME_PATTERN, Constants.FULL_NAME_ERROR));
                request.setAttribute(ATTR_ERR_MSG_PHONE, validationService.getErrorMsg(phone, Constants.PHONE_PATTERN, Constants.PHONE_ERROR));
                request.setAttribute(ATTR_ERR_MSG_CITY, validationService.cityErrorMsg(city));
                request.setAttribute(ATTR_ORDER, OrderProvider.getInstance().getOrder().get(user.getId()));
                request.setAttribute(ATTR_TOTAL, orderService.applyDiscount(orderService.getTotal(user.getId()), orderService.getDiscount(user.getId())));
                request.setAttribute(ATTR_SIZE, OrderProvider.getInstance().getOrder().size());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "SaveNewOrder error.", e);
            session.setAttribute(ATTR_ERROR, ATTR_ERROR_ORDER_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
