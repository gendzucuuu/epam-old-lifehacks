package by.epam.java.training.lifehacks.service.impl;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.model.dto.UserCommentDto;
import by.epam.java.training.lifehacks.service.LifeHackService;
import by.epam.java.training.lifehacks.service.validation.LifeHackValidation;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;
import by.epam.java.training.lifehacks.weblayer.command.ResponseType;
import by.epam.java.training.lifehacks.dao.comment.CommentDaoImpl;
import by.epam.java.training.lifehacks.dao.lifehack.LifeHackDaoImpl;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.dto.UserLifeHackDto;
import by.epam.java.training.lifehacks.model.entity.LifeHack;
import by.epam.java.training.lifehacks.model.entityenum.LifeHackCategory;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.util.constant.Constant;
import by.epam.java.training.lifehacks.util.constant.PagePath;
import by.epam.java.training.lifehacks.model.entityenum.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@MultipartConfig
public class LifeHackServiceImpl implements LifeHackService {

    @Override
    public CommandResult createLifeHack(RequestContent requestContent) throws ServiceException {
        if (validateNameAndDescription(requestContent, Constant.ADD_LIFE_HACK_ERROR_MESSAGE) &&
                validatePictureForCreate(requestContent, Constant.ADD_LIFE_HACK_ERROR_MESSAGE)) {
            LifeHack lifeHack = new LifeHack();
            createLifeHackAttributes(lifeHack, requestContent);


            LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
            try {
                lifeHackDao.create(lifeHack);
            } catch (DaoException e) {
                throw new ServiceException("Error create life hack", e);
            }

            requestContent.insertSessionAttribute(Constant.ADD_LIFE_HACK_ERROR, "false");
            requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.INDEX);
            return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
        }

        requestContent.insertSessionAttribute(Constant.ADD_LIFE_HACK_ERROR, "true");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.CREATE_LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult findAllSubmittedLifeHacks(RequestContent requestContent) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            List<UserLifeHackDto> lifeHacks = lifeHackDao.findAllUserLifeHackDto();
            requestContent.insertSessionAttribute(Constant.LIFE_HACKS, lifeHacks);
        } catch (DaoException e) {
            throw new ServiceException("Error find all submitted life hacks", e);
        }
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.START_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

