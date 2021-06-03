package net.epam.study.service.impl;

import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.HashPasswordDAO;
import net.epam.study.service.HashPasswordService;

public class HashPasswordImpl implements HashPasswordService {

    @Override
    public String hashPassword(String password) {
        DAOProvider provider = DAOProvider.getInstance();
        HashPasswordDAO hashPasswordDAO = provider.getHashPasswordDAO();

        String result;
        result = hashPasswordDAO.hashPassword(password);
        return result;
    }
}
