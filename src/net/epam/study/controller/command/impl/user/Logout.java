package net.epam.study.controller.command.impl.user;

import net.epam.study.Constants;
import net.epam.study.OrderProvider;
import net.epam.study.bean.User;
import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class Logout implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            User user = (User) session.getAttribute(Constants.PARAM_USER);
            if (user == null) {
                response.sendRedirect(PagePath.REDIRECT_LOGIN);
            }
            Enumeration<String> enumeration = session.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String attributeName = enumeration.nextElement();
                session.removeAttribute(attributeName);
            }
            OrderProvider.getInstance().getOrder().get(user.getId()).clear();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
        requestDispatcher.forward(request, response);
    }
}
