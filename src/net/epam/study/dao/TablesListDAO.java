package net.epam.study.dao;

import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;

import java.util.List;

public interface TablesListDAO {
    List<Order> getOrders();
    List<MenuItem> getMenu();
}
