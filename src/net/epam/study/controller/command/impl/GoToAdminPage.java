package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
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

public class GoToAdminPage implements Command {

    private static final String LIMIT_ORDERS = "limit_orders";
    private static final String LIMIT_USERS = "limit_users";

    private static final String LOAD_USERS = "load_users";
    private static final String BACK_USERS = "back_users";

    private static final String LOAD_ORDERS = "load_orders";
    private static final String BACK_ORDERS = "back_orders";

    private static final String PARAM_NEXT_ORDERS = "resultOrdersNext";
    private static final String PARAM_BACK_ORDERS = "resultOrdersBack";
    private static final String PARAM_NEXT_USERS = "resultUsersNext";
    private static final String PARAM_BACK_USERS = "resultUsersBack";

    private static final String PARAM_ORDERS = "orders";
    private static final String PARAM_USERS = "users";

    private static final String PARAM_ERROR = "error";
    private static final String ERROR_MSG = "Show orders fail!";

    private static final Logger log = Logger.getLogger(GoToAdminPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        PaymentService paymentService = serviceProvider.getPaymentService();
        PaginationService paginationService = serviceProvider.getPaginationService();
        UserService userService = serviceProvider.getUserService();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        if (!validationService.isAuthenticated((Boolean) session.getAttribute(Constants.ATTR_AUTH), (String) session.getAttribute(Constants.ATTR_ROLE))
                || !validationService.isUser((String) session.getAttribute(Constants.ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            try {
                if (session.getAttribute(LIMIT_ORDERS) == null &&
                        session.getAttribute(LIMIT_USERS) == null) {
                    session.setAttribute(LIMIT_ORDERS, 0);
                    session.setAttribute(LIMIT_USERS, 0);
                }
                int limitOrders = (int) session.getAttribute(LIMIT_ORDERS);
                int limitUsers = (int) session.getAttribute(LIMIT_USERS);
                if (request.getParameter(LOAD_ORDERS) != null) {
                    session.setAttribute(LIMIT_ORDERS, paginationService.getActualLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }
                if (request.getParameter(BACK_ORDERS) != null) {
                    session.setAttribute(LIMIT_ORDERS, paginationService.getPreviousLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }
                if (request.getParameter(LOAD_USERS) != null) {
                    session.setAttribute(LIMIT_USERS, paginationService.getActualLimit(limitUsers));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }
                if (request.getParameter(BACK_USERS) != null) {
                    session.setAttribute(LIMIT_USERS, paginationService.getPreviousLimit(limitUsers));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }
                int ordersSize = paymentService.getAllOrders().size();
                int usersSize = userService.getAllUsers().size();
                boolean resultOrdersNext = ordersSize > limitOrders + Constants.DEFAULT_LIMIT;
                boolean resultOrdersBack = limitOrders != 0;
                boolean resultUsersNext = usersSize > limitUsers + Constants.DEFAULT_LIMIT;
                boolean resultUsersBack = limitUsers != 0;
                request.setAttribute(PARAM_NEXT_ORDERS, resultOrdersNext);
                request.setAttribute(PARAM_BACK_ORDERS, resultOrdersBack);
                request.setAttribute(PARAM_NEXT_USERS, resultUsersNext);
                request.setAttribute(PARAM_BACK_USERS, resultUsersBack);
                request.setAttribute(PARAM_ORDERS, orderService.getOrders(limitOrders));
                request.setAttribute(PARAM_USERS, userService.getUsers(limitUsers));
                if (request.getParameter(Constants.PARAM_LOCALE) != null) {
                    ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                }
                request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN);
                requestDispatcher.forward(request, response);

            } catch (ServiceException e) {
                log.log(Level.ERROR, "GoToAdminPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
        }
    }
}
