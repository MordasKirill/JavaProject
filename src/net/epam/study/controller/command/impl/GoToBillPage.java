package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToBillPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        ChangeOrderImpl.ORDER.clear();
        ChangeOrderImpl.TOTAL.clear();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))
                || !checkSessionService.checkAdmin((String) session.getAttribute("role"))) {

            response.sendRedirect("Controller?command=gotologinpage");
        } else {
            request.getSession(true).setAttribute("local", ValidationImpl.userLocale);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/billPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
