package ru.practicum.ewmmain.exception.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.ewmmain.category.controller.CategoryAdminController;
import ru.practicum.ewmmain.category.controller.CategoryPublicController;
import ru.practicum.ewmmain.exception.NotFoundException;
import ru.practicum.ewmmain.exception.NotImplementedException;
import ru.practicum.ewmmain.exception.UnsupportedException;
import ru.practicum.ewmmain.exception.ValidException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.practicum.ewmmain.constant.Constant.DATE_TIME_PATTERN;

@Slf4j
@RestControllerAdvice(assignableTypes = {CategoryAdminController.class, CategoryPublicController.class})
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidException.class, ConstraintViolationException.class})
    public ErrorResponse onValidException(final RuntimeException exception) {

        log.warn("Exception1: {}, Validation error(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));
        LocalDateTime now = LocalDateTime.now();

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .timestamp(DATE_TIME_PATTERN)
                .build();
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ErrorResponse onConstraintViolationException(final RuntimeException exception) {
//
//        log.warn("Exception1: {}, ConstraintViolation error(s): \n{}", exception.getClass().getName(),
//                getExceptionMessage(exception));
//
//        return ErrorResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.name())
//                .reason("Incorrectly made request.")
//                .message(exception.getMessage())
//                .timestamp(LocalDateTime.now().toString().replace("T", " "))
//                .build();
//    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnsupportedException.class})
    public ErrorResponse onUnsupportedException(final RuntimeException exception) {

        log.warn("Exception: {}, Unsupported error(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().toString().replace("T", " "))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse onNotFoundException(final NotFoundException exception) {

        log.warn("Exception: {}, Not found: \n{}", exception.getClass().getName(), getExceptionMessage(exception));

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().toString().replace("T", " "))
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse onDataIntegrityViolationException(final DataIntegrityViolationException exception) {

        log.warn("DataIntegrityViolationException: {}, message(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().toString().replace("T", " "))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NotImplementedException.class, Exception.class})
    public ErrorResponse onThrowableException(final Exception exception) {

        log.error("Exception: {}, message(s): \n{}", exception.getClass().getName(), getExceptionMessage(exception));

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().toString().replace("T", " "))
                .build();
    }

    private String getExceptionMessage(Throwable exception) {

        return Arrays.stream(exception.getMessage().split("&"))
                .map(message -> "- " + message.trim())
                .collect(Collectors.joining("\n"));
    }
}