package ua.aleksenko.blogservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtils {

  public String getUserName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

}
