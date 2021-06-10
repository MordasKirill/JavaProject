package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.TablesListImpl;

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

                if (session.getAttribute("limit_orders") == null){
                    session.setAttribute("limit_orders", 0);
                }

                if (session.getAttribute("limit_users") == null){
                    session.setAttribute("limit_users", 0);
                }

                if (session.getAttribute("orders_size") == null){
                    session.setAttribute("orders_size", TablesListImpl.DEFAULT_LIMIT);
                }

                if (session.getAttribute("users_size") == null){
                    session.setAttribute("users_size", TablesListImpl.DEFAULT_LIMIT);
                }

                int limitOrders = (int) session.getAttribute("limit_orders");
                int limitUsers = (int) session.getAttribute("limit_users");

                if (request.getParameter("load_orders") != null) {
                    session.setAttribute("limit_orders", tablesListService.getActualLimit(limitOrders));
                    session.setAttribute("orders_size", tablesListService.getActualLimit(tablesListService.getOrders(limitOrders).size()));
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;
                }

                if (request.getParameter("back_orders") != null) {
                    session.setAttribute("limit_orders", tablesListService.getPreviousLimit(limitOrders));
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;
                }

                if (request.getParameter("load_users") != null) {
                    session.setAttribute("limit_users", tablesListService.getActualLimit(limitUsers));
                    session.setAttribute("users_size", tablesListService.getActualLimit(tablesListService.getUsers(limitUsers).size()));
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;
                }

                if (request.getParameter("back_users") != null) {
                    session.setAttribute("limit_users", tablesListService.getPreviousLimit(limitUsers));
                    response.sendRedirect("Controller?command=gotoadminpage");
                    return;
                }

                int ordersSize = (int) session.getAttribute("orders_size");
                int usersSize = (int) session.getAttribute("users_size");

                boolean resultOrdersNext = ordersSize >= limitOrders;
                boolean resultOrdersBack = limitOrders != 0;
                boolean resultUsersNext = usersSize >= limitUsers + TablesListImpl.DEFAULT_LIMIT;
                boolean resultUsersBack = limitUsers != 0;

                request.setAttribute("resultOrdersNext", resultOrdersNext);
                request.setAttribute("resultOrdersBack", resultOrdersBack);
                request.setAttribute("resultUsersNext", resultUsersNext);
                request.setAttribute("resultUsersBack", resultUsersBack);

                request.setAttribute("orders", tablesListService.getOrders(limitOrders));
                request.setAttribute("users", tablesListService.getUsers(limitUsers));

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
