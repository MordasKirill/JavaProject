package net.epam.study.service;

import net.epam.study.bean.MenuItem;

import java.util.List;

public interface MenuService {
    void deleteMenuItem(String itemName, String category) throws ServiceException;

    boolean isMenuItemExists(String itemName, String category) throws ServiceException;

    List<MenuItem> getMenu() throws ServiceException;

    void createMenuItem(String itemName, String price, String waitTime, String category) throws ServiceException;
}
