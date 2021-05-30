package net.epam.study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

public class ConnectionPool {
    private final Vector<Connection> availableConnections = new Vector<Connection>();
    private final Vector<Connection> usedConnections = new Vector<Connection>();
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
            availableConnections.addElement(getConnection());
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
            newConn = availableConnections.lastElement();
            availableConnections.removeElement(newConn);
        }
        usedConnections.addElement(newConn);
        return newConn;
    }

    public synchronized void putBack(Connection connection) throws NullPointerException {
        if (connection != null) {
            if (usedConnections.removeElement(connection)) {
                availableConnections.addElement(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }

    public int getAvailableConnectionsCnt() {
        return availableConnections.size();
    }
}
