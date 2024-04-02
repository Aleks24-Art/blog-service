package ua.aleksenko.blogservice.service;

public interface TokenService {

    void validateExpirationDate(String token);

    void validateToken(String token);
}
