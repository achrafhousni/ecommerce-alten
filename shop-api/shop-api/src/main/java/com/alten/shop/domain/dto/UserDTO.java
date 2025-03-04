package com.alten.shop.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    @NotEmpty(message = "UserName name cannot be empty")
    private String userName;
    @NotEmpty(message = "FirstName name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Email name cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @JsonIgnore
    private String password;
}
