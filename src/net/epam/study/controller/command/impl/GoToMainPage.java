package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.RetrieveUserService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ManageOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToMainPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_LOGIN = "login";
    public static final String ATTR_LOGIN_STRANGER = "stranger";
    public static final String ATTR_LOCAL = "local";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RetrieveUserService retrieveUserService = serviceProvider.getRetrieveUserService();

        ManageOrderImpl.ORDER.clear();
        ManageOrderImpl.TOTAL.clear();

        HttpSession session = request.getSession(true);

        if (session.getAttribute(ATTR_LOGIN) == null) {
            session.setAttribute(ATTR_LOGIN, ATTR_LOGIN_STRANGER);
        }

        if (!retrieveUserService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !retrieveUserService.checkAdmin((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN);
            requestDispatcher.forward(request, response);
        }
    }
}
