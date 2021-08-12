package net.epam.study.dao;

import net.epam.study.bean.MenuItem;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.util.List;

public interface MenuDAO {
    void createMenuItem(MenuItem menuItem) throws DAOException, ConnectionPoolException;

    boolean isMenuItemExists(String itemName, String category) throws DAOException, ConnectionPoolException;

    void deleteMenuItem(String itemName, String category) throws DAOException, ConnectionPoolException;

    List<MenuItem> getMenu() throws DAOException, ConnectionPoolException;
}
