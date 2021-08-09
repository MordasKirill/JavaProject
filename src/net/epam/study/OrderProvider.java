package net.epam.study;

import net.epam.study.bean.MenuItem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class OrderProvider {
    private static final OrderProvider instance = new OrderProvider();
    private final Map<Integer, LinkedList<MenuItem>> order = new HashMap<>();

    private OrderProvider() {
    }

    public static OrderProvider getInstance(){
        return instance;
    }

    public Map<Integer, LinkedList<MenuItem>> getOrder(){
        return order;
    }
}
