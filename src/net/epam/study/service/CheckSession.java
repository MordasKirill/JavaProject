package net.epam.study.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckSession {
    public static boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        if(session == null) {
            return false;
        }
        Boolean isAuth = (Boolean) session.getAttribute("auth");
        String role = (String) session.getAttribute("role");
        if (isAuth == null || !isAuth || role == null) {
            return false;
        }
        return true;
    }
}
