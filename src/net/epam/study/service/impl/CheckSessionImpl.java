package net.epam.study.service.impl;

import net.epam.study.dao.CheckSessionDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.CheckSessionService;

public class CheckSessionImpl implements CheckSessionService {

    @Override
    public boolean checkSession(Boolean auth, String role) {
        DAOProvider provider = DAOProvider.getInstance();
        CheckSessionDAO checkSessionDAO = provider.getCheckSessionDAO();

        boolean result;
        result = checkSessionDAO.checkSession(auth, role);
        return result;
    }
}
