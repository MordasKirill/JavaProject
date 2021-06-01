package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.FieldsValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToBillPage implements Command {
    DAOProvider provider = DAOProvider.getInstance();
    CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChangeOrderImpl.order.clear();
        ChangeOrderImpl.total.clear();
        if (!checkSessionDAO.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            request.getSession(true).setAttribute("local", FieldsValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/billPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
