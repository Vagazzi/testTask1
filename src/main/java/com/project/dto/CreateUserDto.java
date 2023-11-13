package com.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class CreateUserDto {
  private String name;
  private String username;
  private String password;
}
