package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.MenuItem;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.MenuService;
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
import java.util.LinkedList;

public class GoToMenuPage implements Command {

    private static final String ATTR_SIZE = "size";
    private static final String ATTR_MENU_ITEMS = "menuItems";

    private static final String ATTR_ERROR = "error";
    private static final String ATTR_ERROR_MSG = "Show menu fail!";

    private static final Logger log = Logger.getLogger(GoToMenuPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MenuService menuService = serviceProvider.getMenuService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);
        int userId = 0;
        if (session.getAttribute(Constants.PARAM_ID) != null) {
            userId = (int) session.getAttribute(Constants.PARAM_ID);
        }
        if (!validationService.isAuthenticated((Boolean) session.getAttribute(Constants.ATTR_AUTH), (String) session.getAttribute(Constants.ATTR_ROLE))
                || !validationService.isAdmin((String) session.getAttribute(Constants.ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            try {

                if (request.getParameter(Constants.PARAM_CATEGORY) != null
                        && request.getParameter(Constants.PARAM_CATEGORY) != session.getAttribute(Constants.PARAM_CATEGORY)) {
                    session.setAttribute(Constants.PARAM_CATEGORY, request.getParameter(Constants.PARAM_CATEGORY));
                }
                LinkedList<MenuItem> linkedList;
                linkedList = OrderProvider.getInstance().getOrder().get(userId);
                request.setAttribute(ATTR_SIZE, linkedList.size());
                request.setAttribute(ATTR_MENU_ITEMS, menuService.getMenu());

                if (request.getParameter(Constants.PARAM_LOCALE) != null) {
                    ValidationImpl.userLocale = request.getParameter(Constants.PARAM_LOCALE);
                }

                request.getSession(true).setAttribute(Constants.ATTR_LOCAL, ValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MENU);
                requestDispatcher.forward(request, response);

            } catch (ServiceException e) {

                log.log(Level.ERROR, "GoToMenuPage error.", e);
                session.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
        }
    }
}
