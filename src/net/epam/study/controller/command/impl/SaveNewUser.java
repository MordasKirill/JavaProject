package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.MenuItem;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Role;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.UserService;
import net.epam.study.service.validation.impl.ValidationImpl;
import net.epam.study.utils.PasswordUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

public class SaveNewUser implements Command {

    public static final String ERR_MSG = "errMsg";
    public static final String ATTR_ERR_REG = "local.error.regerr";
    public static final String ATTR_ERR_USER = "Save user fail!";

    private static final Logger log = Logger.getLogger(SaveNewUser.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(Constants.PARAM_LOGIN).trim();
        String password = request.getParameter(Constants.PARAM_PASSWORD);
        String role = String.valueOf(Role.USER);
        int userId;

        HttpSession session = request.getSession(true);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {

            if (userService.isUserUnique(login)) {
                userId = userService.createNewUser(login, PasswordUtils.hashPassword(password), role);
                LinkedList<MenuItem> menuItems = new LinkedList<>();
                OrderProvider.getInstance().getOrder().put(userId, menuItems);
                session.setAttribute(Constants.ATTR_AUTH, true);
                session.setAttribute(Constants.ATTR_ROLE, role);
                session.setAttribute(Constants.PARAM_LOGIN, login);
                session.setAttribute(Constants.PARAM_ID, userId);
                request.setAttribute(ERR_MSG, "");
                ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN_INDEX);
                requestDispatcher.forward(request, response);

            } else {
                request.setAttribute(ERR_MSG, ATTR_ERR_REG);
                ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_REGISTRATION);
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e) {
            log.log(Level.ERROR, "SaveNewUser error.", e);
            session.setAttribute(ERR_MSG, ATTR_ERR_USER);
            session.setAttribute(Constants.PARAM_LOGIN, login);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
