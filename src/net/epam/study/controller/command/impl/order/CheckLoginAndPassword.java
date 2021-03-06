package net.epam.study.controller.command.impl.order;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.MenuItem;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.UserService;
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

public class CheckLoginAndPassword implements Command {

    public static final String ATTR_ERROR = "errMsg";
    public static final String ATTR_ERROR_MSG = "local.error.logerr";
    public static final String USER = "user";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Login error!";

    private static final String LIMIT_ORDERS = "limit_orders";
    private static final String LIMIT_USERS = "limit_users";

    private static final Logger log = Logger.getLogger(CheckLoginAndPassword.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        ValidationService validationService = provider.getValidationService();
        String login = request.getParameter(Constants.PARAM_LOGIN).trim();
        String password = request.getParameter(Constants.PARAM_PASSWORD);
        HttpSession session = request.getSession(true);
        try {
            if (userService.isUserDataCorrect(new User(login, password))) {
                int userId = userService.getUserId(login);
                String role = userService.getUserRole(userId);
                LinkedList<MenuItem> menuItems = new LinkedList<>();
                OrderProvider.getInstance().getOrder().put(userId, menuItems);
                if (!validationService.isAdmin(role)) {
                    session.setAttribute(LIMIT_ORDERS, 0);
                    session.setAttribute(LIMIT_USERS, 0);
                    session.setAttribute(USER, new User(userId, role, true));
                    session.setAttribute(Constants.ATTR_AUTH, true);
                    session.setAttribute(Constants.PARAM_LOGIN, login);
                    request.setAttribute(ATTR_ERROR, "");
                    ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute(LIMIT_ORDERS, 0);
                    session.setAttribute(LIMIT_USERS, 0);
                    session.setAttribute(USER, new User(userId, role, true));
                    session.setAttribute(Constants.ATTR_AUTH, true);
                    session.setAttribute(Constants.PARAM_LOGIN, login);
                    request.setAttribute(ATTR_ERROR, "");
                    ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN_INDEX);
                    requestDispatcher.forward(request, response);
                }
            } else {
                request.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                session.setAttribute(Constants.PARAM_LOGIN, login);
                ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "CheckLoginAndPassword error.", e);
            session.setAttribute(PARAM_ERROR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
