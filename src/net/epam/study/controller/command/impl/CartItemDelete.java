package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartItemDelete implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute("login");

        String deleteValue = request.getParameter("item");

        try {
            changeOrderService.deleteOrderItem(deleteValue, login);

        } catch (ServiceException e){

            session.setAttribute("error", "Delete item fail!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basket-indexPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
