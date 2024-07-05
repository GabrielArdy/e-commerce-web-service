package com.ecommerce.desktop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

  private String statusCode;
  private String message;
  private UserDTO user;

}
