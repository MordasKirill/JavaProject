package net.epam.study.service.impl;

import net.epam.study.dao.RetrieveUserDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.RetrieveUserService;

public class RetrieveUserImpl implements RetrieveUserService {

    @Override
    public boolean isAuthenticated(Boolean auth, String role) {
        DAOProvider provider = DAOProvider.getInstance();
        RetrieveUserDAO retrieveUserDAO = provider.getRetrieveUserDAO();

        boolean result;
        result = retrieveUserDAO.isAuthenticated(auth, role);
        return result;
    }

    @Override
    public boolean checkAdmin(String role) {
        DAOProvider provider = DAOProvider.getInstance();
        RetrieveUserDAO retrieveUserDAO = provider.getRetrieveUserDAO();

        boolean result;
        result = retrieveUserDAO.isAdmin(role);
        return result;
    }

    @Override
    public boolean checkUser(String role) {
        DAOProvider provider = DAOProvider.getInstance();
        RetrieveUserDAO retrieveUserDAO = provider.getRetrieveUserDAO();

        boolean result;
        result = retrieveUserDAO.isUser(role);
        return result;
    }
}
