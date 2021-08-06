package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToPaymentPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_USER_ID = "id";

    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_LOCAL = "local";

    public static final String ATTR_ERROR = "error";
    public static final String ATTR_ERROR_MSG = "Get total fail!";

    private static final Logger log = Logger.getLogger(GoToPaymentPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);
        int userId = (int) session.getAttribute(ATTR_USER_ID);

        if (!validationService.isAuthenticated((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !validationService.isAdmin((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            try {
                request.setAttribute(ATTR_TOTAL, orderService.getTotal(userId));

            } catch (ServiceException e) {

                log.log(Level.ERROR, "GoToPaymentPage error.", e);
                session.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }


            request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_PAYMENT);
            requestDispatcher.forward(request, response);
        }
    }
}
