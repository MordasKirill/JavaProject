package net.epam.study.controller.command.impl.user;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.UserService;
import net.epam.study.utils.PasswordUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewPassword implements Command {

    public static final String ERR_MSG = "errMsg";
    public static final String ATTR_ERR_USER = "Change user password fail!";
    private static final String SUCCESS_ATR = "successDelete";
    private static final String SUCCESS_MSG = "local.error.sucess";

    private static final Logger log = Logger.getLogger(SaveNewPassword.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter(Constants.PARAM_PASSWORD);
        HttpSession session = request.getSession(true);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        User user = (User) session.getAttribute(Constants.PARAM_USER);
        if (user == null) {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
        try {
            if (password != null) {
                userService.changeUserPassword(PasswordUtils.hashPassword(password), user.getId());
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "SaveNewPassword error.", e);
            session.setAttribute(ERR_MSG, ATTR_ERR_USER);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
        session.setAttribute(SUCCESS_ATR, SUCCESS_MSG);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ACCOUNT_INDEX);
        requestDispatcher.forward(request, response);
    }
}
