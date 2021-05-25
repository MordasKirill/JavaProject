package net.epam.study.dao;

import net.epam.study.controller.Listener;
import net.epam.study.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowOrders {
    public static List<Order> showOrders ()  {
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
}
