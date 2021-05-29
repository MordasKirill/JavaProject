package net.epam.study.dao.impl;

import net.epam.study.listener.Listener;
import net.epam.study.dao.ShowTablesDAO;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowTablesImpl implements ShowTablesDAO {
    public static final int defaultLimit = 8;
    public static int limit = 8;
    public static final String columnId = "id";
    public static final String columnFullName = "fullName";
    public static final String columnAddress = "address";
    public static final String columnEmail = "email";
    public static final String columnPhone = "phone";
    public static final String columnDetails = "details";
    public static final String selectFromOrders = "select id, fullName, address, email, phone, details from orders where id>0 LIMIT ";
    public static final String columnItemName = "itemName";
    public static final String columnPrice = "price";
    public static final String columnWaitTime = "waitTime";
    public static final String columnCategory = "category";
    public static final String selectFromMenu = "select itemName, price, waitTime, category from menu";
    public static String error;
    public List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(selectFromOrders + limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getString(columnId));
                order.setFullName(resultSet.getString(columnFullName));
                order.setAddress(resultSet.getString(columnAddress));
                order.setEmail(resultSet.getString(columnEmail));
                order.setPhone(resultSet.getString(columnPhone));
                order.setDetails(resultSet.getString(columnDetails));
                orders.add(order);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to show orders !";
            System.out.println("FAIL DB: Fail to show orders.");
        }
        return orders;
    }

    public List<MenuItem> getMenu()  {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(selectFromMenu);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                MenuItem menuItem = new MenuItem();
                menuItem.setName(resultSet.getString(columnItemName));
                menuItem.setPrice(resultSet.getString(columnPrice));
                menuItem.setFilingTime(resultSet.getString(columnWaitTime));
                menuItem.setCategory(resultSet.getString(columnCategory));
                menuItems.add(menuItem);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to show menu !";
            System.out.println("FAIL DB: Fail to show menu.");
        }
        return menuItems;
    }
}
