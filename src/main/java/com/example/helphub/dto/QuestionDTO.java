package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionDTO {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String title;

    @NotNull
    @Size(min = 10)
    private String content;

    private LocalDateTime createdAt;
    private Long userId;
    private String username;

}
