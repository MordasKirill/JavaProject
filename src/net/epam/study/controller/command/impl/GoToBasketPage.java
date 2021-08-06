package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Status;
import net.epam.study.service.*;
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

public class GoToBasketPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_DISCOUNT = "discount";
    public static final String ATTR_ORDER = "order";
    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_ORDERS_AMOUNT = "ordersAmount";
    public static final String ATTR_SIZE = "size";
    public static final String ATTR_LOCAL = "local";
    public static final String ATTR_USER_ID = "id";
    public static final String ATTR_ORDER_ID = "orderID";

    public static final String PARAM_PAYMENT = "payment";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Get total fail!";
    public static final String ERROR_MSG_PAYMENT = "Payment status change fail!";

    private static final Logger log = Logger.getLogger(GoToBasketPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        PaymentService paymentService = serviceProvider.getPaymentService();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        int userId = 0;
        if (session.getAttribute(ATTR_USER_ID) != null) {
            userId = (int) session.getAttribute(ATTR_USER_ID);
        }
        int orderId = 0;
        if (session.getAttribute(ATTR_ORDER_ID) != null) {
            orderId = (int) session.getAttribute(ATTR_ORDER_ID);
        }
        if (!validationService.isAuthenticated((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !validationService.isAdmin((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            if (request.getParameter(PARAM_PAYMENT) != null) {
                try {
                    paymentService.changeOrderStatus(Status.REJECTED.toString().toLowerCase(), orderId);
                } catch (ServiceException e) {
                    session.setAttribute(PARAM_ERROR, ERROR_MSG_PAYMENT);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }
            }
            request.setAttribute(ATTR_ORDER, Constants.ORDER);
            try {
                request.setAttribute(ATTR_TOTAL, orderService.getTotal(userId));
                session.setAttribute(ATTR_ORDERS_AMOUNT, paymentService.getDonePayments(userId));
                session.setAttribute(ATTR_DISCOUNT, orderService.getDiscount(userId));
            } catch (ServiceException e) {

                log.log(Level.ERROR, "GoToBasketPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
            request.setAttribute(ATTR_SIZE, Constants.ORDER.size());
            request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
            requestDispatcher.forward(request, response);
        }
    }
}
