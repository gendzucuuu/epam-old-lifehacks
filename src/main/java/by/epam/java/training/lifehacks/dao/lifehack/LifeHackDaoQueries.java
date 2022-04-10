package by.epam.java.training.lifehacks.dao.lifehack;

final class LifeHackDaoQueries {
    static final String SQL_SELECT_LIFE_HACK_BY_ID = "SELECT life_hack_id, user_id, category," +
            " name, description, picture, status, date_of_posting FROM life_hack WHERE life_hack_id = '%d'";
    static final String SQL_SELECT_ALL_LIFE_HACKS = "SELECT life_hack_id, user_id," +
            " category, name, description, picture, status, date_of_posting FROM life_hack";
    static final String SQL_SELECT_LIFE_HACKS_BY_NAME = "SELECT l.life_hack_id, l.category," +
            " l.name, l.description, l.picture, l.status, l.date_of_posting, u.username AS username" +
            " FROM life_hack l JOIN user u ON l.user_id = u.user_id WHERE l.name like ? AND l.status = 1";

    static final String SQL_SELECT_OFFERED_LIFE_HACKS = "SELECT life_hack_id, user_id," +
            " category, name, description, picture, status, date_of_posting FROM life_hack" +
            " WHERE status = 0";
    static final String SQL_OFFER_LIFE_HACK = "INSERT INTO life_hack(user_id, category, name, description," +
            " picture, status, date_of_posting) VALUES (?, ?, ?, ?, ?, 0, ?)";
    static final String SQL_CREATE_LIFE_HACK = "INSERT INTO life_hack(user_id, category, name, description," +
            " picture, status, date_of_posting) VALUES (?, ?, ?, ?, ?, 1, ?)";
    static final String SQL_UPDATE_LIFE_HACK_BY_ID= "UPDATE life_hack SET category = ?, name = ?," +
            " description = ?, picture = ? WHERE life_hack_id = ?";
    static final String SQL_SUBMIT_LIFE_HACK = "UPDATE life_hack SET status = 1" +
            " WHERE life_hack_id = '%d'";
    static final String SQL_REJECT_LIFE_HACK = "UPDATE life_hack SET status = 2" +
            " WHERE life_hack_id = '%d'";
    static final String SQL_GET_COUNT_OF_LIFE_HACKS = "SELECT COUNT(*) FROM life_hack";
    static final String SQL_GET_COUNT_OF_SUBMITTED_LIFE_HACKS = "SELECT COUNT(*) FROM life_hack WHERE status = 1";
    static final String SQL_GET_COUNT_OF_REJECTED_LIFE_HACKS = "SELECT COUNT(*) FROM life_hack WHERE status = 2";
    static final String SQL_GET_COUNT_OF_OFFERED_LIFE_HACKS = "SELECT COUNT(*) FROM life_hack WHERE status = 0";
    static final String SQL_SELECT_ALL_SUBMITTED_LIFE_HACKS_WITH_USERNAME = "SELECT l.life_hack_id, l.category," +
            " l.name, l.description, l.picture, l.status, l.date_of_posting, u.username AS username " +
            "FROM life_hack l JOIN user u ON l.user_id = u.user_id WHERE l.status = 1";
    static final String SQL_SELECT_LIFE_HACK_WITH_USERNAME_BY_ID = "SELECT l.life_hack_id, l.category," +
            " l.name, l.description, l.picture, l.status, l.date_of_posting, u.username AS username " +
            "FROM life_hack l JOIN user u ON l.user_id = u.user_id WHERE l.life_hack_id = '%d'";
    static final String SQL_CREATE_FAVORITE_LIFE_HACK = "INSERT INTO favorite_life_hack(user_id, life_hack_id)" +
            " VALUES ('%d', '%d')";
    static final String SQL_DELETE_FAVORITE_LIFE_HACK = "DELETE FROM favorite_life_hack WHERE user_id = '%d'" +
            " AND life_hack_id = '%d'";
    static final String SQL_SELECT_FAVORITE_LIFE_HACKS_BY_USER_ID = "SELECT life_hack_id, user_id," +
            " category, name, description, picture, status, date_of_posting FROM life_hack " +
            "WHERE life_hack_id IN (SELECT life_hack_id FROM favorite_life_hack WHERE user_id = '%d')";
    static final String SQL_IS_LIFE_HACK_LIKED = "SELECT liked_life_hack_id FROM" +
            " favorite_life_hack WHERE user_id = '%d' AND life_hack_id = '%d'";

}
