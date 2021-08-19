package net.epam.study.controller.command.impl.User;

import net.epam.study.Constants;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToLoginPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter(Constants.PARAM_LOCALE) != null) {
            ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
        }
        request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
        requestDispatcher.forward(request, response);
    }
}