package net.epam.study.service.impl;

import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.TablesListDAO;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.service.TablesListService;

import java.util.List;

public class TablesListImpl implements TablesListService {
    @Override
    public List<Order> getOrders() {
        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();
        List<Order> orders;
        orders = showTables.getOrders();
        return orders;
    }

    @Override
    public List<MenuItem> getMenu() {
        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();
        List<MenuItem> menuItems;
        menuItems = showTables.getMenu();
        return menuItems;
    }
}
