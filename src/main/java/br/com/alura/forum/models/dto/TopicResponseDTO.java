package br.com.alura.forum.models.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicResponseDTO(
        UUID id,
        String title,
        String message,
        String status,
        LocalDateTime createdAt
) {
}
