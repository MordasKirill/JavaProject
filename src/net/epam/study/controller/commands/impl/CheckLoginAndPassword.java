package net.epam.study.controller.commands.impl;

import net.epam.study.controller.commands.Command;
import net.epam.study.controller.commands.Role;
import net.epam.study.dao.LoginAndPasswordValidate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckLoginAndPassword implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", true);
        if(LoginAndPasswordValidate.validate(login, password)
                &&LoginAndPasswordValidate.isAdmin(login)) {
            if (LoginAndPasswordValidate.role.equals(String.valueOf(Role.ADMIN))) {
                request.setAttribute("errMsg", "");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute("errMsg", "");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        }else{
            request.setAttribute("errMsg", "Username or password are incorrect !!!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
