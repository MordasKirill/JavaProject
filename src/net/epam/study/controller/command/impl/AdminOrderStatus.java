package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.*;
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
    public static final String ATTR_ERROR = "error";
    public static final String MSG_ERROR = "Change status error!";

    private static final Logger log = Logger.getLogger(AdminOrderStatus.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ChangeTableInfoService changeTableInfoService = serviceProvider.getChangeTableInfoService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute("role"))
                || !checkSessionService.checkUser((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            String id = request.getParameter(PARAM_ID);
            String status = request.getParameter(PARAM_STATUS);
            if (id != null) {

                try {
                    changeTableInfoService.changeStatus(id, status);
                } catch (ServiceException e){

                    log.log(Level.ERROR,"AdminOrderStatus error.", e);
                    session.setAttribute(ATTR_ERROR, MSG_ERROR);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                    requestDispatcher.forward(request, response);
                }

            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
