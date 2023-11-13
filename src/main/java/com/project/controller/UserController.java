package com.project.controller;


import com.project.configuration.JWTTokenProvider;
import com.project.dto.AuthRequestDto;
import com.project.dto.CreateUserDto;
import com.project.model.User;
import com.project.service.UserService;
import com.project.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private JWTTokenProvider tokenProvider;

  @GetMapping("/")
  public String printLoginForm(){
      return "login";
  }

  @GetMapping("/register")
  public String showRegisterForm(){
    return "register";
  }

  @PostMapping("/register")
  public ResponseEntity<User> registration(@RequestBody CreateUserDto dto){
    User userDtoToUser = userMapper.createUserDtoToUser(dto);
    User user = userService.create(userDtoToUser);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody AuthRequestDto dto){

    UserDetails userDetails = userService.loadUserByUsername(dto.getUsername());

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (encoder.matches(dto.getPassword(), userDetails.getPassword())){
      String token = tokenProvider.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.badRequest().build();
  }
}
