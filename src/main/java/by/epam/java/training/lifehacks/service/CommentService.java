package by.epam.java.training.lifehacks.service;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;

public interface CommentService {
    CommandResult createComment(RequestContent requestContent) throws ServiceException;
    CommandResult showComments(RequestContent requestContent);
    CommandResult hideComments(RequestContent requestContent);
    CommandResult deleteComment(RequestContent requestContent) throws ServiceException;
}
