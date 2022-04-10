package by.epam.java.training.lifehacks.service;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;

public interface AdminService {
    CommandResult goToAdminPanel(RequestContent requestContent) throws ServiceException;
    CommandResult showUsers(RequestContent requestContent) throws ServiceException;
    CommandResult blockUser(RequestContent requestContent) throws ServiceException;
    CommandResult unlockUser(RequestContent requestContent) throws ServiceException;
    CommandResult makeAdmin(RequestContent requestContent) throws ServiceException;
}
