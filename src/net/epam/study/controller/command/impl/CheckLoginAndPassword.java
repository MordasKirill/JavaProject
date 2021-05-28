package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.Role;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.UserLoginValidateDAO;
import net.epam.study.dao.impl.LoginAndPasswordValidateImpl;
import net.epam.study.service.impl.FieldsValidation;

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
        DAOProvider provider = DAOProvider.getInstance();
        UserLoginValidateDAO validateDAO = provider.getLoginAndPasswordValidate();
        if(validateDAO.validate(login, password)
                &&validateDAO.isAdmin(login)) {
            if (LoginAndPasswordValidateImpl.role.equals(String.valueOf(Role.ADMIN))
                    || LoginAndPasswordValidateImpl.role.equals(String.valueOf(Role.OWNER))) {
                request.setAttribute("errMsg", "");
                session.setAttribute("role", LoginAndPasswordValidateImpl.role);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                FieldsValidation.userLocale = request.getParameter("locale");
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute("errMsg", "");
                session.setAttribute("role", LoginAndPasswordValidateImpl.role);
                FieldsValidation.userLocale = request.getParameter("locale");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        }else{
            if (LoginAndPasswordValidateImpl.error != null) {
                session.setAttribute("error", LoginAndPasswordValidateImpl.error);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            request.setAttribute("errMsg", "Username or password are incorrect !!!");
            FieldsValidation.userLocale = request.getParameter("locale");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
