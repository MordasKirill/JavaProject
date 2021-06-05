package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.impl.TablesListImpl;
import net.epam.study.service.CheckSessionService;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();
        TablesListService tablesListService = serviceProvider.getTablesListService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))) {

            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            try {
                if (session.getAttribute("limit") == null){
                    session.setAttribute("limit", TablesListImpl.DEFAULT_LIMIT);
                }
                int limit = (int) session.getAttribute("limit");

                if (request.getParameter("load") != null) {
                    session.setAttribute("limit", tablesListService.getActualLimit(limit));
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;

                }

                int size = tablesListService.getOrders(limit).size();
                boolean result = size >= limit;

                request.setAttribute("result", result);
                request.setAttribute("orders", tablesListService.getOrders(limit));

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminPage.jsp");
                requestDispatcher.forward(request, response);

            } catch (ServiceException e){
                session.setAttribute("error", "Show orders fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}
