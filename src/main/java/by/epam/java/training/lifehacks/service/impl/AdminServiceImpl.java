package by.epam.java.training.lifehacks.service.impl;

import by.epam.java.training.lifehacks.dao.lifehack.LifeHackDaoImpl;
import by.epam.java.training.lifehacks.dao.user.UserDaoImpl;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.service.AdminService;
import by.epam.java.training.lifehacks.util.constant.Constant;
import by.epam.java.training.lifehacks.util.constant.PagePath;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;
import by.epam.java.training.lifehacks.weblayer.command.ResponseType;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public CommandResult goToAdminPanel(RequestContent requestContent) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        try {
            long countOfUsers = userDao.getCountOfUsers();
            long countOfActiveUsers = userDao.getCountOfActiveUsers();
            long countOfBlockedUsers = userDao.getCountOfBlockedUsers();
            long countOfLifeHacks = lifeHackDao.getCountOfLifeHacks();
            long countOfOfferedLifeHacks = lifeHackDao.getCountOfOfferedLifeHacks();
            long countOfSubmittedLifeHacks = lifeHackDao.getCountOfSubmittedLifeHacks();
            long countOfRejectedLifeHacks = lifeHackDao.getCountOfRejectedLifeHacks();

            requestContent.insertSessionAttribute(Constant.COUNT_OF_USERS, countOfUsers);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_ACTIVE_USERS, countOfActiveUsers);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_BLOCKED_USERS, countOfBlockedUsers);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_LIFE_HACKS, countOfLifeHacks);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_OFFERED_LIFE_HACKS, countOfOfferedLifeHacks);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_SUBMITTED_LIFE_HACKS, countOfSubmittedLifeHacks);
            requestContent.insertSessionAttribute(Constant.COUNT_OF_REJECTED_LIFE_HACKS, countOfRejectedLifeHacks);
        } catch (DaoException e) {
            throw new ServiceException("Error getting info about life hacks and users", e);
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.ADMIN_PANEL);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult showUsers(RequestContent requestContent) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<User> users;
        try {
            users = userDao.findUsers();
        } catch (DaoException e) {
            throw new ServiceException("Error find users", e);
        }

        requestContent.insertSessionAttribute(Constant.USERS, users);
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.USERS_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult blockUser(RequestContent requestContent) throws ServiceException {
        Long userId  =  Long.parseLong(requestContent.getRequestParameter("userId"));
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.block(userId);
        } catch (DaoException e) {
            throw new ServiceException("Error block user", e);
        }

        return showUsers(requestContent);
    }

    @Override
    public CommandResult unlockUser(RequestContent requestContent) throws ServiceException {
        Long userId  =  Long.parseLong(requestContent.getRequestParameter("userId"));
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.unlock(userId);
        } catch (DaoException e) {
            throw new ServiceException("Error unlock user", e);
        }

        return showUsers(requestContent);
    }

    @Override
    public CommandResult makeAdmin(RequestContent requestContent) throws ServiceException {
        Long userId  =  Long.parseLong(requestContent.getRequestParameter("userId"));
        UserDaoImpl userDao = UserDaoImpl.getInstance();


        try {
            userDao.makeAdmin(userId);
        } catch (DaoException e) {
            throw new ServiceException("Error make user admin", e);
        }

        return showUsers(requestContent);
    }
}
