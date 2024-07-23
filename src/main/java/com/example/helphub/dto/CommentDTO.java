package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;

    @NotNull
    @Size(min = 10)
    private String content;

    private Long userId;
    private Long answerId;

    private String username;
    private LocalDateTime createdAt;

}
