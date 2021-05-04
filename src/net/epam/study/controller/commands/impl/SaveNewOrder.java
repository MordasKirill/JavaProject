package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.dao.OrderValidate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.epam.study.controller.commands.impl.GoToBasketPage.getTotal;

public class SaveNewOrder implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String sql = "INSERT INTO orders (fullName,address,email,phone)" +
                "VALUES ('" + fullName + "','" + address + "','" + email + "','" + phone + "')";
        if(!OrderValidate.isValidEmailAddress(email)) {
            request.setAttribute("errMsgEmail", "Email is incorrect");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
        if(!OrderValidate.isValidFullName(fullName)) {
            request.setAttribute("errMsgFullName", "Full name is incorrect!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
        if(!OrderValidate.isValidPhoneNumber(phone)) {
            request.setAttribute("errMsgPhone", "Phone is incorrect!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
        if(!OrderValidate.isValidCity(city)) {
            request.setAttribute("errMsgCity", "Only Minsk required!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
        if (OrderValidate.isValidEmailAddress(email)
                &&OrderValidate.isValidFullName(fullName)
                &&OrderValidate.isValidPhoneNumber(phone)
                &&OrderValidate.isValidCity(city)){
            OrderValidate.validate(sql);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
