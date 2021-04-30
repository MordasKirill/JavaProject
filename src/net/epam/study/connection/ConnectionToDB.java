//package net.epam.study.connection;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class ConnectionToDB {
//    public static Connection connection = null;
//    public static Statement statement = null;
//
//    public static Statement connect(){
//        try {
//            String userNameDB = "root";
//            String passwordDB = "3158095KIRILLMordas";
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            String connectionUrl = "jdbc:mysql://localhost:3306/test";
//            connection = DriverManager.getConnection(connectionUrl, userNameDB, passwordDB);
//            System.out.println("SUCCESS DB: Connected.");
//            statement = connection.createStatement();
//        } catch (ClassNotFoundException | SQLException exc) {
//            exc.printStackTrace();
//            System.out.println("FAIL DB: Fail to write DB.");
//        } catch (IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (connection != null) connection.close();
//                if (statement != null) statement.close();
//            } catch (SQLException throwables) {
//
//            }
//        }
//        return statement;
//    }
//
//}
