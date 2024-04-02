package ua.aleksenko.blogservice.controller;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.aleksenko.blogservice.model.error.ApiError;
import ua.aleksenko.blogservice.model.error.ValidationError;


@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    private static final String CONSTRAINT_VIOLATION_MESSAGE = "Validation failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        o -> Optional.ofNullable(o.getDefaultMessage()).orElse("Message is unavailable")
                ));
        log.error("MethodArgumentNotValidException handled due to errors: {}", errors);
        return new ValidationError(HttpStatus.BAD_REQUEST.value(), CONSTRAINT_VIOLATION_MESSAGE, errors);
    }


	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiError handleAccessDeniedException(AccessDeniedException e) {
		log.error("AccessDeniedException handled with error: ", e);
		return new ApiError(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAllOtherException(RuntimeException e) {
        log.error("RuntimeException handled with error: ", e);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
