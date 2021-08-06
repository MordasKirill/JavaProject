package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;

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

        if (session != null) {

            session.removeAttribute("auth");
            session.removeAttribute("role");
            session.removeAttribute("login");
            session.removeAttribute("id");
            session.removeAttribute("orderID");

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

            session.removeAttribute("numberPayment");
            session.removeAttribute("fullNamePayment");

            session.removeAttribute("ordersAmount");
            session.removeAttribute("discount");

            session.removeAttribute("success");
            session.removeAttribute("successDelete");
            session.removeAttribute("notFound");
            session.removeAttribute("errMsgPrice");
            session.removeAttribute("errMsgWaitTime");

            Constants.ORDER.clear();
            Constants.TOTAL.clear();

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
        requestDispatcher.forward(request, response);
    }
}
