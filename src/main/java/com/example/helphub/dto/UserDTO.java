package com.example.helphub.dto;

import com.example.helphub.role.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;

    @NotNull
    @Size(min = 6,message = "FirstName Should not be less than 6 character")
    private String firstName;

    private String lastName;

    @NotNull
    @Size(min = 3,message = "UserName should not be less than 3 character")
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    private Role role;

    @NotNull
    @Email
    private String email;

    private Integer reputation;

}
