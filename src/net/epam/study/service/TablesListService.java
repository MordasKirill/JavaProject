package net.epam.study.service;

import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;

import java.util.List;

public interface TablesListService {
    List<Order> getOrders(int limit) throws ServiceException;
    List<MenuItem> getMenu() throws ServiceException;
    int getActualLimit(int limit);
}
