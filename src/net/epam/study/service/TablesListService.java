package net.epam.study.service;

import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;

import java.util.List;

public interface TablesListService {
    List<Order> getOrders();
    List<MenuItem> getMenu();
}
