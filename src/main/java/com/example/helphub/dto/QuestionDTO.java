package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
