package by.epam.java.training.lifehacks.dao.user;

final class UserDaoQueries {
    static final String SQL_SELECT_ALL_USERS = "SELECT user_id, is_blocked," +
            " username, email, password, role FROM user";
    static final String SQL_SELECT_USERS = "SELECT user_id, is_blocked," +
            " username, first_name, second_name, email, password, role FROM user WHERE role = 'USER'";
    static final String SQL_SELECT_USER_BY_ID = "SELECT user_id, is_blocked," +
            " username, email, password, role FROM user WHERE id = '%d'";
    static final String SQL_CREATE_USER = "INSERT INTO user(is_blocked, username, first_name, second_name," +
            " email, password, role) VALUES (false, ?, ?, ?, ?, ?, 'USER')";
    static final String SQL_DELETE_USER_BY_ID = "UPDATE user SET is_blocked = 1 " +
            "WHERE user_id = '%d'";
    static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET username = ? AND email = ? AND password = ? " +
            " WHERE user_id = ?";
    static final String SQL_SELECT_USER_BY_EMAIL = "SELECT user_id, username, email FROM user " +
            "WHERE email = ?";
    static final String SQL_SELECT_USER_BY_USERNAME = "SELECT user_id, username, email FROM user " +
            "WHERE username = ?";
    static final String SQL_GET_USER_ROLE = "SELECT role FROM user " +
            "WHERE username = ? AND password = ?";
    static final String SQL_AUTH_USER = "SELECT user_id, is_blocked, username, first_name, second_name, " +
            "password, email, role FROM user " +
            "WHERE username = ? AND password = ? AND is_blocked = 0";
    static final String SQL_BLOCK_USER = "UPDATE user SET is_blocked = 1" +
            " WHERE user_id = '%d'";
    static final String SQL_UNLOCK_USER = "UPDATE user SET is_blocked = 0" +
            " WHERE user_id = '%d'";
    static final String SQL_MAKE_ADMIN = "UPDATE user SET role = 'ADMIN'" +
            " WHERE user_id = '%d'";
    static final String SQL_GET_COUNT_OF_USERS = "SELECT COUNT(*) FROM user WHERE role = 'USER'";
    static final String SQL_GET_COUNT_OF_BLOCKED_USERS = "SELECT COUNT(*) FROM user WHERE role = 'USER'" +
            " and is_blocked = 1";
    static final String SQL_GET_COUNT_OF_ACTIVE_USERS = "SELECT COUNT(*) FROM user WHERE role = 'USER'" +
            " and is_blocked = 0";
}
