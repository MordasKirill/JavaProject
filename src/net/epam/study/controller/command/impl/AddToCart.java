package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCart implements Command {
    DAOProvider provider = DAOProvider.getInstance();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkSessionDAO.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            String time = request.getParameter("time");
            GoToMenuPage.category = request.getParameter("category");
            request.setAttribute("category", GoToMenuPage.category);
            changeOrderService.addToOrder(name, price, time);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menu-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
