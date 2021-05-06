package net.epam.study.dao;

import net.epam.study.connection.ConnectionToDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDelete {
    public static void delete(String sqlCommand){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(ConnectionToDB.connectionUrl, ConnectionToDB.userNameDB, ConnectionToDB.passwordDB);
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println("SUCCESS DB: Order deleted.");
        } catch (ClassNotFoundException | SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
            } catch (SQLException exc) {
            }
        }
    }
}

