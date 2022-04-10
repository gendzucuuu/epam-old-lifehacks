package by.epam.java.training.lifehacks.model.entity;

import by.epam.java.training.lifehacks.model.entityenum.LifeHackCategory;
import lombok.*;

import java.time.LocalDateTime;


@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LifeHack {
    private Long lifeHackId;
    private Long userId;
    private String name;
    private LifeHackCategory lifeHackCategory;
    private String description;
    private byte[] picture;
    private String pictureEnc;
    private int status;
    private LocalDateTime dateOfPosting;

}
