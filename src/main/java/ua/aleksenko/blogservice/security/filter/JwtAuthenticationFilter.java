package ua.aleksenko.blogservice.security.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.aleksenko.blogservice.model.error.ApiError;
import ua.aleksenko.blogservice.model.exception.VerificationTokenException;
import ua.aleksenko.blogservice.security.User;
import ua.aleksenko.blogservice.service.TokenService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final int TOKEN_START_INDEX = 7;

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(authHeader) || !authHeader.trim().startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.trim().substring(TOKEN_START_INDEX);

        try {
            tokenService.validateExpirationDate(token);
            tokenService.validateToken(token);
        } catch (VerificationTokenException | TokenExpiredException | JWTDecodeException e1) {
	        log.error(e1.getMessage());
	        response.setStatus(UNAUTHORIZED.value());
            response.getWriter().write(convertErrorToJson(UNAUTHORIZED, e1.getMessage()));
            return;
        } catch (Exception e2) {
			log.error(e2.getMessage());
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(convertErrorToJson(INTERNAL_SERVER_ERROR, e2.getMessage()));
            return;
        }

        User user = new User(token);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private String convertErrorToJson(HttpStatus status, String errorMessage) throws JsonProcessingException {
        log.error("Validation token error: {}", errorMessage);
        return objectMapper.writeValueAsString(new ApiError(status.value(), errorMessage));
    }
}
