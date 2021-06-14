package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.DeleteTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminDeleteMenuItem implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        DeleteTableInfoService deleteTableInfoService = serviceProvider.getDeleteTableInfoService();

        String itemName = request.getParameter("itemNameDelete");
        String category = request.getParameter("categoryDelete");
        String notFound = "Item not found";

        String success = "Success!";

        HttpSession session = request.getSession(true);

        try {

            if (deleteTableInfoService.isMenuItemExists(itemName, category)){

                deleteTableInfoService.deleteMenuItem(itemName, category);

                session.setAttribute("successDelete", success);

                session.removeAttribute("notFound");

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                requestDispatcher.forward(request, response);

            }else {
                session.removeAttribute("successDelete");

                session.setAttribute("notFound", notFound);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e){

            session.setAttribute("error", "Remove menu item fail!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
