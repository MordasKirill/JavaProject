package net.epam.study.dao.impl;

import net.epam.study.dao.CheckSessionDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckSessionImpl implements CheckSessionDAO {
    public boolean checkSession(HttpServletRequest request, HttpServletResponse response) {

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
