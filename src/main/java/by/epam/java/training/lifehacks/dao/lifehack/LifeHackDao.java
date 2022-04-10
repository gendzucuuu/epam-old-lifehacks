package by.epam.java.training.lifehacks.dao.lifehack;

import by.epam.java.training.lifehacks.dao.AbstractDao;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.entity.LifeHack;

import java.util.List;

public interface LifeHackDao extends AbstractDao<LifeHack> {
//    List<LifeHack> findByName(String name) throws DaoException;
    boolean checkAddToFavorite(Long userId, Long lifeHackId) throws DaoException;
    List<LifeHack> findFavoriteLifeHacksByUserId(Long userId) throws DaoException;
    boolean createFavoriteLifeHack(Long userId, Long lifeHackId) throws DaoException;
    boolean delete(Long userId, Long lifeHackId) throws DaoException;
    List<LifeHack> findOfferedLifeHacks() throws DaoException;
    boolean submit(Long id) throws DaoException;
    boolean reject(Long id) throws DaoException;
    boolean offer(LifeHack lifeHack) throws DaoException;
    Long getCountOfLifeHacks() throws DaoException;
    Long getCountOfSubmittedLifeHacks() throws DaoException;
    Long getCountOfRejectedLifeHacks() throws DaoException;
    Long getCountOfOfferedLifeHacks() throws DaoException;


}
