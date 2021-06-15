package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToPaymentPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getParameter("locale")!=null) {
//            ValidationImpl.userLocale = request.getParameter("locale");
//        }
//        request.getSession(true).setAttribute("local", ValidationImpl.userLocale);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute("login");

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))
                || !checkSessionService.checkAdmin((String) session.getAttribute("role"))) {

            response.sendRedirect("Controller?command=gotologinpage");
        } else {

            try {
                request.setAttribute("total", changeOrderService.getTotal(login));

            } catch (ServiceException e){

                session.setAttribute("error", "Get total fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }


            request.getSession(true).setAttribute("local", ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/paymentPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
