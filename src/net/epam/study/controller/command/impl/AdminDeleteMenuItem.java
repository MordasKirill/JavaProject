package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.DeleteTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class represents an AdminDeleteMenuItem command
 * command that provides tools that can
 * help admin to delete item from menu.
 */

public class AdminDeleteMenuItem implements Command {
    public static final String PARAM_NAME = "itemNameDelete";
    public static final String PARAM_CATEGORY = "categoryDelete";

    public static final String SUCCESS_ATR = "successDelete";
    public static final String NOTFOUND_ATTR = "notFound";
    public static final String ERROR_ATTR = "error";
    public static final String ERROR_MSG = "Remove menu item fail!";
    public static final String NOTFOUND_MSG = "local.error.notfound";
    public static final String SUCCESS_MSG = "local.error.sucess";

    private static final Logger LOG = Logger.getLogger(AdminDeleteMenuItem.class);
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

        String itemName = request.getParameter(PARAM_NAME);
        String category = request.getParameter(PARAM_CATEGORY);


        HttpSession session = request.getSession(true);

        try {

            if (deleteTableInfoService.isMenuItemExists(itemName, category)){

                deleteTableInfoService.deleteMenuItem(itemName, category);

                session.setAttribute(SUCCESS_ATR, SUCCESS_MSG);

                session.removeAttribute(NOTFOUND_MSG);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);

            }else {
                session.removeAttribute(SUCCESS_ATR);

                session.setAttribute(NOTFOUND_ATTR, NOTFOUND_MSG);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e){

            LOG.log(Level.ERROR,"AdminDeleteMenuItem error.", e);
            session.setAttribute(ERROR_ATTR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }

    }
}
