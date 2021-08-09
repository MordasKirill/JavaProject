package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
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
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        int userId = 0;
        if (session.getAttribute(Constants.PARAM_ID) != null) {
            userId = (int) session.getAttribute(Constants.PARAM_ID);
        }
        OrderProvider.getInstance().getOrder().get(userId).clear();
        if (!validationService.isAuthenticated((Boolean) session.getAttribute(Constants.ATTR_AUTH), (String) session.getAttribute(Constants.ATTR_ROLE))
                || !validationService.isAdmin((String) session.getAttribute(Constants.ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BILL);
            requestDispatcher.forward(request, response);
        }
    }
}
