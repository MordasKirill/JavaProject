package net.epam.study.controller.command.impl;

import net.epam.study.BCrypt.BCrypt;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.Role;
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
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(15));
        String role = String.valueOf(Role.USER);
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", true);
        session.setAttribute("role", role);
        String sql = "INSERT INTO users (login,password,role)" +
                "VALUES ('" + login + "','" + hashPassword + "','" + role + "')";
        if(NewUserValidate.validate(sql, login)) {
            request.setAttribute("errMsg", "");
            CheckLoginAndPassword.userLocale = request.getParameter("locale");
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
            CheckLoginAndPassword.userLocale = request.getParameter("locale");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registrationPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
