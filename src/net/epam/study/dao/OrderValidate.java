package net.epam.study.dao;

import net.epam.study.controller.Listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderValidate {
    public static void validate (String sqlCommand)  {
        Connection connection = Listener.connection;
        Statement statement;
        try {
            statement = connection.createStatement();
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate(sqlCommand);
            System.out.println("SUCCESS DB: Order created.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
    }
}
