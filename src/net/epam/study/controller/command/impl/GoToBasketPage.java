package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToBasketPage implements Command {
    public static double getTotal(){
        double sum =0;
        for (int i=0; i<AddToCart.total.size(); i++){
            sum = sum + Double.parseDouble(AddToCart.total.get(i));
            sum = (double) Math.round(sum * 100) / 100;
        }
        return sum;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!CheckSession.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            request.setAttribute("order", AddToCart.order);
            request.setAttribute("total", getTotal());
            request.setAttribute("size", AddToCart.order.size());
            request.getSession(true).setAttribute("local", CheckLoginAndPassword.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
