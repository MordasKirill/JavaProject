package net.epam.study.dao;

import net.epam.study.controller.Listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderValidate {
    public static void validate (String sqlCommand)  {
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sqlCommand);
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate();
            System.out.println("SUCCESS DB: Order created.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
    }
}
