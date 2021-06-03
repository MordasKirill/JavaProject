package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToMenuPage implements Command {
    //todo remove
    public static String category;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TablesListService tablesListService = serviceProvider.getTablesListService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))) {

            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            try {
                if (category == null) {
                    category = request.getParameter("category");
                }
                request.setAttribute("size", ChangeOrderImpl.order.size());
                request.setAttribute("menuItems", tablesListService.getMenu());
                request.setAttribute("category", category);

                category = null;

                if (request.getParameter("locale") != null) {
                    ValidationImpl.userLocale = request.getParameter("locale");
                }

                request.getSession(true).setAttribute("local", ValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menuPage.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServiceException e){
                session.setAttribute("error", "Show menu fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}
