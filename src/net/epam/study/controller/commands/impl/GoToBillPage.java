package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;

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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/billPage.jsp");
            requestDispatcher.forward(request, response);
    }
}