//    public CommandResult findAll(RequestContent requestContent) {
//        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
//        try {
//            List<LifeHack> lifeHacks = lifeHackDao.findAll();
//            requestContent.insertSessionAttribute(Constant.LIFE_HACKS, lifeHacks);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.START_PAGE);
//        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
//    }

    @Override
    public CommandResult goToLifeHackProfile(RequestContent requestContent) throws ServiceException {
        Long id = Long.parseLong(requestContent.getRequestParameter("lifeHackId"));
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();

        UserLifeHackDto lifeHack = null;
        try {
            lifeHack = lifeHackDao.findUserLifeHackDtoById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error find user life hack", e);
        }


        if (requestContent.getSessionAttribute(Constant.ROLE) != UserRole.GUEST) {
            User user  = (User) requestContent.getSessionAttribute(Constant.USER);
            Long userId = user.getUserId();

            try {
                if (lifeHackDao.checkAddToFavorite(userId, lifeHack.getLifeHackId())) {
                    requestContent.insertSessionAttribute(Constant.IS_ADDED, "added");
                } else {
                    requestContent.insertSessionAttribute(Constant.IS_ADDED, "notAdded");
                }
            } catch (DaoException e) {
                throw new ServiceException("Error check life hack add to favorite", e);
            }

            CommentDaoImpl commentDao = CommentDaoImpl.getInstance();

            List<UserCommentDto> comments = null;
            try {
                comments = commentDao.findByLifeHackId(lifeHack.getLifeHackId());
                System.out.println(comments);
            } catch (DaoException e) {
                throw new ServiceException("Error find comments by life hack id", e);
            }

            requestContent.insertSessionAttribute(Constant.COMMENTS, comments);
            requestContent.insertSessionAttribute(Constant.COMMENTS_STATUS, "notShowed");
        }

        requestContent.insertSessionAttribute(Constant.LIFE_HACK, lifeHack);
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult offerLifeHack(RequestContent requestContent) throws ServiceException {
        LifeHack lifeHack = new LifeHack();

        if (validateNameAndDescription(requestContent, Constant.OFFER_LIFE_HACK_ERROR_MESSAGE)
                && validatePictureForCreate(requestContent, Constant.OFFER_LIFE_HACK_ERROR_MESSAGE)) {
            createLifeHackAttributes(lifeHack, requestContent);

            LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
            try {
                lifeHackDao.offer(lifeHack);
            } catch (DaoException e) {
                throw new ServiceException("Error offer life hack", e);
            }

            requestContent.insertSessionAttribute(Constant.OFFER_LIFE_HACK_ERROR, "false");
            requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.INDEX);
            return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
        }

        requestContent.insertSessionAttribute(Constant.OFFER_LIFE_HACK_ERROR, "true");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.OFFER_LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult findLifeHacksByName(RequestContent requestContent) throws ServiceException {
        String searchString = null;

        if (requestContent.getRequestParameter("searchString") != null) {
            searchString = requestContent.getRequestParameter("searchString");
        }

        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            List<UserLifeHackDto> lifeHacks = lifeHackDao.findByName(searchString);
            requestContent.insertSessionAttribute(Constant.LIFE_HACKS, lifeHacks);
        } catch (DaoException e) {
            throw new ServiceException("Error find life hack by name", e);
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.START_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult addLifeHackToFavorite(RequestContent requestContent) throws ServiceException {
        User user  = (User) requestContent.getSessionAttribute(Constant.USER);

        Long userId = user.getUserId();
        Long lifeHackId  =  Long.parseLong(requestContent.getRequestParameter("lifeHackId"));

        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.createFavoriteLifeHack(userId, lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error create add life hack to favorite", e);
        }

        requestContent.insertSessionAttribute(Constant.IS_ADDED, "added");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult deleteLifeHackFromFavorite(RequestContent requestContent) throws ServiceException {
        User user  = (User) requestContent.getSessionAttribute(Constant.USER);

        Long userId = user.getUserId();
        Long lifeHackId  =  Long.parseLong(requestContent.getRequestParameter("lifeHackId"));


        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.delete(userId, lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error delete favorite life hack", e);
        }

        requestContent.insertSessionAttribute(Constant.IS_ADDED, "notAdded");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }


    public CommandResult findAllFavoriteLifeHacks(RequestContent requestContent) throws ServiceException {
        User user  = (User) requestContent.getSessionAttribute(Constant.USER);
        Long userId = user.getUserId();

        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            List<LifeHack> lifeHacks = lifeHackDao.findFavoriteLifeHacksByUserId(userId);
            requestContent.insertSessionAttribute(Constant.FAVORITE_LIFE_HACKS, lifeHacks);
        } catch (DaoException e) {
            throw new ServiceException("Error find all favorite life hacks", e);
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.FAVORITE_LIFE_HACKS_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult findOfferedLifeHacks(RequestContent requestContent) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        List<LifeHack> lifeHacks;
        try {
            lifeHacks = lifeHackDao.findOfferedLifeHacks();
            requestContent.insertSessionAttribute(Constant.OFFERED_LIFE_HACKS, lifeHacks);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.OFFERED_LIFE_HACKS);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult goToLifeHacksAdminPage(RequestContent requestContent) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            List<LifeHack> lifeHacks = lifeHackDao.findAll();
            requestContent.insertSessionAttribute(Constant.LIFE_HACKS, lifeHacks);
        } catch (DaoException e) {
            throw new ServiceException("Error find all life hacks", e);
        }

        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.LIFE_HACKS_ADMIN_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult confirmLifeHack(RequestContent requestContent) throws ServiceException {
        Long lifeHackId = Long.parseLong(requestContent.getRequestParameter("lifeHackId"));

        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.submit(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error submit life hack", e);
        }

        String to = requestContent.getRequestParameter("to");
        if (to.equals("admin")) {
            return goToLifeHacksAdminPage(requestContent);
        } else {
            return findOfferedLifeHacks(requestContent);
        }
    }

    @Override
    public CommandResult rejectLifeHack(RequestContent requestContent) throws ServiceException {
        Long lifeHackId = Long.parseLong(requestContent.getRequestParameter("lifeHackId"));

        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.reject(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error reject life hack", e);
        }

        String to = requestContent.getRequestParameter("to");
        if (to.equals("admin")) {
            return goToLifeHacksAdminPage(requestContent);
        } else {
            return findOfferedLifeHacks(requestContent);
        }
    }

    @Override
    public CommandResult goToLifeHackEditForm(RequestContent requestContent) throws ServiceException {
        Long lifeHackId = Long.parseLong(requestContent.getRequestParameter("lifeHackId"));

        LifeHack lifeHack;
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHack = lifeHackDao.findById(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Error find life hack by id" , e);
        }

        requestContent.insertSessionAttribute(Constant.LIFE_HACK, lifeHack);
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.EDIT_LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult editLifeHack(RequestContent requestContent) throws ServiceException {
        LifeHack lifeHack  = (LifeHack) requestContent.getSessionAttribute(Constant.LIFE_HACK);

        if (validateNameAndDescription(requestContent, Constant.EDIT_LIFE_HACK_ERROR_MESSAGE)) {
            String category = requestContent.getRequestParameter("category");
            lifeHack.setLifeHackCategory(LifeHackCategory.valueOf(category));

            String name = requestContent.getRequestParameter("name");
            lifeHack.setName(name);

            String description = requestContent.getRequestParameter("description");
            lifeHack.setDescription(description);

            if (validatePictureForEdit(requestContent)) {
                lifeHack.setPicture(lifeHack.getPicture());
            } else if (validatePictureForCreate(requestContent, Constant.EDIT_LIFE_HACK_ERROR_MESSAGE)) {
                try {
                    lifeHack.setPicture(readFile(requestContent.getRequest().getPart("picture").getInputStream()));
                } catch (IOException | ServletException e) {
                    throw new ServiceException(e);
                }
            } else {
                requestContent.insertSessionAttribute(Constant.EDIT_LIFE_HACK_ERROR, "true");
                requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.EDIT_LIFE_HACK_PAGE);
                return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
            }

            Long lifeHackId = lifeHack.getLifeHackId();
            LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
            try {
                lifeHackDao.updateById(lifeHackId, lifeHack);
            } catch (DaoException e) {
                throw new ServiceException("Error update life hack", e);
            }

            requestContent.insertSessionAttribute(Constant.EDIT_LIFE_HACK_ERROR, "false");
            requestContent.insertSessionAttribute(Constant.ADD_LIFE_HACK_ERROR, "true");
            return goToLifeHacksAdminPage(requestContent);
        }

        requestContent.insertSessionAttribute(Constant.EDIT_LIFE_HACK_ERROR, "true");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.EDIT_LIFE_HACK_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }


    private boolean validateNameAndDescription(RequestContent requestContent, String constant) throws ServiceException {
        String name = requestContent.getRequestParameter("name");
        if (!LifeHackValidation.validateName(name)) {
            requestContent.insertSessionAttribute(constant, "Invalid data. " +
                    "Check name of life hack.");
            return false;
        }

        String description = requestContent.getRequestParameter("description");
        if (!LifeHackValidation.validateDescription(description)) {
            requestContent.insertSessionAttribute(constant, "Invalid data. " +
                    "Check description of life hack.");
            return false;
        }

        return true;
}

    private boolean validatePictureForCreate(RequestContent requestContent, String constant) throws ServiceException {
        try {
            if (requestContent.getRequest().getPart("picture").getSize() < Constant.MIN_IMAGE_SIZE) {
                requestContent.insertSessionAttribute(constant, "Invalid data. " +
                        "Check picture size(should be > 10 bytes) of life hack.");
                return false;
            }
        } catch (IOException | ServletException e) {
            throw new ServiceException(e);
        }

        return true;
    }

    private boolean validatePictureForEdit(RequestContent requestContent) throws ServiceException {
        try {
            if (requestContent.getRequest().getPart("picture").getSize() == Constant.ZERO_IMAGE_SIZE) {
                return true;
            } else if(requestContent.getRequest().getPart("picture").getSize() < Constant.MIN_IMAGE_SIZE &&
                    requestContent.getRequest().getPart("picture").getSize() > Constant.MIN_IMAGE_SIZE) {
                requestContent.insertSessionAttribute(Constant.EDIT_LIFE_HACK_ERROR_MESSAGE, "Invalid data. " +
                        "Check picture size(should be > 10 bytes) of life hack.");
                return false;
            }
        } catch (IOException | ServletException e) {
            throw new ServiceException(e);
        }

        return false;
    }

    private void createLifeHackAttributes(LifeHack lifeHack, RequestContent requestContent) throws ServiceException {
        User user = (User) requestContent.getSessionAttribute(Constant.USER);
        Long id = user.getUserId();
        lifeHack.setUserId(id);

        String category = requestContent.getRequestParameter("category");
        lifeHack.setLifeHackCategory(LifeHackCategory.valueOf(category));

        String name = requestContent.getRequestParameter("name");
        lifeHack.setName(name);

        String description = requestContent.getRequestParameter("description");
        lifeHack.setDescription(description);

        try {
            lifeHack.setPicture(readFile(requestContent.getRequest().getPart("picture").getInputStream()));
        } catch (IOException | ServletException e) {
            throw new ServiceException(e);
        }

        lifeHack.setDateOfPosting(LocalDateTime.now());
    }

    private byte[] readFile(InputStream input) throws IOException
    {
        System.out.println("start read");
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
