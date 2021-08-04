package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ChangeDBTableFieldsService;
import net.epam.study.service.RetrieveUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
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
        ChangeDBTableFieldsService changeDBTableFieldsService = serviceProvider.getChangeDBTableFieldsService();
        RetrieveUserService retrieveUserService = serviceProvider.getRetrieveUserService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!retrieveUserService.isAuthenticated((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute("role"))
                || !retrieveUserService.checkUser((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            String id = request.getParameter(PARAM_ID);
            String role = request.getParameter(PARAM_ROLE);

            if (validationService.isParamNotNull(id)) {

                try {
                    changeDBTableFieldsService.changeRole(id, role);
                } catch (ServiceException e){

                    log.log(Level.ERROR,"AdminUserRole error.", e);
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
