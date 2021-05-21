package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.controller.commands.Role;
import net.epam.study.dao.NewUserValidate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewUser implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String role = String.valueOf(Role.USER);
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", true);
        session.setAttribute("role", role);
        String sql = "INSERT INTO users (login,password,role)" +
                "VALUES ('" + login + "','" + password + "','" + role + "')";
        if(NewUserValidate.validate(sql, login)) {
            request.setAttribute("errMsg", "");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }else{
            if (NewUserValidate.error != null) {
                session.setAttribute("error", NewUserValidate.error);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            request.setAttribute("errMsg", "User with such login already exist !");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registrationPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
