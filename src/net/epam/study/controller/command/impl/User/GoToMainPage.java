package net.epam.study.controller.command.impl.user;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.User;
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

public class GoToMainPage implements Command {

    private static final String ATTR_LOGIN_STRANGER = "stranger";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        if (session.getAttribute(Constants.PARAM_LOGIN) == null) {
            session.setAttribute(Constants.PARAM_LOGIN, ATTR_LOGIN_STRANGER);
        }
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (session.getAttribute(Constants.PARAM_USER) == null) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
        int userId = user.getId();

        if (!validationService.isAdmin(user.getRole())) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);

        } else {
            OrderProvider.getInstance().getOrder().get(userId).clear();
            request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MAIN);
            requestDispatcher.forward(request, response);
        }
    }

}
