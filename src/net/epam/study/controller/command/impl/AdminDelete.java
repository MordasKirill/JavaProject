package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.DeleteTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminDelete implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        DeleteTableInfoService deleteTableInfoService = serviceProvider.getDeleteTableInfoService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))
                || !checkSessionService.checkUser((String) session.getAttribute("role"))) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {

            String idOrder = request.getParameter("idOrder");
            String idUser = request.getParameter("idUser");

            if (idOrder != null) {

                try {
                    deleteTableInfoService.deleteOrder(idOrder);
                } catch (ServiceException e){
                    session.setAttribute("error", "Delete order error!");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                    requestDispatcher.forward(request, response);
                }

            }

            if (idUser != null) {

                try {
                    deleteTableInfoService.deleteUser(idUser);
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
