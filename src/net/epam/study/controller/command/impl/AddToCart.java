package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddToCart implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))
                || !checkSessionService.checkAdmin((String) session.getAttribute("role"))) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {

            String name = request.getParameter("name");
            String price = request.getParameter("price");
            String time = request.getParameter("time");

            session.setAttribute("category", request.getParameter("category"));
            changeOrderService.addToOrder(name, price, time);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menu-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
