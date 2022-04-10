package by.epam.java.training.lifehacks.dao.comment;

final class CommentDaoQueries {
    static final String SQL_CREATE_COMMENT = "INSERT INTO comment(user_id, life_hack_id, description," +
            " date_of_comment) VALUES (?, ?, ?, ?)";
    static final String SQL_DELETE_COMMENT = "DELETE FROM comment WHERE comment_id = '%d'";
    static final String SQL_SELECT_COMMENTS_BY_LIFE_HACK_ID = "SELECT c.comment_id, c.life_hack_id, c.description," +
            "c.date_of_comment, u.username AS username, u.role AS role FROM comment c JOIN user u ON c.user_id = u.user_id " +
            "WHERE c.life_hack_id = '%d' AND u.is_blocked = 0";
}
