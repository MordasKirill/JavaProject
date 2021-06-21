package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_LOCAL = "local";
    public static final String ATTR_LOCALE = "locale";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(ATTR_LOCALE)!=null) {
            ValidationImpl.userLocale = request.getParameter(ATTR_LOCALE);
        }
        request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_REGISTRATION);
        requestDispatcher.forward(request, response);
    }
}
