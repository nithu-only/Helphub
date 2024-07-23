package com.example.helphub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TagDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

}
