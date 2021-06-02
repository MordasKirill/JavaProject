package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.TablesListDAO;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.service.ServiceException;
import net.epam.study.service.TablesListService;

import java.util.List;

public class TablesListImpl implements TablesListService {
    @Override
    public List<Order> getOrders() throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();
        List<Order> orders;
        try {
            orders = showTables.getOrders();
            return orders;
        } catch (DAOException e){
            throw new ServiceException("Get orders fail", e);
        }

    }

    @Override
    public List<MenuItem> getMenu() throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();
        List<MenuItem> menuItems;
        try {
            menuItems = showTables.getMenu();
            return menuItems;
        } catch (DAOException e){
            throw new ServiceException("Get orders fail", e);
        }
    }
}
