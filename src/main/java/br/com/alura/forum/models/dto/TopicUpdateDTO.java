package br.com.alura.forum.models.dto;

import br.com.alura.forum.models.TopicStatus;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record TopicUpdateDTO(
        @NotBlank @NotEmpty
        String title,
        @NotNull @Length(min = 10)
        String message,

        @NotNull
        TopicStatus status
) {
}
