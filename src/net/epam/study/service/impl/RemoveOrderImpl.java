package net.epam.study.service.impl;

import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.DeleteOrderDAO;
import net.epam.study.service.RemoveOrderService;

public class RemoveOrderImpl implements RemoveOrderService {
    @Override
    public void delete(String id) {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteOrderDAO deleteOrderDAO = daoProvider.getDeleteOrderDAO();
        deleteOrderDAO.delete(id);
    }
}
