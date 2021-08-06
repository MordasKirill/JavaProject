package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
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

public class CartItemDelete implements Command {

    public static final String PARAM_ITEM = "item";

    public static final String PARAM_ERROR = "error";
    public static final String ERROR_MSG = "Delete item fail!";

    private static final Logger log = Logger.getLogger(CartItemDelete.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();

        HttpSession session = request.getSession(true);

        String deleteValue = request.getParameter(PARAM_ITEM);

        try {
            orderService.deleteOrderItem(deleteValue);

        } catch (ServiceException e) {

            log.log(Level.ERROR, "CartItemDelete error.", e);
            session.setAttribute(PARAM_ERROR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET_INDEX);
        requestDispatcher.forward(request, response);
    }
}
