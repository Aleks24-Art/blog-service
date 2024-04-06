package ua.aleksenko.blogservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class User implements UserDetails {

  private final String email;

  private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

  public User(String token) {
    Map<String, Claim> claims = JWT.decode(token).getClaims();
    this.email = claims.get("sub").asString();
    setUpAuthorities(claims);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private void setUpAuthorities(Map<String, Claim> claims) {
    String role = claims.get("role").asString();
    authorities.add(new SimpleGrantedAuthority(role));
  }
}
