package net.epam.study.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CheckSessionDAO {
    boolean checkSession(HttpServletRequest request, HttpServletResponse response);
}
