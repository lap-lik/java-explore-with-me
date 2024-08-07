package ru.practicum.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidException extends RuntimeException {

    private final String message;
}
