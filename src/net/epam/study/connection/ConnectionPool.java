package net.epam.study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class ConnectionPool {
    private final ArrayList<Connection> availableConnections = new ArrayList<Connection>();
    private final ArrayList<Connection> usedConnections = new ArrayList<Connection>();
    private String url;
    private String password;
    private String userName;
    public ConnectionPool(){}
    public ConnectionPool(String url, String password, String userName, String driver, int initConnCnt) {
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.url = url;
        this.password = password;
        this.userName = userName;
        for (int i = 0; i < initConnCnt; i++) {
            availableConnections.add(getConnection());
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized Connection retrieve() {
        Connection newConn;
        if (availableConnections.size() == 0) {
            newConn = getConnection();
        } else {
            newConn = availableConnections.get(availableConnections.size() - 1);
            availableConnections.remove(newConn);
        }
        usedConnections.add(newConn);
        return newConn;
    }

    public synchronized void putBack(Connection connection) throws NullPointerException {
        if (connection != null) {
            if (usedConnections.remove(connection)) {
                availableConnections.add(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }

    public int getAvailableConnectionsCnt() {
        return availableConnections.size();
    }
}
