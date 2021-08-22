package net.epam.study.controller.command.impl.order;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartItemDelete implements Command {

    private static final String PARAM_ITEM_NAME = "itemName";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession(true);
        String deleteItemName = request.getParameter(PARAM_ITEM_NAME);
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user == null) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
        orderService.deleteOrderItem(user.getId(), deleteItemName);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET_INDEX);
        requestDispatcher.forward(request, response);
    }
}
