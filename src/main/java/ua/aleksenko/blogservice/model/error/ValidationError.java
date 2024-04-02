package ua.aleksenko.blogservice.model.error;

import java.util.Map;
import lombok.Getter;

@Getter
public class ValidationError extends ApiError {

    private final Map<String, String> errors;

    public ValidationError(int code, String message, Map<String, String> errors) {
        super(code, message);
        this.errors = errors;
    }
}
