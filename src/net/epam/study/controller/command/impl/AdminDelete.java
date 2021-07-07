package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.DeleteTableInfoService;
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
/**
 * Class represents an adminDelete command
 * command that provides tools that can
 * help admin to delete user or order.
 */

public class AdminDelete implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";

    public static final String ID_ORDER = "idOrder";
    public static final String ID_USER = "idUser";
    public static final String ATTR_ERROR = "error";
    public static final String MSG_ERROR = "Delete order error!";

    /**
     * Logger to get error log
     */
    private static final Logger LOG = Logger.getLogger(AdminDelete.class);
    /**
     *
     * @param request stores information about the request
     * @param response manages the response to the request
     * @throws ServletException servlet exceptions
     * @throws IOException exceptions produced by failed or
     * interrupted I/O operations.
     *
     * In case incorrect role user will be redirected to loginPage
     * to prevent security breach.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        DeleteTableInfoService deleteTableInfoService = serviceProvider.getDeleteTableInfoService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !checkSessionService.checkUser((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);

        } else {

            String idOrder = request.getParameter(ID_ORDER);
            String idUser = request.getParameter(ID_USER);

            try {

                if (validationService.isIdNull(idOrder)) {
                    deleteTableInfoService.deleteOrder(idOrder);
                }
                if (validationService.isIdNull(idUser)){
                    deleteTableInfoService.deleteUser(idUser);
                }

            } catch (ServiceException e){

                LOG.log(Level.ERROR,"Admin delete action error.", e);
                session.setAttribute(ATTR_ERROR, MSG_ERROR);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
