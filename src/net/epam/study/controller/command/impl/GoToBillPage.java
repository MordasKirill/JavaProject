package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToBillPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddToCart.order.clear();
        AddToCart.total.clear();
        if (!CheckSession.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            request.getSession(true).setAttribute("local", CheckLoginAndPassword.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/billPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
