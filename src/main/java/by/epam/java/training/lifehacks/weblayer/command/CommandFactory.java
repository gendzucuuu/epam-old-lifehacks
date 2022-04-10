package by.epam.java.training.lifehacks.weblayer.command;


import by.epam.java.training.lifehacks.util.constant.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@NoArgsConstructor
public class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    @Getter
    private static final CommandFactory instance = new CommandFactory();

    public Command getCommand(RequestContent content){
        if (content.getRequestParameter(Constant.COMMAND) != null) {
            String name = content.getRequestParameter(Constant.COMMAND).toUpperCase();
            try {
                CommandType commandType = CommandType.valueOf(name);
                return commandType.getCommand();
            } catch (EnumConstantNotPresentException | IllegalArgumentException e){
                logger.log(Level.ERROR, "Can't define command "+"'"+name+"'");
                return CommandType.START.getCommand();
            }
        }
        else return null;
    }
}
