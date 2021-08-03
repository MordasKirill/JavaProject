package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.RetrieveUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.TablesListImpl;
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

    public static final String ATTR_AUTH = "auth";

    public static final String LIMIT_ORDERS = "limit_orders";
    public static final String LIMIT_USERS = "limit_users";

    public static final String LOAD_USERS = "load_users";
    public static final String BACK_USERS = "back_users";

    public static final String LOAD_ORDERS = "load_orders";
    public static final String BACK_ORDERS = "back_orders";

    public static final String ATTR_ROLE = "role";

    public static final String PARAM_NEXT_ORDERS = "resultOrdersNext";
    public static final String PARAM_BACK_ORDERS = "resultOrdersBack";
    public static final String PARAM_NEXT_USERS = "resultUsersNext";
    public static final String PARAM_BACK_USERS = "resultUsersBack";

    public static final String PARAM_ORDERS = "orders";
    public static final String PARAM_USERS = "users";

    public static final String PARAM_LOCALE = "locale";
    public static final String PARAM_LOCAL = "local";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Show orders fail!";

    private static final Logger log = Logger.getLogger(GoToAdminPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RetrieveUserService retrieveUserService = serviceProvider.getRetrieveUserService();
        TablesListService tablesListService = serviceProvider.getTablesListService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!retrieveUserService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
        || !retrieveUserService.checkUser((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            
            try {

                if (session.getAttribute(LIMIT_ORDERS) == null &&
                        session.getAttribute(LIMIT_USERS) == null){

                    session.setAttribute(LIMIT_ORDERS, 0);
                    session.setAttribute(LIMIT_USERS, 0);
                }


                int limitOrders = (int) session.getAttribute(LIMIT_ORDERS);
                int limitUsers = (int) session.getAttribute(LIMIT_USERS);

                if (validationService.isParamNotNull(request.getParameter(LOAD_ORDERS))) {
                    session.setAttribute(LIMIT_ORDERS, tablesListService.getActualLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }

                if (validationService.isParamNotNull(request.getParameter(BACK_ORDERS))) {
                    session.setAttribute(LIMIT_ORDERS, tablesListService.getPreviousLimit(limitOrders));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }

                if (validationService.isParamNotNull(request.getParameter(LOAD_USERS))) {
                    session.setAttribute(LIMIT_USERS, tablesListService.getActualLimit(limitUsers));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }

                if (validationService.isParamNotNull(request.getParameter(BACK_USERS))) {
                    session.setAttribute(LIMIT_USERS, tablesListService.getPreviousLimit(limitUsers));
                    response.sendRedirect(PagePath.REDIRECT_ADMIN);
                    return;
                }

                int ordersSize = tablesListService.getAllOrders().size();
                int usersSize = tablesListService.getAllUsers().size();

                boolean resultOrdersNext =  ordersSize > limitOrders + TablesListImpl.DEFAULT_LIMIT;
                boolean resultOrdersBack = limitOrders != 0;
                boolean resultUsersNext = usersSize > limitUsers + TablesListImpl.DEFAULT_LIMIT;
                boolean resultUsersBack = limitUsers != 0;

                request.setAttribute(PARAM_NEXT_ORDERS, resultOrdersNext);
                request.setAttribute(PARAM_BACK_ORDERS, resultOrdersBack);
                request.setAttribute(PARAM_NEXT_USERS, resultUsersNext);
                request.setAttribute(PARAM_BACK_USERS, resultUsersBack);

                request.setAttribute(PARAM_ORDERS, tablesListService.getOrders(limitOrders));
                request.setAttribute(PARAM_USERS, tablesListService.getUsers(limitUsers));

                if (request.getParameter(PARAM_LOCALE) != null) {
                    ValidationImpl.userLocale = request.getParameter(PARAM_LOCALE);
                }

                request.getSession(true).setAttribute(PARAM_LOCAL, ValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN);
                requestDispatcher.forward(request, response);

            } catch (ServiceException e){

                log.log(Level.ERROR,"GoToAdminPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
        }
    }
}
