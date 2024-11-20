package br.com.alura.forum.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TopicRequestDTO(
        @NotBlank @NotEmpty
        String title,
        @NotNull @Length(min = 10)
        String message,

        @NotBlank @NotEmpty @Length(min = 2)
        String courseName
) {
}
