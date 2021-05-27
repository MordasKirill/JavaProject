package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.RemoveOrderElementService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDelete implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RemoveOrderElementService removeOrderElementService = serviceProvider.getRemoveOrderElementService();
        String deleteValue = request.getParameter("item");
        removeOrderElementService.delete(deleteValue);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basket-indexPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
