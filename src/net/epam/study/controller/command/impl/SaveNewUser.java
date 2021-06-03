package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.Role;
import net.epam.study.service.CheckNewUserService;
import net.epam.study.service.HashPasswordService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.impl.ValidationImpl;

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
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        HashPasswordService hashPasswordService = serviceProvider.getHashPasswordService();
        CheckNewUserService checkNewUserService = serviceProvider.getCheckNewUserService();

        session.setAttribute("auth", true);
        session.setAttribute("role", role);

        try {

            if(checkNewUserService.check(login, hashPasswordService.hashPassword(password), role)) {
                request.setAttribute("errMsg", "");
                ValidationImpl.userLocale = request.getParameter("locale");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
                requestDispatcher.forward(request, response);

            }else{

                request.setAttribute("errMsg", "User with such login already exist !");
                ValidationImpl.userLocale = request.getParameter("locale");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registrationPage.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){
            session.setAttribute("error", "Save user fail!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
