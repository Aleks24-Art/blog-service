package ua.aleksenko.blogservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.aleksenko.blogservice.model.error.ApiError;
import ua.aleksenko.blogservice.model.exception.VerificationTokenException;
import ua.aleksenko.blogservice.property.TokenIssuerProperties;
import ua.aleksenko.blogservice.service.TokenService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final TokenIssuerProperties tokenIssuerProperties;

	@Override
	public void validateExpirationDate(String token) {
		log.info("Start validation token expiration date");

		Instant expiresAt = JWT.decode(token).getExpiresAt().toInstant();
		if (expiresAt.isBefore(Instant.now())) {
			throw new TokenExpiredException("Token has been expired", expiresAt);
		}
		log.info("Finish validation token expiration date");
	}

	@Override
	public void validateToken(String token) {
		log.info("Start validation token signature");
		WebClient.create(tokenIssuerProperties.getBaseUrl())
				.get()
				.uri(uriBuilder -> uriBuilder.path(tokenIssuerProperties.getTokenValidationEndpoint())
						.queryParam("token", token)
						.build())
				.retrieve()
				.onStatus(HttpStatus::is2xxSuccessful, response -> {
					log.info("Validation passed, response: {}", response);
					return Mono.empty();
				})
				.onStatus(HttpStatus::isError,
						response -> {
							if (HttpStatus.UNAUTHORIZED.equals(response.statusCode())) {
								Mono<ApiError> bodyToMono = response.bodyToMono(ApiError.class);
								return bodyToMono.flatMap(
										apiError -> Mono.error(
												new VerificationTokenException(apiError.getMessage())
										)
								);
							} else {
								return Mono.error(
										new RuntimeException("Error while verifying token signature")
								);
							}
						}).bodyToMono(Void.class)
				.block();
		log.info("Finish validation token signature");
	}
}

