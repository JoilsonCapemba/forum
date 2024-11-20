package br.com.alura.forum.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @NotBlank
        String name,

        String category) {
}
