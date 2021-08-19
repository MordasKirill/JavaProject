package net.epam.study.controller.command.impl.Admin;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.dao.email.EmailException;
import net.epam.study.dao.email.SendEmail;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminChangeOrderStatus implements Command {
    private static final String ATTR_ERROR = "error";
    private static final String MSG_ERROR = "Change status error!";
    private static final String MSG_EMAIL = "Send email error!";

    private static final Logger LOG = Logger.getLogger(AdminChangeOrderStatus.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        ValidationService validationService = serviceProvider.getValidationService();
        HttpSession session = request.getSession(true);
        if (!validationService.isUser((String) session.getAttribute(Constants.ATTR_ROLE))) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        } else {
            User user = (User) session.getAttribute(Constants.PARAM_USER);
            int id = Integer.parseInt(user.getId());
            String status = request.getParameter(Constants.PARAM_STATUS);
            String email = request.getParameter(Constants.PARAM_EMAIL);
            try {
                orderService.changeOrderStatus(status, id);
                SendEmail.sendEmail.send(status, email);
            } catch (ServiceException e) {
                LOG.log(Level.ERROR, "AdminOrderStatus error.", e);
                session.setAttribute(ATTR_ERROR, MSG_ERROR);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            } catch (EmailException e) {
                LOG.log(Level.ERROR, "SendEmail error.", e);
                session.setAttribute(ATTR_ERROR, MSG_EMAIL);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
                requestDispatcher.forward(request, response);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
            requestDispatcher.forward(request, response);
        }
    }
}
