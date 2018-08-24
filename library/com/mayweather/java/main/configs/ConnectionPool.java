package configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> availableConnections = new LinkedList<>();
    private LinkedList<Connection> usedConnections = new LinkedList<>();
    private DB db;

    public ConnectionPool(DB db, int initConnections) {
        this.db = db;
        try {
            Class.forName(this.db.getDriverName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < initConnections; i++) {
            availableConnections.add(getConnection());
        }
    }

    private Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(db.getConnectionString(), db.getLogin(), db.getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public synchronized Connection retrieve() {
        Connection connection = null;

        if (availableConnections.size() == 0) {
            connection = getConnection();
        } else {
            connection = availableConnections.getLast();
            availableConnections.remove(connection);
        }
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void putback(Connection connection) {
        if (connection != null) {
            if (usedConnections.remove(connection)) {
                availableConnections.add(connection);
            } else {
                throw new NullPointerException("Connection not in used connections");
            }
        }
    }

    public int getAvailableConnections() {
        return availableConnections.size();
    }

}
