package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.dao.RemoveOrderElement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDelete implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deleteValue = request.getParameter("item");
        RemoveOrderElement.delete(deleteValue);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basket-indexPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
