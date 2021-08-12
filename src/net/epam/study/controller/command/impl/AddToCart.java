package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
import net.epam.study.bean.MenuItem;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;

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

    /**
     * execute method produces all necessary actions to
     * add item to cart
     *
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
        OrderService orderService = serviceProvider.getOrderService();
        ValidationService validationService = serviceProvider.getValidationService();

        HttpSession session = request.getSession(true);

        if (!validationService.isAuthenticated((Boolean) session.getAttribute(Constants.ATTR_AUTH), (String) session.getAttribute(Constants.ATTR_ROLE))
                || !validationService.isAdmin((String) session.getAttribute(Constants.ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {

            String name = request.getParameter(Constants.PARAM_NAME);
            String price = request.getParameter(Constants.PARAM_PRICE);

            int userId = 0;
            if (session.getAttribute(Constants.PARAM_ID) != null) {
                userId = (int) session.getAttribute(Constants.PARAM_ID);
            }
            session.setAttribute(Constants.PARAM_CATEGORY, request.getParameter(Constants.PARAM_CATEGORY));
            orderService.addToOrder(new MenuItem(name, price), userId);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_MENU_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
