package by.epam.java.training.lifehacks.dao;

import by.epam.java.training.lifehacks.dao.connection.ProxyConnection;
import by.epam.java.training.lifehacks.exception.DaoException;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface AbstractDao<T> {
    final static Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    boolean create(T t) throws DaoException;
    List<T> findAll() throws DaoException;
    T findById(Long id) throws DaoException;
    boolean deleteById(Long id) throws DaoException;
    boolean updateById(Long id, T t) throws DaoException;

    T createEntity(ResultSet resultSet) throws DaoException;



    default void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Wrong statement", e);
            }
        }
        else {
            LOGGER.log(Level.ERROR, "Statement is null");
        }
    }

    default void closeConnection(ProxyConnection proxyConnection) {
        if (proxyConnection != null) {
            try {
                proxyConnection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Wrong connection", e);
            }
        }
        else {
            LOGGER.log(Level.ERROR, "Connection is null");
        }
    }

    default void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Wrong result set", e);
            }
        }
        else {
            LOGGER.log(Level.ERROR, "Result set is null");
        }
    }


    default List<T> createList(ResultSet resultSet) throws DaoException, SQLException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(createEntity(resultSet));
        }
        return list;
    }
}
