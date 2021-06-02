package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.FieldsValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToMenuPage implements Command {
    DAOProvider provider = DAOProvider.getInstance();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    TablesListService tablesListService = serviceProvider.getTablesListService();
    public static String category;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!checkSessionDAO.checkSession(request, response)) {
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
                    FieldsValidationImpl.userLocale = request.getParameter("locale");
                }
                request.getSession(true).setAttribute("local", FieldsValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menuPage.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServiceException e){
                session.setAttribute("error", "Show menu fail!");
                response.sendRedirect("/error.jsp");
            }
        }
    }
}
