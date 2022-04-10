package by.epam.java.training.lifehacks.dao.user;

import by.epam.java.training.lifehacks.dao.AbstractDao;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.model.entityenum.UserRole;

import java.util.List;

public interface UserDao extends AbstractDao<User> {
    boolean isEmailNotExist (String email) throws DaoException;
    boolean isUsernameNotExist (String username) throws DaoException;
    UserRole checkUserRole(String username, String password) throws DaoException;
    User authorization(String username, String password) throws DaoException;
    List<User> findUsers() throws DaoException;
    boolean block(Long userId) throws DaoException;
    boolean unlock(Long userId) throws DaoException;
    boolean makeAdmin(Long userId) throws DaoException;
    boolean create(User user, String pass) throws DaoException;
    Long getCountOfUsers() throws DaoException;
    Long getCountOfActiveUsers() throws DaoException;
    Long getCountOfBlockedUsers() throws DaoException;

}
