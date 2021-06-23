package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToMenuPage implements Command {

    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_CATEGORY = "category";
    public static final String ATTR_SIZE = "size";
    public static final String ATTR_MENU_ITEMS = "menuItems";
    public static final String ATTR_LOCAL = "local";
    public static final String ATTR_LOCALE = "locale";

    public static final String ATTR_ERROR = "error";
    public static final String ATTR_ERROR_MSG = "Show menu fail!";

    private static final Logger log = Logger.getLogger(GoToMenuPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TablesListService tablesListService = serviceProvider.getTablesListService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !checkSessionService.checkAdmin((String) session.getAttribute(ATTR_ROLE))) {

            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            try {

                if (request.getParameter(ATTR_CATEGORY)!=null
                        &&request.getParameter(ATTR_CATEGORY)!=session.getAttribute(ATTR_CATEGORY)){
                    session.setAttribute(ATTR_CATEGORY, request.getParameter(ATTR_CATEGORY));
                }
                request.setAttribute(ATTR_SIZE, ChangeOrderImpl.ORDER.size());
                request.setAttribute(ATTR_MENU_ITEMS, tablesListService.getMenu());

                if (request.getParameter(ATTR_LOCALE) != null) {
                    ValidationImpl.userLocale = request.getParameter(ATTR_LOCALE);
                }

                request.getSession(true).setAttribute(ATTR_LOCAL, ValidationImpl.userLocale);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MENU);
                requestDispatcher.forward(request, response);

            } catch (ServiceException e){

                log.log(Level.ERROR,"GoToMenuPage error.", e);
                session.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
        }
    }
}
