package net.epam.study.dao;

import net.epam.study.controller.Listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDelete {
    public static void delete(String sqlCommand){
        Connection connection = Listener.connection;
        Statement statement;
        try {
            statement = connection.createStatement();
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate(sqlCommand);
            System.out.println("SUCCESS DB: Order deleted.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
    }
}

