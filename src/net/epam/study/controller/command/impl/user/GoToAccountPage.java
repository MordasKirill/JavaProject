package net.epam.study.controller.command.impl.user;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToAccountPage implements Command {

    private static final String ATTR_ORDERS_AMOUNT = "ordersAmount";
    private static final String PARAM_ERROR = "error";
    private static final String ERROR_MSG = "Get total fail!";
    private static final String PARAM_NEXT_ORDERS = "resultOrdersNext";
    private static final String PARAM_BACK_ORDERS = "resultOrdersBack";
    private static final String LOAD_ORDERS = "load_orders";
    private static final String BACK_ORDERS = "back_orders";
    private static final String LIMIT_ORDERS = "limit_orders";
    private static final String PARAM_ORDERS = "userOrders";

    private static final Logger log = Logger.getLogger(GoToAccountPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        PaymentService paymentService = serviceProvider.getPaymentService();
        PaginationService paginationService = serviceProvider.getPaginationService();
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user != null) {
            try {
                int limitOrders = (int) session.getAttribute(LIMIT_ORDERS);
                if (request.getParameter(LOAD_ORDERS) != null) {
                    session.setAttribute(LIMIT_ORDERS, paginationService.getActualLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ACCOUNT);
                    return;
                }
                if (request.getParameter(BACK_ORDERS) != null) {
                    session.setAttribute(LIMIT_ORDERS, paginationService.getPreviousLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ACCOUNT);
                    return;
                }
                boolean resultOrdersNext = orderService.getOrderDetails(user.getId()).size() > limitOrders + Constants.DEFAULT_LIMIT;
                boolean resultOrdersBack = limitOrders != 0;
                request.setAttribute(PARAM_NEXT_ORDERS, resultOrdersNext);
                request.setAttribute(PARAM_BACK_ORDERS, resultOrdersBack);
                request.setAttribute(PARAM_ORDERS, paymentService.getDetailsForCurrentUser(orderService.getOrderDetails(user.getId()), paymentService.getAllPayments(user.getId(), limitOrders)));
                request.setAttribute(Constants.ATTR_ROLE, user.getRole());
                session.setAttribute(ATTR_ORDERS_AMOUNT, paymentService.getDonePayments(user.getId()));
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ACCOUNT);
                requestDispatcher.forward(request, response);
            } catch (ServiceException e) {
                log.log(Level.ERROR, "GoToAccountPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
    }
}
