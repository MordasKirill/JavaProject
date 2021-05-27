package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.RemoveOrderElementDAO;
import net.epam.study.dao.impl.RemoveOrderElementImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDelete implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOProvider provider = DAOProvider.getInstance();
        RemoveOrderElementDAO removeOrderElementDAO = provider.getRemoveOrderElementDAO();
        String deleteValue = request.getParameter("item");
        removeOrderElementDAO.delete(deleteValue);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basket-indexPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
