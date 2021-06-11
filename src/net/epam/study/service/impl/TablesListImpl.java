package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.TablesListDAO;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.entity.User;
import net.epam.study.service.ServiceException;
import net.epam.study.service.TablesListService;

import javax.jws.soap.SOAPBinding;
import java.util.List;


public class TablesListImpl implements TablesListService {
    public static final int DEFAULT_LIMIT = 8;

    @Override
    public List<Order> getOrders(int limit) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        List<Order> orders;

        try {
            orders = showTables.getOrders(limit);
            return orders;
            
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get orders fail", e);
        }

    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        List<Order> orders;

        try {
            orders = showTables.getAllOrders();
            return orders;

        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get all orders fail", e);
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

        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get orders fail", e);
        }

    }


    @Override
    public List<User> getUsers(int limit) throws ServiceException{

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        List<User> users;

        try {
            users = showTables.getUsers(limit);
            return users;

        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get orders fail", e);
        }

    }

    @Override
    public List<User> getAllUsers() throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        List<User> users;

        try {
            users = showTables.getAllUsers();
            return users;

        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get all orders fail", e);
        }

    }

    @Override
    public int getActualLimit(int limit){

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        int result;
        result = showTables.getActualLimit(limit);

        return result;
    }


    @Override
    public int getPreviousLimit(int limit){

        DAOProvider daoProvider = DAOProvider.getInstance();
        TablesListDAO showTables = daoProvider.getTablesListDAO();

        int result;
        result = showTables.getPreviousLimit(limit);

        return result;
    }
}
