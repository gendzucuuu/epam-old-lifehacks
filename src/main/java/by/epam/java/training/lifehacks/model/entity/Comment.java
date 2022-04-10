package by.epam.java.training.lifehacks.model.entity;


import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private Long commentId;
    private Long userId;
    private Long lifeHackId;
    private String description;
    private LocalDateTime dateOfComment;
}
