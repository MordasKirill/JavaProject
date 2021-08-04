package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Role;
import net.epam.study.service.*;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewUser implements Command {

    public static final String ATTR_PASSWORD = "password";
    public static final String ATTR_LOGIN = "login";

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_LOCALE = "locale";

    public static final String ERR_MSG = "errMsg";
    public static final String ATTR_ERR_REG = "local.error.regerr";
    public static final String ATTR_ERR_USER = "Save user fail!";

    private static final Logger log = Logger.getLogger(SaveNewUser.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(ATTR_LOGIN).trim();
        String password = request.getParameter(ATTR_PASSWORD);
        String role = String.valueOf(Role.USER);
        int userId;

        HttpSession session = request.getSession(true);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        HashPasswordService hashPasswordService = serviceProvider.getHashPasswordService();
        CheckUserService checkUserService = serviceProvider.getCheckUserService();
        CreateTableInfoService createTableInfoService = serviceProvider.getCreateTableInfoService();

        try {
            userId = createTableInfoService.getUserId(login);

            if(checkUserService.isUserUniq(userId, login, hashPasswordService.hashPassword(password), role)) {
                session.setAttribute(ATTR_AUTH, true);
                session.setAttribute(ATTR_ROLE, role);
                session.setAttribute(ATTR_LOGIN, login);
                request.setAttribute(ERR_MSG, "");

                ValidationImpl.userLocale = request.getParameter(ATTR_LOCALE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN_INDEX);
                requestDispatcher.forward(request, response);

            }else{

                request.setAttribute(ERR_MSG, ATTR_ERR_REG);
                ValidationImpl.userLocale = request.getParameter(ATTR_LOCALE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_REGISTRATION);
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){

            log.log(Level.ERROR,"SaveNewUser error.", e);
            session.setAttribute(ERR_MSG, ATTR_ERR_USER);
            session.setAttribute(ATTR_LOGIN, login);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
