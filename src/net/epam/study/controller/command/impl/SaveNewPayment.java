package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
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

    public static final String ATTR_NAME = "name";
    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ERR_MSG = "errMsg";
    public static final String ATTR_CARD_NUMBER = "cardnumber";
    public static final String ATTR_LOGIN = "login";
    public static final String STATUS_DONE = "done";
    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_NUMBER_PAYMENT = "numberPayment";
    public static final String ATTR_FULL_NAME_PAYMENT = "fullNamePayment";
    public static final String ATTR_ERR_PAYMENT = "Payment fail!";
    public static final String ATTR_ERR_TOTAL = "Get total fail!";
    public static final String ERR_MSG_FULL_NAME = "errMsgFullName";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        ChangeTableInfoService changeTableInfoService = serviceProvider.getChangeTableInfoService();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();

        HttpSession session = request.getSession(true);

        String login = (String) session.getAttribute(ATTR_LOGIN);
        String fullName = request.getParameter(ATTR_NAME);
        String number = request.getParameter(ATTR_CARD_NUMBER);

        if (validationService.fullNameErrorMsg(fullName) == null){

            try {

                changeTableInfoService.changePaymentStatus(STATUS_DONE, login);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BILL_INDEX);
                requestDispatcher.forward(request, response);

            } catch (ServiceException e){

                session.setAttribute(ERR_MSG, ATTR_ERR_PAYMENT);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }

        } else {

            request.setAttribute(ERR_MSG_FULL_NAME, validationService.fullNameErrorMsg(fullName));

            try {
                request.setAttribute(ATTR_TOTAL, changeOrderService.getTotal(login));

            } catch (ServiceException e){

                session.setAttribute(ERR_MSG, ATTR_ERR_TOTAL);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }

            session.setAttribute(ATTR_NUMBER_PAYMENT, number);
            session.setAttribute(ATTR_FULL_NAME_PAYMENT, fullName);


            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_PAYMENT);
            requestDispatcher.forward(request, response);
        }
    }
}
