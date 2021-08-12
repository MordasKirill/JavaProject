package net.epam.study.controller.command.impl;

import net.epam.study.Constants;
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

    private static final String PARAM_ITEM_NAME = "itemName";
    private static final String PARAM_ERROR = "error";
    private static final String ERROR_MSG = "Delete item fail!";
    private static final Logger log = Logger.getLogger(CartItemDelete.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();

        HttpSession session = request.getSession(true);
        String deleteItemName = request.getParameter(PARAM_ITEM_NAME);
        int userId = 0;
        if (session.getAttribute(Constants.PARAM_ID) != null) {
            userId = (int) session.getAttribute(Constants.PARAM_ID);
        }
        try {
            orderService.deleteOrderItem(userId, deleteItemName);
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
