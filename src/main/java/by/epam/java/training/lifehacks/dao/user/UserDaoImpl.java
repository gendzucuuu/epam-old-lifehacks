package by.epam.java.training.lifehacks.dao.user;

import by.epam.java.training.lifehacks.dao.connection.ConnectionPool;
import by.epam.java.training.lifehacks.dao.connection.ProxyConnection;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.model.entityenum.UserRole;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);


    private UserDaoImpl() {}

    private static class UserDaoImplHolder {
        private final static UserDaoImpl INSTANCE = new UserDaoImpl();
    }

    public static UserDaoImpl getInstance() {
        return UserDaoImplHolder.INSTANCE;
    }


    @Override
    public boolean create(User user) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException{
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(UserDaoQueries.SQL_SELECT_ALL_USERS);
            users = createList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return users;
    }

    @Override
    public User findById(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(UserDaoQueries.SQL_SELECT_USER_BY_ID, id));

            if (resultSet.next()) {
                return createEntity(resultSet);
            }
            else {
                LOGGER.log(Level.INFO, "No result set");
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return null;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        boolean flag = false;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeQuery(String.format(UserDaoQueries.SQL_DELETE_USER_BY_ID, id));

            flag = true;
        } catch (SQLException e) {
            throw new DaoException();
        }
        finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return flag;
    }

    @Override
    public boolean updateById(Long id, User user) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        boolean flag = false;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_UPDATE_USER_BY_ID);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();

            flag = true;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
        }

        return flag;
    }

    @Override
    public boolean isEmailNotExist (String email) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
        }

        return true;
    }

    @Override
    public boolean isUsernameNotExist (String username) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_SELECT_USER_BY_USERNAME);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
        }

        return true;
    }

    @Override
    public UserRole checkUserRole(String username, String password) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserRole userRole = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_GET_USER_ROLE);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String roleStr = resultSet.getString("role");
                userRole = UserRole.valueOf(roleStr.toUpperCase());
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
            closeResultSet(resultSet);
        }
        
        return userRole;
    }

    @Override
    public User authorization(String username, String password) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_AUTH_USER);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return createEntity(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
        }

        return null;
    }

    @Override
    public List<User> findUsers() throws DaoException{
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(UserDaoQueries.SQL_SELECT_USERS);
            users = createList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return users;
    }

    @Override
    public boolean block(Long userId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeUpdate(String.format(UserDaoQueries.SQL_BLOCK_USER, userId));

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

    }

    @Override
    public boolean unlock(Long userId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeUpdate(String.format(UserDaoQueries.SQL_UNLOCK_USER, userId));

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }
    }

    @Override
    public boolean makeAdmin(Long userId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        boolean flag = false;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeUpdate(String.format(UserDaoQueries.SQL_MAKE_ADMIN, userId));

            flag = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return flag;
    }

    @Override
    public boolean create(User user, String pass) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(UserDaoQueries.SQL_CREATE_USER);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, pass);
            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);
        }
    }


    @Override
    public Long getCountOfUsers() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(UserDaoQueries.SQL_GET_COUNT_OF_USERS);
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return count;
    }

    @Override
    public Long getCountOfActiveUsers() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(UserDaoQueries.SQL_GET_COUNT_OF_ACTIVE_USERS);
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return count;
    }

    @Override
    public Long getCountOfBlockedUsers() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(UserDaoQueries.SQL_GET_COUNT_OF_BLOCKED_USERS);
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return count;
    }

    @Override
    public User createEntity(ResultSet resultSet) throws DaoException {
        try {
            return new User(
                    resultSet.getLong("user_id"), resultSet.getBoolean("is_blocked"),
                    resultSet.getString("username"), resultSet.getString("first_name"),
                    resultSet.getString("second_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    UserRole.valueOf(resultSet.getString("role"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

}
