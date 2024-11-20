package br.com.alura.forum.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserDTO(
        @NotNull
        String name,
        @Email
        String email,
        @NotNull @Length(min = 6)
        String password) {
}
