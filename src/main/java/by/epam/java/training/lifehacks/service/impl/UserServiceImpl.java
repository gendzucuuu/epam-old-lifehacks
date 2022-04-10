package by.epam.java.training.lifehacks.service.impl;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.lifehacks.service.validation.UserValidation;
import by.epam.java.training.lifehacks.weblayer.command.CommandResult;
import by.epam.java.training.lifehacks.weblayer.command.RequestContent;
import by.epam.java.training.lifehacks.weblayer.command.ResponseType;
import by.epam.java.training.lifehacks.dao.user.UserDaoImpl;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.model.entity.User;
import by.epam.java.training.lifehacks.service.UserService;
import by.epam.java.training.lifehacks.util.constant.Constant;
import by.epam.java.training.lifehacks.util.constant.PagePath;
import by.epam.java.training.lifehacks.model.entityenum.UserRole;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


public class UserServiceImpl implements UserService {
//    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public CommandResult createUser(RequestContent requestContent) throws ServiceException {
        System.out.println("start");
        if (validate(requestContent)) {
            System.out.println("validated");
            if (isEmailNotExist(requestContent) & isUsernameNotExist(requestContent)) {
                User user = new User();

                createUserAttributes(user, requestContent);

                String password;
                password = getHashPassword(requestContent.getRequestParameter(Constant.PASS1));

                UserDaoImpl userDao = UserDaoImpl.getInstance();
                try {
                    userDao.create(user, password);
                } catch (DaoException e) {
                    throw new ServiceException("Error createLifeHack user", e);
                }

                requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.INDEX);
                return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
            }
            else {
                requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                        "Username or email exist.");
            }
        }

        requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR, "true");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.REGISTRATION_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult signIn(RequestContent requestContent) throws ServiceException {
        String password = getHashPassword(requestContent.getRequestParameter(Constant.PASSWORD));
        String username = requestContent.getRequestParameter(Constant.USERNAME);

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        UserRole userRole;
        try {
            userRole = userDao.checkUserRole(username, password);
        } catch (DaoException e) {
            throw new ServiceException("Error check role", e);
        }

        if (userRole != null) {
            User user;

            try {
                user = userDao.authorization(username, password);
            } catch (DaoException e) {
                throw new ServiceException("Error sign in", e);
            }

            if (user!= null) {
                insertUserSessionAttributes(requestContent, user, userRole);
                requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.INDEX);
                return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
            }

        }

        requestContent.insertSessionAttribute(Constant.SIGN_IN_ERROR,"true");
        requestContent.insertSessionAttribute(Constant.REDIRECT_PATH, PagePath.AUTHORIZATION_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);

    }

    @Override
    public CommandResult changeLocale(RequestContent requestContent) {
        if (requestContent.getSessionAttribute(Constant.LOCALE).toString().equals("ru_RU")) {
            requestContent.insertSessionAttribute(Constant.LOCALE, new Locale(Constant.EN_LOCALE_LANG, Constant.EN_LOCALE_COUNTRY));
        } else {
            requestContent.insertSessionAttribute(Constant.LOCALE, new Locale(Constant.RU_LOCALE_LANG, Constant.RU_LOCALE_COUNTRY));
        }


        return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
    }

    @Override
    public CommandResult logout(RequestContent requestContent) {
        if (requestContent.getSession() != null) {
            requestContent.getSession().invalidate();
        }

        requestContent.insertAttribute(Constant.REDIRECT_PATH, PagePath.INDEX);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    private void insertUserSessionAttributes(RequestContent requestContent, User user, UserRole userRole) {
        requestContent.insertSessionAttribute(Constant.USER, user);
        requestContent.insertSessionAttribute(Constant.ROLE, userRole);
    }

    private boolean isEmailNotExist (RequestContent requestContent) throws ServiceException {
        String email;

        if (requestContent.getRequestParameter("email") != null) {
            email = requestContent.getRequestParameter("email");

            UserDaoImpl userDao = UserDaoImpl.getInstance();
            try {
                if (userDao.isEmailNotExist(email)) {
                    return true;
                } else {
                    requestContent.insertAttribute(Constant.EMAIL_EXIST_ERROR, "Email exist");//TODO localization
                }
            } catch (DaoException e) {
                throw new ServiceException("Error check email",e);
            }
        } else {
            requestContent.insertAttribute(Constant.EMAIL_EXIST_ERROR, "Email can't be null");
        }

        return false;
    }

    private boolean isUsernameNotExist (RequestContent requestContent) throws ServiceException {
        String username;

        if (requestContent.getRequestParameter("username") != null) {
            username = requestContent.getRequestParameter("username");
            UserDaoImpl userDao = UserDaoImpl.getInstance();

            try {
                if (userDao.isUsernameNotExist(username)) {
                    return true;
                } else {
                    requestContent.insertAttribute(Constant.USERNAME_EXIST_ERROR, "Username exist");//TODO localization
                }
            } catch (DaoException e) {
                throw new ServiceException("Error check username", e);
            }

        } else {
            requestContent.insertAttribute(Constant.USERNAME_EXIST_ERROR, "Username can't be null");
        }
        return false;
    }


    private String getHashPassword(String pass) throws ServiceException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            StringBuilder stringBuilder = new StringBuilder();
            byte[] passBytes = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));

            for (byte passByte : passBytes) {
                stringBuilder.append(passByte);
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e){
            throw new ServiceException("Can't hash password", e);
        }

    }

    private boolean validate(RequestContent requestContent) {
        String username = requestContent.getRequestParameter("username");
        if (!UserValidation.validateUsername(username)) {
            requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                    "Check username.");
            return false;
        }
        String firstName = requestContent.getRequestParameter("first-name");
        String secondName = requestContent.getRequestParameter("second-name");

        if (!UserValidation.validateName(firstName) && !UserValidation.validateName(secondName)) {
            requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                    " Check first and second name.");
            return false;

        }

        String email = requestContent.getRequestParameter("email");
        if (!UserValidation.validateEmail(email)) {
            requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                    "Check email.");
            return false;

        }

        String pass1 = requestContent.getRequestParameter("pass1");
        String pass2 = requestContent.getRequestParameter("pass2");
        if (!UserValidation.validatePassword(pass1) && !UserValidation.validatePassword(pass2)) {
            requestContent.insertSessionAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                    "Check passwords.");
            return false;
        }

        if (!pass1.equals(pass2)) {
            requestContent.insertAttribute(Constant.REGISTRATION_ERROR_MESSAGE, "Registration failed. " +
                    "Passwords must match.");
            return false;
        }

        return true;
    }

    private void createUserAttributes(User user, RequestContent requestContent) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
        String username = requestContent.getRequestParameter("username");
        user.setUsername(username);

        String email = requestContent.getRequestParameter("email");
        user.setEmail(email);

        String firstName = requestContent.getRequestParameter("first-name");
        user.setFirstName(firstName);

        String secondName = requestContent.getRequestParameter("second-name");
        user.setSecondName(secondName);

    }

}
