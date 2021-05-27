package net.epam.study.dao.impl;

import net.epam.study.controller.Listener;
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
    public List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.prepareStatement("select id, fullName, address, email, phone, details from orders ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getString("id"));
                order.setFullName(resultSet.getString("fullName"));
                order.setAddress(resultSet.getString("address"));
                order.setEmail(resultSet.getString("email"));
                order.setPhone(resultSet.getString("phone"));
                order.setDetails(resultSet.getString("details"));
                orders.add(order);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
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
            statement = connection.prepareStatement("select itemName, price, waitTime, category from menu ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                MenuItem menuItem = new MenuItem();
                menuItem.setName(resultSet.getString("itemName"));
                menuItem.setPrice(resultSet.getString("price"));
                menuItem.setFilingTime(resultSet.getString("waitTime"));
                menuItem.setCategory(resultSet.getString("category"));
                menuItems.add(menuItem);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to show menu.");
        }
        return menuItems;
    }
}
