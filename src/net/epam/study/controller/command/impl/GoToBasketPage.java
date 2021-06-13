package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.*;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToBasketPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();
        ChangeTableInfoService changeTableInfoService = serviceProvider.getChangeTableInfoService();
        TablesListService tablesListService = serviceProvider.getTablesListService();

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute("login");

        if (!checkSessionService.checkSession((Boolean) session.getAttribute("auth"), (String) session.getAttribute("role"))) {

            response.sendRedirect("Controller?command=gotologinpage");
        } else {

            if (request.getParameter("payment") != null){

                try {

                    changeTableInfoService.changePaymentStatus("rejected", login);

                } catch (ServiceException e){

                    session.setAttribute("error", "Payment status change fail!");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                    requestDispatcher.forward(request, response);
                }
            }

            request.setAttribute("order", ChangeOrderImpl.ORDER);

            try {
                request.setAttribute("total", changeOrderService.getTotal(login));
                session.setAttribute("ordersAmount", tablesListService.getDonePayments(login));

                if (tablesListService.getDonePayments(login) >= 3){
                    session.setAttribute("discount", 3);
                }  else{
                    session.setAttribute("discount", 0);
                }
                if (tablesListService.getDonePayments(login) >= 10){
                    session.setAttribute("discount", 10);
                }


            } catch (ServiceException e){

                session.setAttribute("error", "Get total fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }
            request.setAttribute("size", ChangeOrderImpl.ORDER.size());

            request.getSession(true).setAttribute("local", ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
