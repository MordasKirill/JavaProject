package net.epam.study.service;

import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.entity.User;

import java.util.List;

public interface TablesListService {
    List<Order> getOrders(int limit) throws ServiceException;
    List<Order> getAllOrders() throws ServiceException;
    List<MenuItem> getMenu() throws ServiceException;
    List<User> getUsers(int limit) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    int getActualLimit(int limit);
    int getPreviousLimit(int limit);
}
