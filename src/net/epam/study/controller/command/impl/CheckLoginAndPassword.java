package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.Role;
import net.epam.study.dao.impl.CheckUserImpl;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.impl.FieldsValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckLoginAndPassword implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    CheckUserService checkUserService = provider.getCheckUserService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", true);
        try {
            if(checkUserService.validateUser(login, password)
                    &&checkUserService.isAdmin(login)) {
                if (CheckUserImpl.role.equals(String.valueOf(Role.ADMIN))
                        || CheckUserImpl.role.equals(String.valueOf(Role.OWNER))) {
                    request.setAttribute("errMsg", "");
                    session.setAttribute("role", CheckUserImpl.role);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                    FieldsValidationImpl.userLocale = request.getParameter("locale");
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute("errMsg", "");
                    session.setAttribute("role", CheckUserImpl.role);
                    FieldsValidationImpl.userLocale = request.getParameter("locale");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
                    requestDispatcher.forward(request, response);
                }
            }else{
                request.setAttribute("errMsg", "Username or password are incorrect !!!");
                FieldsValidationImpl.userLocale = request.getParameter("locale");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e){
            session.setAttribute("error", "Login error!");
            response.sendRedirect("/error.jsp");
        }
    }
}
