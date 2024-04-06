package ua.aleksenko.blogservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token-issuer")
public class TokenIssuerProperties {

  private String baseUrl;
  private String tokenValidationEndpoint;
}
