package by.epam.java.training.lifehacks.service;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;
import org.omg.CORBA.portable.ApplicationException;

public interface UserService {
    CommandResult createUser(RequestContent requestContent) throws ServiceException;
    CommandResult signIn (RequestContent requestContent) throws ServiceException;
    CommandResult changeLocale(RequestContent requestContent);
    CommandResult logout (RequestContent requestContent);
}
