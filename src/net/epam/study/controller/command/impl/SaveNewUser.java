package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.Role;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.dao.impl.NewUserValidateImpl;
import net.epam.study.service.HashPasswordService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.FieldsValidationImpl;

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
        DAOProvider provider = DAOProvider.getInstance();
        NewUserValidateDAO newUserValidateDAO = provider.getNewUserValidateDAO();
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        HashPasswordService hashPasswordService = serviceProvider.getHashPasswordService();
        session.setAttribute("auth", true);
        session.setAttribute("role", role);
        if(newUserValidateDAO.validate(login, hashPasswordService.hashPassword(password), role)) {
            request.setAttribute("errMsg", "");
            FieldsValidationImpl.userLocale = request.getParameter("locale");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
            requestDispatcher.forward(request, response);
        }else{
            if (NewUserValidateImpl.error != null) {
                session.setAttribute("error", NewUserValidateImpl.error);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            request.setAttribute("errMsg", "User with such login already exist !");
            FieldsValidationImpl.userLocale = request.getParameter("locale");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registrationPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
