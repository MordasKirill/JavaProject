package net.epam.study.service.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.CheckUserService;

public class CheckUserImpl implements CheckUserService {
    @Override
    public boolean validateUser(String login, String password) {
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        return checkUserDAO.check(login, password);
    }

    @Override
    public boolean isAdmin(String login) {
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        return checkUserDAO.isAdmin(login);
    }
}
