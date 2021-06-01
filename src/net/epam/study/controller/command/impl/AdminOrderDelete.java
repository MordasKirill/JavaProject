package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.impl.DeleteOrderImpl;
import net.epam.study.service.RemoveOrderService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminOrderDelete implements Command {
    DAOProvider provider = DAOProvider.getInstance();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    RemoveOrderService removeOrderService = serviceProvider.getRemoveOrderService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!checkSessionDAO.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            if (DeleteOrderImpl.error != null) {
                session.setAttribute("error", DeleteOrderImpl.error);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            String id = request.getParameter("id");
            if (id != null) {
                removeOrderService.delete(id);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
