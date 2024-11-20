package br.com.alura.forum.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public enum TopicStatus {
    NOT_ANSWERED,
    NOT_SOLVED,
    SOLVED,
    CLOSED;


}
