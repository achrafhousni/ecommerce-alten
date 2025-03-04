package com.alten.shop.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name="USERS")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotEmpty(message = "UserName name cannot be empty")
   private String userName;
   @NotEmpty(message = "FirstName name cannot be empty")
   private String firstName;
   @NotEmpty(message = "Email name cannot be empty")
   @Email(message = "Invalid email. Please enter a valid email address")
   private String email;
   @NotEmpty(message = "Password cannot be empty")
   private String password;


}
