package net.epam.study.controller.command.impl.user;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.MenuItem;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Status;
import net.epam.study.service.OrderService;
import net.epam.study.service.PaymentService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

public class GoToBasketPage implements Command {

    private static final String ATTR_DISCOUNT = "discount";
    private static final String ATTR_ORDER = "order";
    private static final String ATTR_TOTAL = "total";
    private static final String ATTR_ORDERS_AMOUNT = "ordersAmount";
    private static final String ATTR_SIZE = "size";
    private static final String ATTR_ORDER_ID = "orderID";

    private static final String PARAM_PAYMENT = "payment";

    private static final String PARAM_ERROR = "error";
    private static final String ERROR_MSG = "Get total fail!";
    private static final String ERROR_MSG_PAYMENT = "Payment status change fail!";

    private static final Logger log = Logger.getLogger(GoToBasketPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        PaymentService paymentService = serviceProvider.getPaymentService();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user != null || validationService.isAdmin(user.getRole())) {
            int orderId = 0;
            if (session.getAttribute(ATTR_ORDER_ID) != null) {
                orderId = (int) session.getAttribute(ATTR_ORDER_ID);
            }
            if (request.getParameter(PARAM_PAYMENT) != null) {
                try {
                    paymentService.changeOrderStatus(Status.REJECTED.toString().toLowerCase(), orderId);
                } catch (ServiceException e) {
                    session.setAttribute(PARAM_ERROR, ERROR_MSG_PAYMENT);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }
            }
            LinkedList<MenuItem> linkedList = new LinkedList<>();
            request.setAttribute(ATTR_ORDER, linkedList);
            if (OrderProvider.getInstance().getOrder().size() != 0) {
                linkedList = OrderProvider.getInstance().getOrder().get(user.getId());
                request.setAttribute(ATTR_ORDER, linkedList);
            }
            try {
                request.setAttribute(ATTR_TOTAL, orderService.applyDiscount(orderService.getTotal(user.getId()), user.getId()));
                session.setAttribute(ATTR_ORDERS_AMOUNT, paymentService.getDonePayments(user.getId()));
                session.setAttribute(ATTR_DISCOUNT, orderService.getDiscount(user.getId()));
            } catch (ServiceException e) {

                log.log(Level.ERROR, "GoToBasketPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
            request.setAttribute(ATTR_SIZE, linkedList.size());
            request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
    }
}
