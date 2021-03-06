package net.epam.study.service.impl;

import net.epam.study.bean.MenuItem;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.MenuDAO;
import net.epam.study.service.MenuService;
import net.epam.study.service.ServiceException;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    @Override
    public void deleteMenuItem(String itemName, String category) throws ServiceException {
        MenuDAO menuDAO = DAOProvider.getInstance().getMenuDAO();
        try {
            menuDAO.deleteMenuItem(itemName, category);
        } catch (DAOException e) {
            throw new ServiceException("Fail to delete user", e);
        }
    }

    @Override
    public boolean isMenuItemExists(String itemName, String category) throws ServiceException {
        MenuDAO menuDAO = DAOProvider.getInstance().getMenuDAO();
        try {
            return menuDAO.isMenuItemExists(itemName, category);
        } catch (DAOException e) {
            throw new ServiceException("Fail to delete user", e);
        }
    }

    @Override
    public List<MenuItem> getMenu() throws ServiceException {
        MenuDAO menuDAO = DAOProvider.getInstance().getMenuDAO();
        try {
            return menuDAO.getMenu();
        } catch (DAOException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }

    @Override
    public void createMenuItem(MenuItem menuItem) throws ServiceException {
        MenuDAO menuDAO = DAOProvider.getInstance().getMenuDAO();
        try {
            menuDAO.createMenuItem(menuItem);
        } catch (DAOException e) {
            throw new ServiceException("Menu item create fail", e);
        }
    }
}
