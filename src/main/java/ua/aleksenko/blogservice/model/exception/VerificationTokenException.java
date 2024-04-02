package ua.aleksenko.blogservice.model.exception;

import lombok.Getter;

@Getter
public class VerificationTokenException extends RuntimeException {

    public VerificationTokenException(String message) {
        super(message);
    }
}
