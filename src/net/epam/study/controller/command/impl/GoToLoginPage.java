package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToLoginPage implements Command {
    public static final String PARAM_LOCALE = "locale";

    public static final String LOCAL_ATTR = "local";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter(PARAM_LOCALE)!=null) {
            ValidationImpl.userLocale = request.getParameter(PARAM_LOCALE);
        }
        request.getSession(true).setAttribute(LOCAL_ATTR, ValidationImpl.userLocale);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
        requestDispatcher.forward(request, response);
    }
}