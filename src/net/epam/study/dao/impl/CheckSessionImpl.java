package net.epam.study.dao.impl;

import net.epam.study.controller.command.Role;
import net.epam.study.dao.CheckSessionDAO;

public class CheckSessionImpl implements CheckSessionDAO {
    public boolean checkSession(Boolean auth, String role) {

        if (auth == null || !auth || role == null) {
            return false;
        }
        return true;
    }

    public boolean checkAdmin(String role) {
        String upperCase = role.toUpperCase();

        if (upperCase.equals(Role.ADMIN.toString()) || upperCase.equals(Role.OWNER.toString())) {
            return false;
        }
        return true;
    }

    public boolean checkUser(String role) {
        String upperCase = role.toUpperCase();

        if (upperCase.equals(Role.USER.toString())) {
            return false;
        }
        return true;
    }
}
