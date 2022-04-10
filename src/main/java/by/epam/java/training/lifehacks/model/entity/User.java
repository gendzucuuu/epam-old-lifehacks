package by.epam.java.training.lifehacks.model.entity;


import by.epam.java.training.lifehacks.model.entityenum.UserRole;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long userId;
    private boolean isBlocked;
    private String username;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private UserRole role;




}
