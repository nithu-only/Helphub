package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QuestionTagDTO {

    private Long id;

    @NotNull
    private Long questionId;

    @NotNull
    private Long tagId;
}
