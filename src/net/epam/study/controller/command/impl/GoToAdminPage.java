package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.impl.TablesListImpl;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToAdminPage implements Command {

    DAOProvider provider = DAOProvider.getInstance();
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    TablesListService tablesListService = serviceProvider.getTablesListService();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!checkSessionDAO.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            try {
                if (request.getParameter("load") != null) {
                    TablesListImpl.limit = TablesListImpl.limit + TablesListImpl.defaultLimit;
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;
                }
                int size = tablesListService.getOrders().size();
                boolean result = size >= TablesListImpl.limit;
                request.setAttribute("result", result);
                request.setAttribute("orders", tablesListService.getOrders());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminPage.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServiceException e){
                session.setAttribute("error", "Show orders fail!");
                response.sendRedirect("/error.jsp");
            }
        }
    }
}
