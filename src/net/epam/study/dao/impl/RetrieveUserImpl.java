package net.epam.study.dao.impl;

import net.epam.study.controller.command.Role;
import net.epam.study.dao.RetrieveUserDAO;

public class RetrieveUserImpl implements RetrieveUserDAO {
    public boolean isAuthenticated(Boolean auth, String role) {

        if (auth == null || !auth || role == null) {
            return false;
        }
        return true;
    }

    public boolean isAdmin(String role) {


        if (role.equalsIgnoreCase(Role.ADMIN.toString()) || role.equalsIgnoreCase(Role.OWNER.toString())) {
            return false;
        }
        return true;
    }

    public boolean isUser(String role) {
        String upperCase = role.toUpperCase();

        if (upperCase.equals(Role.USER.toString())) {
            return false;
        }
        return true;
    }

}
