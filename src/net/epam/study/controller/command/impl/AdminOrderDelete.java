package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.RemoveOrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminOrderDelete implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RemoveOrderService removeOrderService = serviceProvider.getRemoveOrderService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {

            String id = request.getParameter("id");
            if (id != null) {

                try {
                    removeOrderService.delete(id);
                } catch (ServiceException e){
                    session.setAttribute("error", "Delete order error!");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                    requestDispatcher.forward(request, response);
                }

            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
