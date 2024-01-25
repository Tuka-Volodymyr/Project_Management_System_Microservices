package com.example.identity_service.config;

import com.example.identity_service.model.User;
import com.example.identity_service.model.exception.UserNotFoundException;
import com.example.identity_service.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> credential = userRepository.findByEmail(username);
    return credential.map(CustomUserDetails::new).orElseThrow(UserNotFoundException::new);
  }
}
