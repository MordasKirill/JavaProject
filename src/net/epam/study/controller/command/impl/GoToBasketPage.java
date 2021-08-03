package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.controller.command.Status;
import net.epam.study.service.*;
import net.epam.study.service.impl.ManageOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToBasketPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_LOGIN = "login";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_DISCOUNT = "discount";
    public static final String ATTR_ORDER = "order";
    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_ORDERS_AMOUNT = "ordersAmount";
    public static final String ATTR_SIZE = "size";
    public static final String ATTR_LOCAL = "local";

    public static final String PARAM_PAYMENT = "payment";
    public static final String PARAM_REJECTED = "rejected";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Get total fail!";
    public static final String ERROR_MSG_PAYMENT = "Payment status change fail!";

    private static final Logger log = Logger.getLogger(GoToBasketPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        RetrieveUserService retrieveUserService = serviceProvider.getRetrieveUserService();
        ChangeDBTableFieldsService changeDBTableFieldsService = serviceProvider.getChangeDBTableFieldsService();
        TablesListService tablesListService = serviceProvider.getTablesListService();

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute(ATTR_LOGIN);

        if (!retrieveUserService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !retrieveUserService.checkAdmin((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            if (request.getParameter(PARAM_PAYMENT) != null){

                try {

                    changeDBTableFieldsService.changePaymentStatus(Status.REJECTED.toString().toLowerCase(), login);

                } catch (ServiceException e){

                    session.setAttribute(PARAM_ERROR, ERROR_MSG_PAYMENT);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }
            }

            request.setAttribute(ATTR_ORDER, ManageOrderImpl.ORDER);

            try {
                request.setAttribute(ATTR_TOTAL, changeOrderService.getTotal(login));
                session.setAttribute(ATTR_ORDERS_AMOUNT, tablesListService.getDonePayments(login));

                if (tablesListService.getDonePayments(login) >= 3){
                    session.setAttribute(ATTR_DISCOUNT, 3);
                }  else{
                    session.setAttribute(ATTR_DISCOUNT, 0);
                }
                if (tablesListService.getDonePayments(login) >= 10){
                    session.setAttribute(ATTR_DISCOUNT, 10);
                }


            } catch (ServiceException e){

                log.log(Level.ERROR,"GoToBasketPage error.", e);
                session.setAttribute(PARAM_ERROR, ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
            request.setAttribute(ATTR_SIZE, ManageOrderImpl.ORDER.size());

            request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
            requestDispatcher.forward(request, response);
        }
    }
}
