package by.epam.java.training.lifehacks.dao.comment;

import by.epam.java.training.lifehacks.dao.AbstractDao;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.dto.UserCommentDto;
import by.epam.java.training.lifehacks.model.entity.Comment;

import java.util.List;

public interface CommentDao extends AbstractDao<Comment> {
    List<UserCommentDto> findByLifeHackId(Long lifeHackId) throws DaoException;
}
