package by.epam.java.training.lifehacks.dao.lifehack;

import by.epam.java.training.lifehacks.dao.connection.ConnectionPool;
import by.epam.java.training.lifehacks.dao.connection.ProxyConnection;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.dto.UserLifeHackDto;
import by.epam.java.training.lifehacks.model.entity.LifeHack;
import by.epam.java.training.lifehacks.model.entityenum.LifeHackCategory;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LifeHackDaoImpl implements LifeHackDao {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    private LifeHackDaoImpl() {}

    private static class LifeHackDaoImplHolder {
        private final static LifeHackDaoImpl INSTANCE = new LifeHackDaoImpl();
    }

    public static LifeHackDaoImpl getInstance() {
        return LifeHackDaoImplHolder.INSTANCE;
    }


    @Override
    public boolean create(LifeHack lifeHack) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(LifeHackDaoQueries.SQL_CREATE_LIFE_HACK);
            preparedStatement.setLong(1, lifeHack.getUserId());
            preparedStatement.setString(2, lifeHack.getLifeHackCategory().toString());
            preparedStatement.setString(3, lifeHack.getName());
            preparedStatement.setString(4, lifeHack.getDescription());
            preparedStatement.setBytes(5, lifeHack.getPicture());
            preparedStatement.setLong(6, lifeHack.getDateOfPosting().atZone(ZoneId.systemDefault()).
                    toInstant().toEpochMilli());
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
    public List<LifeHack> findAll() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<LifeHack> lifeHacks;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_SELECT_ALL_LIFE_HACKS);
            lifeHacks = createList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
            closeResultSet(resultSet);
        }

        return lifeHacks;
    }

    @Override
    public LifeHack findById(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(LifeHackDaoQueries.SQL_SELECT_LIFE_HACK_BY_ID, id));

            if (resultSet.next()) {
                return createEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return null;
    }

    @Override
    public boolean updateById(Long id, LifeHack lifeHack) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(LifeHackDaoQueries.SQL_UPDATE_LIFE_HACK_BY_ID);
            preparedStatement.setString(1, lifeHack.getLifeHackCategory().toString());
            preparedStatement.setString(2, lifeHack.getName());
            preparedStatement.setString(3, lifeHack.getDescription());
            preparedStatement.setBytes(4, lifeHack.getPicture());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);

        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    public List<UserLifeHackDto> findByName(String name) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<UserLifeHackDto> lifeHacks;
        name += "%";

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(LifeHackDaoQueries.SQL_SELECT_LIFE_HACKS_BY_NAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            lifeHacks = createListOfUserLifeHackDto(resultSet);

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(proxyConnection);

        }

        return lifeHacks;
    }



    @Override
    public boolean checkAddToFavorite(Long userId, Long lifeHackId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(LifeHackDaoQueries.SQL_IS_LIFE_HACK_LIKED, userId, lifeHackId));
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
            closeResultSet(resultSet);

        }

        return false;
    }

    @Override
    public List<LifeHack> findFavoriteLifeHacksByUserId(Long userId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<LifeHack> lifeHacks;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(LifeHackDaoQueries.SQL_SELECT_FAVORITE_LIFE_HACKS_BY_USER_ID, userId));
            lifeHacks = createList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
            closeResultSet(resultSet);

        }

        return lifeHacks;
    }

    @Override
    public boolean createFavoriteLifeHack(Long userId, Long lifeHackId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.execute(String.format(LifeHackDaoQueries.SQL_CREATE_FAVORITE_LIFE_HACK, userId, lifeHackId));

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

    }

    @Override
    public boolean delete(Long userId, Long lifeHackId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.execute(String.format(LifeHackDaoQueries.SQL_DELETE_FAVORITE_LIFE_HACK, userId, lifeHackId));
            return true;

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

    }

    @Override
    public List<LifeHack> findOfferedLifeHacks() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<LifeHack> lifeHacks;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_SELECT_OFFERED_LIFE_HACKS);
            lifeHacks = createList(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return lifeHacks;
    }


    @Override
    public boolean submit(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeUpdate(String.format(LifeHackDaoQueries.SQL_SUBMIT_LIFE_HACK, id));

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
    public boolean reject(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.executeUpdate(String.format(LifeHackDaoQueries.SQL_REJECT_LIFE_HACK, id));

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
    public boolean offer(LifeHack lifeHack) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(LifeHackDaoQueries.SQL_OFFER_LIFE_HACK);
            preparedStatement.setLong(1, lifeHack.getUserId());
            preparedStatement.setString(2, lifeHack.getLifeHackCategory().toString());
            preparedStatement.setString(3, lifeHack.getName());
            preparedStatement.setString(4, lifeHack.getDescription());
            preparedStatement.setBytes(5, lifeHack.getPicture());
            preparedStatement.setLong(6, lifeHack.getDateOfPosting().atZone(ZoneId.systemDefault()).
                    toInstant().toEpochMilli());
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
    public Long getCountOfLifeHacks() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_GET_COUNT_OF_LIFE_HACKS);
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
    public Long getCountOfSubmittedLifeHacks() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_GET_COUNT_OF_SUBMITTED_LIFE_HACKS);
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
    public Long getCountOfRejectedLifeHacks() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_GET_COUNT_OF_REJECTED_LIFE_HACKS);
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
    public Long getCountOfOfferedLifeHacks() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Long count = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_GET_COUNT_OF_OFFERED_LIFE_HACKS);
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
    public LifeHack createEntity(ResultSet resultSet) throws DaoException {
        try {
            return new LifeHack(
                    resultSet.getLong("life_hack_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getString("name"),
                    LifeHackCategory.valueOf(resultSet.getString("category")),
                    resultSet.getString("description"),
                    resultSet.getBytes("picture"),
                    new String(Base64.encodeBase64(resultSet.getBytes("picture")), "UTF-8"),
                    resultSet.getInt("status"),
                    Instant.ofEpochMilli(resultSet.getLong("date_of_posting")).
                            atZone(ZoneId.systemDefault()).toLocalDateTime()
                    );
        } catch (SQLException | UnsupportedEncodingException e) {
            throw new DaoException(e);
        }
    }


    public List<UserLifeHackDto> findAllUserLifeHackDto() throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<UserLifeHackDto> lifeHacks;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(LifeHackDaoQueries.SQL_SELECT_ALL_SUBMITTED_LIFE_HACKS_WITH_USERNAME);
            lifeHacks = createListOfUserLifeHackDto(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
            closeResultSet(resultSet);
        }

        return lifeHacks;
    }

    public UserLifeHackDto findUserLifeHackDtoById(Long lifeHackId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(LifeHackDaoQueries.SQL_SELECT_LIFE_HACK_WITH_USERNAME_BY_ID, lifeHackId));

            if (resultSet.next()) {
                return createDto(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return null;
    }


    private UserLifeHackDto createDto(ResultSet resultSet) throws DaoException {
        try {
            return new UserLifeHackDto(
                    resultSet.getLong("life_hack_id"),
                    resultSet.getString("username"),
                    resultSet.getString("name"),
                    LifeHackCategory.valueOf(resultSet.getString("category")),
                    resultSet.getString("description"),
                    resultSet.getBytes("picture"),
                    new String(Base64.encodeBase64(resultSet.getBytes("picture")), "UTF-8"),
                    resultSet.getInt("status"),
                    Instant.ofEpochMilli(resultSet.getLong("date_of_posting")).
                            atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
                    );
        } catch (SQLException | UnsupportedEncodingException e) {
            throw new DaoException(e);
        }
    }

    private List<UserLifeHackDto> createListOfUserLifeHackDto(ResultSet resultSet) throws SQLException, DaoException {
        List<UserLifeHackDto> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(createDto(resultSet));
        }
        return list;
    }

}
