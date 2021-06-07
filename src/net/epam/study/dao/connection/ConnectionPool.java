package net.epam.study.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;
    private String url;
    private String password;
    private String userName;
    private String driver;
    private int initConnCnt;
    public static ConnectionPool connectionPool;

    private static final Logger log = Logger.getLogger(ConnectionPool.class);

    public ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driver = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.userName = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.initConnCnt = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            initConnCnt = 5;
        }
        availableConnections = new ArrayBlockingQueue<Connection>(initConnCnt);
        usedConnections = new ArrayBlockingQueue<Connection>(initConnCnt);
        for (int i = 0; i < initConnCnt; i++) {
            availableConnections.add(getConnection());
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Connection retrieve() throws ConnectionPoolException {
        Connection newConn;
        if (availableConnections.size() == 0) {
            newConn = getConnection();
        } else {
            try {
                newConn = availableConnections.take();
                availableConnections.remove(newConn);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException("Error connecting to the data source.", e);
            }
        }
        usedConnections.add(newConn);
        return newConn;
    }

    public void putBack(Connection connection) throws NullPointerException {
        if (connection != null) {
            if (usedConnections.remove(connection)) {
                availableConnections.add(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }
    public void dispose() {
        clearConnectionQueue();
    }
    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
            con.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"Connection isn't return to the pool.", e);
        }
        try {
            rs.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"ResultSet isn't closed.", e);
        }
        try {
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"Statement isn't closed.", e);
        }
    }

    public void closeConnection(Statement st, ResultSet rs) {

        try {
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"Statement isn't closed.", e);
        }
        try {
            rs.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"ResultSet isn't closed.", e);
        }
    }

    public void closeConnection(Statement st) {

        try {
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR,"Statement isn't closed.", e);
        }
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(usedConnections);
            closeConnectionsQueue(availableConnections);
        } catch (SQLException e) {
            log.log(Level.ERROR, "Error closing the connection.", e);
        }
    }
    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException { Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) { connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }
    public int getAvailableConnectionsCnt() {
        return availableConnections.size();
    }


}
