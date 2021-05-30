package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.ShowTablesDAO;
import net.epam.study.dao.impl.ShowTablesImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToAdminPage implements Command {
    DAOProvider provider = DAOProvider.getInstance();
    ShowTablesDAO showTablesDAO = provider.getShowTablesDAO();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!checkSessionDAO.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            if (ShowTablesImpl.error != null) {
                session.setAttribute("error", ShowTablesImpl.error);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            if (request.getParameter("load")!=null){
                ShowTablesImpl.limit = ShowTablesImpl.limit + ShowTablesImpl.defaultLimit;
                response.sendRedirect("Controller?command=gotoadminpage");
                return;
            }
            int size = showTablesDAO.getOrders().size();
            boolean result = size>ShowTablesImpl.limit;
            request.setAttribute("result", result);
            request.setAttribute("orders", showTablesDAO.getOrders());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
