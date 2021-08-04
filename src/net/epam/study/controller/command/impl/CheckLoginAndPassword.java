package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.CreateTableInfoService;
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

public class CheckLoginAndPassword implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ERROR = "errMsg";
    public static final String ATTR_ERROR_MSG = "local.error.logerr";
    public static final String ATTR_ROLE = "role";

    public static final String PARAM_LOGIN = "login";
    public static final String ATTR_USER_ID = "id";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_LOCALE = "locale";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Login error!";

    private static final Logger log = Logger.getLogger(CheckLoginAndPassword.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider provider = ServiceProvider.getInstance();
        CheckUserService checkUserService = provider.getCheckUserService();
        ValidationService validationService = provider.getValidationService();
        CreateTableInfoService createTableInfo = provider.getCreateTableInfoService();

        String login = request.getParameter(PARAM_LOGIN).trim();
        String password = request.getParameter(PARAM_PASSWORD);
        String role;
        int userId;

        HttpSession session = request.getSession(true);


        try {
            userId = createTableInfo.getUserId(login);
            role = checkUserService.getUserRole(userId);

            if(checkUserService.isUserExists(userId, password)) {

                if (validationService.isAdmin(role)) {
                    session.setAttribute(ATTR_AUTH, true);
                    request.setAttribute(ATTR_ERROR, "");
                    session.setAttribute(ATTR_ROLE, role);
                    session.setAttribute(ATTR_USER_ID, userId);

                    ValidationImpl.userLocale = request.getParameter(PARAM_LOCALE);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute(ATTR_AUTH, true);
                    session.setAttribute(PARAM_LOGIN, login);
                    session.setAttribute(ATTR_USER_ID, userId);
                    request.setAttribute(ATTR_ERROR, "");
                    session.setAttribute(ATTR_ROLE, role);

                    ValidationImpl.userLocale = request.getParameter(PARAM_LOCALE);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN_INDEX);
                    requestDispatcher.forward(request, response);
                }
            }else{

                request.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                session.setAttribute(PARAM_LOGIN, login);
                ValidationImpl.userLocale = request.getParameter(PARAM_LOCALE);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){

            log.log(Level.ERROR,"CheckLoginAndPassword error.", e);
            session.setAttribute(PARAM_ERROR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
