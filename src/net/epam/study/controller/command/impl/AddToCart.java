package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.CheckSessionService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class represents an addToCart command
 */
public class AddToCart implements Command {
    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";

    public static final String PARAM_NAME = "name";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_TIME = "time";
    public static final String PARAM_CATEGORY = "category";

    /**
     * execute method produces all necessary actions to
     * add item to cart
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
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        CheckSessionService checkSessionService = serviceProvider.getCheckSessionService();

        HttpSession session = request.getSession(true);

        if (!checkSessionService.checkSession((Boolean) session.getAttribute(ATTR_AUTH), (String) session.getAttribute(ATTR_ROLE))
                || !checkSessionService.checkAdmin((String) session.getAttribute(ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            String name = request.getParameter(PARAM_NAME);
            String price = request.getParameter(PARAM_PRICE);
            String time = request.getParameter(PARAM_TIME);

            session.setAttribute(PARAM_CATEGORY, request.getParameter(PARAM_CATEGORY));
            changeOrderService.addToOrder(name, price, time);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MENU_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
