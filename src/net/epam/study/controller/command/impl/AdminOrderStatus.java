package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.dao.email.EmailException;
import net.epam.study.dao.email.SendEmail;
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

public class AdminOrderStatus implements Command {
    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";

    public static final String PARAM_ID = "id";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_EMAIL = "email";

    public static final String ATTR_ERROR = "error";
    public static final String MSG_ERROR = "Change status error!";
    public static final String MSG_EMAIL = "Send email error!";

    private static final Logger LOG = Logger.getLogger(AdminOrderStatus.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeDBTableFieldsService changeDBTableFieldsService = serviceProvider.getChangeDBTableFieldsService();
        RetrieveUserService retrieveUserService = serviceProvider.getRetrieveUserService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!retrieveUserService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute("role"))
                || !retrieveUserService.checkUser((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            String id = request.getParameter(PARAM_ID);
            String status = request.getParameter(PARAM_STATUS);
            String email = request.getParameter(PARAM_EMAIL);

            if (validationService.isParamNotNull(id)) {

                try {
                    changeDBTableFieldsService.changeStatus(id, status);
                    SendEmail.sendEmail.send(status, email);

                } catch (ServiceException e){

                    LOG.log(Level.ERROR,"AdminOrderStatus error.", e);
                    session.setAttribute(ATTR_ERROR, MSG_ERROR);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                } catch (EmailException e) {

                    LOG.log(Level.ERROR,"SendEmail error.", e);
                    session.setAttribute(ATTR_ERROR, MSG_EMAIL);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }

            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
