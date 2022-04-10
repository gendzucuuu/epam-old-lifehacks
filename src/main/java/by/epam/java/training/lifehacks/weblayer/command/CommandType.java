package by.epam.java.training.lifehacks.weblayer.command;

import by.epam.java.training.lifehacks.service.impl.AdminServiceImpl;
import by.epam.java.training.lifehacks.service.impl.CommentServiceImpl;
import by.epam.java.training.lifehacks.service.impl.LifeHackServiceImpl;
import by.epam.java.training.lifehacks.service.impl.UserServiceImpl;

public enum     CommandType {
    START(new LifeHackServiceImpl()::findAllSubmittedLifeHacks),
    AUTHORIZATION(new UserServiceImpl()::signIn),
    CHANGE_LOCALE(new UserServiceImpl()::changeLocale),
    REGISTER_USER(new UserServiceImpl()::createUser),
    GO_TO_LIFE_HACK_PAGE(new LifeHackServiceImpl()::goToLifeHackProfile),
    ADD_TO_FAVORITE(new LifeHackServiceImpl()::addLifeHackToFavorite),
    DELETE_FROM_FAVORITE(new LifeHackServiceImpl()::deleteLifeHackFromFavorite),
    FIND_ALL_FAVORITE_LIFE_HACKS(new LifeHackServiceImpl()::findAllFavoriteLifeHacks),
    ADD_COMMENT(new CommentServiceImpl()::createComment),
    SHOW_COMMENTS(new CommentServiceImpl()::showComments),
    HIDE_COMMENTS(new CommentServiceImpl()::hideComments),
    DELETE_COMMENT(new CommentServiceImpl()::deleteComment),
    MANAGE_USERS(new AdminServiceImpl()::showUsers),
    BLOCK_USER(new AdminServiceImpl()::blockUser),
    UNLOCK_USER(new AdminServiceImpl()::unlockUser),
    MAKE_ADMIN(new AdminServiceImpl()::makeAdmin),
    MANAGE_LIFE_HACKS(new LifeHackServiceImpl()::goToLifeHacksAdminPage),
    CONFIRM_LIFE_HACK(new LifeHackServiceImpl()::confirmLifeHack),
    REJECT_LIFE_HACK(new LifeHackServiceImpl()::rejectLifeHack),
    GO_TO_LIFE_HACK_EDIT_FORM(new LifeHackServiceImpl()::goToLifeHackEditForm),
    EDIT_LIFE_HACK(new LifeHackServiceImpl()::editLifeHack),
    GO_TO_ADMIN_PANEL(new AdminServiceImpl()::goToAdminPanel),
    SEARCH_BY_NAME(new LifeHackServiceImpl()::findLifeHacksByName),
    LOGOUT(new UserServiceImpl()::logout),
    USERS_OFFERS(new LifeHackServiceImpl()::findOfferedLifeHacks),
    FIND_ALL_SUBMITTED_LIFE_HACKS(new LifeHackServiceImpl()::findAllSubmittedLifeHacks),
    CREATE_LIFE_HACK(new LifeHackServiceImpl()::createLifeHack),
    OFFER_LIFE_HACK(new LifeHackServiceImpl()::offerLifeHack);


    private Command command;

    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
