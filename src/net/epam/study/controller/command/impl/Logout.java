package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.impl.ChangeOrderImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session != null){

            session.removeAttribute("auth");
            session.removeAttribute("role");
            session.removeAttribute("login");

            session.removeAttribute("limit_orders");
            session.removeAttribute("limit_users");
            session.removeAttribute("orders_size");
            session.removeAttribute("users_size");
            session.removeAttribute("category");

            session.removeAttribute("emailSession");
            session.removeAttribute("fullNameSession");
            session.removeAttribute("addressSession");
            session.removeAttribute("phoneSession");
            session.removeAttribute("citySession");

            ChangeOrderImpl.ORDER.clear();
            ChangeOrderImpl.TOTAL.clear();

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
