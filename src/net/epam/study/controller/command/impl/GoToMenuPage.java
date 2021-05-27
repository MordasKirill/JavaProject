package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.ShowTablesDAO;
import net.epam.study.service.CheckSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMenuPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOProvider provider = DAOProvider.getInstance();
        ShowTablesDAO showTablesDAO = provider.getShowTablesDAO();
        if (!CheckSession.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            request.setAttribute("size", AddToCart.order.size());
            request.setAttribute("menuItems", showTablesDAO.getMenu());
            request.getSession(true).setAttribute("local", CheckLoginAndPassword.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menuPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
