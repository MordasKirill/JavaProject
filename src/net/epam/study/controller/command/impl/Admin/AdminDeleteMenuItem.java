package net.epam.study.controller.command.impl.Admin;

import net.epam.study.Constants;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.MenuService;
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
    private static final String SUCCESS_ATR = "successDelete";
    private static final String NOTFOUND_ATTR = "notFound";
    private static final String ERROR_ATTR = "error";
    private static final String ERROR_MSG = "Remove menu item fail!";
    private static final String NOTFOUND_MSG = "local.error.notfound";
    private static final String SUCCESS_MSG = "local.error.sucess";

    private static final Logger LOG = Logger.getLogger(AdminDeleteMenuItem.class);

    /**
     * @param request  stores information about the request
     * @param response manages the response to the request
     * @throws ServletException servlet exceptions
     * @throws IOException      exceptions produced by failed or
     *                          interrupted I/O operations.
     *                          <p>
     *                          In case incorrect role user will be redirected to loginPage
     *                          to prevent security breach.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MenuService menuService = serviceProvider.getMenuService();
        String itemName = request.getParameter(Constants.ITEM_NAME_DELETE);
        String category = request.getParameter(Constants.CATEGORY_DELETE);
        HttpSession session = request.getSession(true);
        try {
            if (menuService.isMenuItemExists(itemName, category)) {
                menuService.deleteMenuItem(itemName, category);
                session.setAttribute(SUCCESS_ATR, SUCCESS_MSG);
                session.removeAttribute(NOTFOUND_ATTR);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);
            } else {
                session.removeAttribute(SUCCESS_ATR);
                session.setAttribute(NOTFOUND_ATTR, NOTFOUND_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "AdminDeleteMenuItem error.", e);
            session.setAttribute(ERROR_ATTR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
