package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.dao.OrderDelete;
import net.epam.study.service.CheckSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminOrderDelete implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!CheckSession.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            String id = request.getParameter("id");
            String sql = "delete from orders where id ='" + id + "'";
            if (id != null) {
                OrderDelete.delete(sql);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
