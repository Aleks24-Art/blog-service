package ua.aleksenko.blogservice.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

  public String getUserName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

}
