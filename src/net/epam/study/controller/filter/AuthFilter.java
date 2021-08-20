package net.epam.study.controller.filter;

import net.epam.study.controller.command.CommandName;
import net.epam.study.controller.command.PagePath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        String name = request.getParameter("command");
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        boolean isLoggingPathValid = name.equalsIgnoreCase(CommandName.GOTOLOGINPAGE.name())
                || name.equalsIgnoreCase(CommandName.CHECKLOGINANDPASSWORD.name())
                || name.equalsIgnoreCase(CommandName.SAVENEWUSER.name())
                || name.equalsIgnoreCase(CommandName.REGISTRATION.name());
        if (isLoggedIn || isLoggingPathValid) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(PagePath.REDIRECT_LOGIN);
        }
    }

    @Override
    public void destroy() {

    }
}
