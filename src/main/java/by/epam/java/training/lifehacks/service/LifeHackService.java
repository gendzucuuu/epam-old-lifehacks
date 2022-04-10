package by.epam.java.training.lifehacks.service;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;

public interface LifeHackService {
    CommandResult createLifeHack(RequestContent requestContent) throws ServiceException;
    CommandResult findAllSubmittedLifeHacks(RequestContent requestContent) throws ServiceException;
    CommandResult goToLifeHackProfile(RequestContent requestContent) throws ServiceException;
    CommandResult offerLifeHack(RequestContent requestContent) throws ServiceException;
    CommandResult findLifeHacksByName(RequestContent requestContent) throws ServiceException;
    CommandResult addLifeHackToFavorite(RequestContent requestContent) throws ServiceException;
    CommandResult deleteLifeHackFromFavorite(RequestContent requestContent) throws ServiceException;
    CommandResult findOfferedLifeHacks(RequestContent requestContent) throws ServiceException;
    CommandResult goToLifeHacksAdminPage(RequestContent requestContent) throws ServiceException;
    CommandResult confirmLifeHack(RequestContent requestContent) throws ServiceException;
    CommandResult rejectLifeHack(RequestContent requestContent) throws ServiceException;
    CommandResult goToLifeHackEditForm(RequestContent requestContent) throws ServiceException;
    CommandResult editLifeHack(RequestContent requestContent) throws ServiceException;
}
