package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.dao.NewUserValidate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveNewUser implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String sql = "INSERT INTO users (login,password)" +
                "VALUES ('" + login + "','" + password + "')";
        if(NewUserValidate.validate(sql, login)) {
            request.setAttribute("errMsg", "");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }else{
            request.setAttribute("errMsg", "User with such login already exist !");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registrationPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
