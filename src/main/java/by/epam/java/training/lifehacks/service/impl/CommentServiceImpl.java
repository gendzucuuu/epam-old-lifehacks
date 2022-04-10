package by.epam.java.training.lifehacks.service.impl;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.model.dto.UserCommentDto;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.service.CommentService;
import by.epam.java.training.lifehacks.service.validation.CommentValidation;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;
import by.epam.java.training.lifehacks.weblayer.command.ResponseType;
import by.epam.java.training.lifehacks.dao.comment.CommentDaoImpl;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.entity.Comment;
import by.epam.java.training.lifehacks.util.constant.Constant;
import by.epam.java.training.lifehacks.util.constant.PagePath;

import java.time.LocalDateTime;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Override
    public CommandResult createComment(RequestContent requestContent) throws ServiceException {
        String description  =  requestContent.getRequestParameter("comment");
        if (CommentValidation.validateComment(description)) {
            Long lifeHackId  =  Long.parseLong(requestContent.getRequestParameter("lifeHackId"));

            User user  = (User) requestContent.getSessionAttribute(Constant.USER);
            Long userId = user.getUserId();



            Comment comment = new Comment();
            comment.setLifeHackId(lifeHackId);
            comment.setUserId(userId);
            comment.setDescription(description);
            comment.setDateOfComment(LocalDateTime.now());

            List<UserCommentDto> comments;
            CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
            try {
                commentDao.create(comment);
                comments = commentDao.findByLifeHackId(lifeHackId);
            } catch (DaoException e) {
                throw new ServiceException("Error create or find comments", e);
            }


            requestContent.insertSessionAttribute(Constant.ADD_COMMENT_ERROR, "false");
            requestContent.insertSessionAttribute(Constant.COMMENTS, comments);
        }
        else {
            requestContent.insertSessionAttribute(Constant.ADD_COMMENT_ERROR, "true");
            requestContent.insertSessionAttribute(Constant.ADD_COMMENT_ERROR_MESSAGE, "Add comment failed. " +
                    "Comment cannot be empty.");
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult showComments(RequestContent requestContent) {
        requestContent.insertSessionAttribute(Constant.COMMENTS_STATUS, "showed");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult hideComments(RequestContent requestContent) {
        requestContent.insertSessionAttribute(Constant.COMMENTS_STATUS, "notShowed");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult deleteComment(RequestContent requestContent) throws ServiceException{
        Long lifeHackId = Long.parseLong(requestContent.getRequestParameter("lifeHackId"));
        Long commentId  =  Long.parseLong(requestContent.getRequestParameter("commentId"));


        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        List<UserCommentDto> comments;
        try {
            commentDao.deleteById(commentId);
            comments = commentDao.findByLifeHackId(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error delete or find comments", e);
        }


        requestContent.insertSessionAttribute(Constant.COMMENTS, comments);
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }
}
