package com.project.service;

import com.project.exception.UserNotFoundException;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public User create(User user) {
    user.setRoleList(Set.of(Role.USER));
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> byUsername = userRepository.findByUsername(username);
    return byUsername.orElseThrow(UserNotFoundException::new);
  }
}
