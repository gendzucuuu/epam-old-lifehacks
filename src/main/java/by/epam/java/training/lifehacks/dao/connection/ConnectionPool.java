package by.epam.java.training.lifehacks.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


public class   ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final String DATABASE_PROPERTY = "database";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_USE_UNICODE = "db.useUnicode";
    private static final String DATABASE_POOL_SIZE = "db.poolSize";

    private static ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private static ConnectionPool instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean instanceStatus = new AtomicBoolean(false);

    private static int poolSize;

    private ConnectionPool() {
        //maybe check instance
    }

    public static ConnectionPool getInstance() {
        if (!instanceStatus.get()) {
            lock.lock();
            try {
                if (!instanceStatus.get()) {
                    instance = new ConnectionPool();
                    registerDriver();
                    init();
                    instanceStatus.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection proxyConnection = null;

        try {
            proxyConnection = connectionQueue.take();
            logger.log(Level.INFO, "Take connection from connection pool");

        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Can't take connection from connection pool",e);
        }

        return proxyConnection;
    }

    void putConnection(ProxyConnection proxyConnection) {
        try {
            connectionQueue.put(proxyConnection);
            logger.log(Level.INFO, "Put connection to connection pool");
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Can't put connection to connection pool", e);
        }
    }

    public void destroyConnections() {
        for (int i = 0; i < poolSize; i++) {
            ProxyConnection proxyConnection = null;

            try {
                proxyConnection = connectionQueue.take();
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e);
            } finally {
                if (proxyConnection != null) {
                    proxyConnection.trueClose();
                }
            }
        }

        deregisterDrivers();
    }

    private static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            logger.log(Level.INFO, "MySQL JDBC driver registered");
        } catch (SQLException e) {
            logger.log(Level.FATAL, "Mysql jdbc driver can't be registered", e);
            throw new RuntimeException("Mysql jdbc driver can't be registered", e);
        }
    }

    private static void deregisterDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
            logger.log(Level.INFO, "Drivers deregistered");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Can't deregister drivers", e);
        }
    }

    private static void init() {
        ResourceBundle resourceBundle;

        try {
            resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY);
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Not found bundle for database", e);
            throw new RuntimeException("Not found bundle for database");
        }

        String url = resourceBundle.getString(DATABASE_URL);
        String user = resourceBundle.getString(DATABASE_USER);
        String password = resourceBundle.getString(DATABASE_PASSWORD);
        String useUnicode = resourceBundle.getString(DATABASE_USE_UNICODE);
        String poolSizeString = resourceBundle.getString(DATABASE_POOL_SIZE);

        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);
        properties.put("useUnicode", useUnicode);

        poolSize = Integer.parseInt(poolSizeString);

        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            Connection connection;

            try {
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                logger.log(Level.FATAL, "Not found resources for connection " +
                        "to database", e);
                throw new RuntimeException("Not found resources for connection " +
                        "to database");
            }

            ProxyConnection proxyConnection = new ProxyConnection(connection);

            try {
                connectionQueue.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Can't put connection in pool",e);
            }
        }

        logger.log(Level.INFO, "Pool created");
    }
}
