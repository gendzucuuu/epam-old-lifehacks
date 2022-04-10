package by.epam.java.training.lifehacks.weblayer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommandResult {
    private ResponseType responseType;
    private String page;
}
