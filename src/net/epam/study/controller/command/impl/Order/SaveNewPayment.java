package net.epam.study.controller.command.impl.Order;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Status;
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

public class SaveNewPayment implements Command {

    private static final String ATTR_CARD_NUMBER = "cardnumber";
    private static final String ATTR_ORDER_ID = "orderID";
    private static final String ATTR_TOTAL = "total";

    private static final String ATTR_NUMBER_PAYMENT = "numberPayment";
    private static final String ATTR_FULL_NAME_PAYMENT = "fullNamePayment";

    private static final String ERR_MSG = "errMsg";
    private static final String ATTR_ERR_PAYMENT = "Payment fail!";
    private static final String ERR_MSG_PRICE = "errMsgNullPrice";
    private static final String ERR_MSG_FULL_NAME = "errMsgFullName";

    private static final Logger log = Logger.getLogger(SaveNewPayment.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        PaymentService paymentService = serviceProvider.getPaymentService();
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession(true);
        int userId = 0;
        if (session.getAttribute(Constants.PARAM_USER) != null) {
            User user = (User) session.getAttribute(Constants.PARAM_USER);
            userId = Integer.parseInt(user.getId());
        }
        int orderId = 0;
        if (session.getAttribute(ATTR_ORDER_ID) != null) {
            orderId = (int) session.getAttribute(ATTR_ORDER_ID);
        }
        String fullName = request.getParameter(Constants.PARAM_NAME);
        String number = request.getParameter(ATTR_CARD_NUMBER);
            try {
                if (validationService.fullNameErrorMsg(fullName) == null &&
                        orderService.getTotal(userId) != null) {

                    paymentService.changeOrderStatus(Status.DONE.toString().toLowerCase(), orderId);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BILL_INDEX);
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute(ERR_MSG_FULL_NAME, validationService.fullNameErrorMsg(fullName));
                    request.setAttribute(ERR_MSG_PRICE, validationService.priceErrorMsg(orderService.applyDiscount(orderService.getTotal(userId), userId)));
                    request.setAttribute(ATTR_TOTAL, orderService.applyDiscount(orderService.getTotal(userId), orderService.getDiscount(userId)));
                    session.setAttribute(ATTR_NUMBER_PAYMENT, number);
                    session.setAttribute(ATTR_FULL_NAME_PAYMENT, fullName);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_PAYMENT);
                    requestDispatcher.forward(request, response);
                }
            } catch (ServiceException e) {
                log.log(Level.ERROR, "changePaymentStatus error.", e);
                session.setAttribute(ERR_MSG, ATTR_ERR_PAYMENT);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
    }
}


