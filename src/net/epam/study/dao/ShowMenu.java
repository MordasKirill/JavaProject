package net.epam.study.dao;

import net.epam.study.controller.Listener;
import net.epam.study.entity.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowMenu {
    public static List<MenuItem> showMenu ()  {
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
