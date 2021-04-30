package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMainPage implements Command {
    public static String userLogin;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userLogin != null) {
            request.setAttribute("login", userLogin);
        } else {
            request.setAttribute("login", "stranger");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
