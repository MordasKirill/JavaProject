package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.ChangeTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewPayment implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        ChangeTableInfoService changeTableInfoService = serviceProvider.getChangeTableInfoService();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();

        HttpSession session = request.getSession(true);

        String login = (String) session.getAttribute("login");
        String fullName = request.getParameter("name");
        String number = request.getParameter("cardnumber");

        if (validationService.fullNameErrorMsg(fullName) == null){

            try {
                String status = "done";

                changeTableInfoService.changePaymentStatus(status);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
                requestDispatcher.forward(request, response);

            } catch (ServiceException e){

                session.setAttribute("error", "Payment fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }

        } else {

            request.setAttribute("errMsgFullName", validationService.fullNameErrorMsg(fullName));

            try {
                request.setAttribute("total", changeOrderService.getTotal(login));

            } catch (ServiceException e){

                session.setAttribute("error", "Get total fail!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                requestDispatcher.forward(request, response);
            }

            session.setAttribute("numberPayment", number);
            session.setAttribute("fullNamePayment", fullName);


            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/paymentPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
