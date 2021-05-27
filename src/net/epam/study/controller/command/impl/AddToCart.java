package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.entity.MenuItem;
import net.epam.study.service.CheckSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddToCart implements Command {
    public static List<MenuItem> order = new ArrayList<>();
    public static List<String> total = new ArrayList<>();

    public static void addToList(){
        for (int i = 0; i<order.size(); i++) {
            System.out.println(order.get(i));
        }
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!CheckSession.checkSession(request, response)) {
            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            String time = request.getParameter("time");
            if (name != null && price != null && time != null) {
                order.add(new MenuItem(name, price, time));
                total.add(price);
                addToList();
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menu-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
