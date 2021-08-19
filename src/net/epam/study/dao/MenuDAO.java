package net.epam.study.dao;

import net.epam.study.bean.MenuItem;

import java.util.List;

public interface MenuDAO {
    void createMenuItem(MenuItem menuItem) throws DAOException;

    boolean isMenuItemExists(String itemName, String category) throws DAOException;

    void deleteMenuItem(String itemName, String category) throws DAOException;

    List<MenuItem> getMenu() throws DAOException;
}
