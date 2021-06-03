package net.epam.study.dao.impl;

import net.epam.study.dao.CheckSessionDAO;

public class CheckSessionImpl implements CheckSessionDAO {
    public boolean checkSession(Boolean auth, String role) {

        if (auth == null || !auth || role == null) {
            return false;
        }
        return true;
    }
}
