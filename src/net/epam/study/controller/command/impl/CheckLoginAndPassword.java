package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckLoginAndPassword implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider provider = ServiceProvider.getInstance();
        CheckUserService checkUserService = provider.getCheckUserService();
        ValidationService validationService = provider.getValidationService();

        String login = request.getParameter("login").trim();
        String password = request.getParameter("password");
        String role;

        HttpSession session = request.getSession(true);


        try {
            role = checkUserService.getUserRole(login);

            if(checkUserService.isUser(login, password)) {

                if (validationService.isAdmin(role)) {
                    session.setAttribute("auth", true);
                    request.setAttribute("errMsg", "");
                    session.setAttribute("role", role);

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                    ValidationImpl.userLocale = request.getParameter("locale");
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute("auth", true);
                    session.setAttribute("login", login);
                    request.setAttribute("errMsg", "");
                    session.setAttribute("role", role);

                    ValidationImpl.userLocale = request.getParameter("locale");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
                    requestDispatcher.forward(request, response);
                }
            }else{

                request.setAttribute("errMsg", "local.error.logerr");
                session.setAttribute("login", login);
                ValidationImpl.userLocale = request.getParameter("locale");

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){
            session.setAttribute("error", "Login error!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
