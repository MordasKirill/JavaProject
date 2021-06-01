package net.epam.study.service.impl;

import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.service.CheckNewUserService;

public class CheckNewUserImpl implements CheckNewUserService {
    @Override
    public boolean check(String login, String hashPassword, String role) {
        DAOProvider provider = DAOProvider.getInstance();
        NewUserValidateDAO newUserValidateDAO = provider.getNewUserValidateDAO();
        return newUserValidateDAO.validate(login, hashPassword, role);
    }
}
