package by.epam.java.training.lifehacks.dao.comment;

import by.epam.java.training.lifehacks.dao.connection.ConnectionPool;
import by.epam.java.training.lifehacks.dao.connection.ProxyConnection;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.dto.UserCommentDto;
import by.epam.java.training.lifehacks.model.entity.Comment;
import by.epam.java.training.lifehacks.model.entityenum.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao{
    private static final Logger logger = LogManager.getLogger(CommentDaoImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    private CommentDaoImpl() {}

    private static class CommentDaoImplHolder {
        private final static CommentDaoImpl INSTANCE = new CommentDaoImpl();
    }

    public static CommentDaoImpl getInstance() {
        return CommentDaoImplHolder.INSTANCE;
    }


    @Override
    public boolean create(Comment comment) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            preparedStatement = proxyConnection.prepareStatement(CommentDaoQueries.SQL_CREATE_COMMENT);
            preparedStatement.setLong(1, comment.getUserId());
            preparedStatement.setLong(2, comment.getLifeHackId());
            preparedStatement.setString(3, comment.getDescription());
            preparedStatement.setLong(4, comment.getDateOfComment().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
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
    public List<Comment> findAll() throws DaoException {
        return null;
    }

    @Override
    public Comment findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public boolean updateById(Long id, Comment comment) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            statement.execute(String.format(CommentDaoQueries.SQL_DELETE_COMMENT, id));
            return true;

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(proxyConnection);
        }
    }

    @Override
    public List<UserCommentDto> findByLifeHackId(Long lifeHackId) throws DaoException {
        ProxyConnection proxyConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<UserCommentDto> comments;

        try {
            proxyConnection = ConnectionPool.getInstance().getConnection();
            statement = proxyConnection.createStatement();
            resultSet = statement.executeQuery(String.format(CommentDaoQueries.SQL_SELECT_COMMENTS_BY_LIFE_HACK_ID, lifeHackId));
            comments = createListOfUserCommentDto(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(proxyConnection);
        }

        return comments;
    }



    @Override
    public Comment createEntity(ResultSet resultSet) throws DaoException {
        try {
            return new Comment(
                    resultSet.getLong("comment_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("life_hack_id"),
                    resultSet.getString("description"),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getLong("date_of_comment")),
                            ZoneId.systemDefault())
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    private UserCommentDto createDto(ResultSet resultSet) throws DaoException {
        try {
            return new UserCommentDto(
                    resultSet.getLong("comment_id"),
                    resultSet.getString("username"),
                    UserRole.valueOf(resultSet.getString("role")),
                    resultSet.getLong("life_hack_id"),
                    resultSet.getString("description"),
                    Instant.ofEpochMilli(resultSet.getLong("date_of_comment")).
                            atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
            );
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<UserCommentDto> createListOfUserCommentDto(ResultSet resultSet) throws SQLException, DaoException {
        List<UserCommentDto> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(createDto(resultSet));
        }
        return list;
    }

}
