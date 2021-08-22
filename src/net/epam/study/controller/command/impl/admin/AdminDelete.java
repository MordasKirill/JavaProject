package net.epam.study.controller.command.impl.admin;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.UserService;
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
    private static final String ATTR_ERROR = "error";
    private static final String MSG_ERROR = "Delete order error!";
    /**
     * Logger to get error log
     */
    private static final Logger LOG = Logger.getLogger(AdminDelete.class);

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
        UserService userService = serviceProvider.getUserService();
        OrderService orderService = serviceProvider.getOrderService();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user != null || validationService.isUser(user.getRole())) {
            String idOrder = request.getParameter(Constants.ID_ORDER);
            String idUser = request.getParameter(Constants.ID_USER);
            try {
                if (idOrder != null) {
                    orderService.deleteOrder(idOrder);
                }
                if (idUser != null) {
                    userService.deleteUser(Integer.parseInt(idUser));
                }
            } catch (ServiceException e) {
                LOG.log(Level.ERROR, "Admin delete action error.", e);
                session.setAttribute(ATTR_ERROR, MSG_ERROR);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
    }
}
