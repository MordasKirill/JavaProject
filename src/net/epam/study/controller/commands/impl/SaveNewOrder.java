package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.dao.OrderValidate;
import net.epam.study.service.FieldsValidation;

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

        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0; i<AddToCart.order.size(); i++){
            stringBuilder.append(AddToCart.order.get(i).toString()).append(" ");
        }
        String sql = "INSERT INTO orders (fullName,address,email,phone,details)" +
                "VALUES ('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "')";
        if(!FieldsValidation.isValidEmailAddress(email)) {
            request.setAttribute("errMsgEmail", "Email is incorrect");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
        }
        if(!FieldsValidation.isValidFullName(fullName)) {
            request.setAttribute("errMsgFullName", "Full name is incorrect!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
        }
        if(!FieldsValidation.isValidPhoneNumber(phone)) {
            request.setAttribute("errMsgPhone", "Phone is incorrect!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
        }
        if(!FieldsValidation.isValidCity(city)) {
            request.setAttribute("errMsgCity", "Only Minsk required!");
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
        }
        if (FieldsValidation.isValidEmailAddress(email)
                &&FieldsValidation.isValidFullName(fullName)
                &&FieldsValidation.isValidPhoneNumber(phone)
                &&FieldsValidation.isValidCity(city)){
            if (AddToCart.order.size() == 0) {
                request.setAttribute("error", "You cant checkout with empty cart!");
                request.setAttribute("order", AddToCart.order);
                request.setAttribute("total", GoToBasketPage.getTotal());
                request.setAttribute("size", AddToCart.order.size());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
                requestDispatcher.forward(request, response);
            } else {
                OrderValidate.validate(sql);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
