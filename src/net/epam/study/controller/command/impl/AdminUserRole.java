package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.UserService;
import net.epam.study.service.validation.ValidationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminUserRole implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";

    public static final String PARAM_ID = "id";
    public static final String PARAM_ROLE = "role";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Change role error!";

    private static final Logger log = Logger.getLogger(AdminUserRole.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!validationService.isAuthenticated((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute("role"))
                || !validationService.isUser((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            int id = Integer.parseInt(request.getParameter(PARAM_ID));
            String role = request.getParameter(PARAM_ROLE);

            if (validationService.isParamNotNull(id)) {

                try {
                    userService.changeUserRole(role, id);
                } catch (ServiceException e) {

                    log.log(Level.ERROR, "AdminUserRole error.", e);
                    session.setAttribute(PARAM_ERROR, ERROR_MSG);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }

            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
