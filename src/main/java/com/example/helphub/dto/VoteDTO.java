package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteDTO {

    private Long id;

    @NotNull
    private boolean upvote;

    @NotNull
    private Long userId;

    @NotNull
    private Long questionId;

}
