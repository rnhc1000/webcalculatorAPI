package br.dev.ferreiras.webcalculatorapi.dto;

import br.dev.ferreiras.webcalculatorapi.entity.User;

import java.math.BigDecimal;

public class UserResponseDto {

  private Long userId;
  private String username;
  private String password;
  private String status;
  private BigDecimal balance;

  public UserResponseDto() {

  }
  public UserResponseDto(User entity) {
    userId = entity.getUserId();
    username = entity.getUsername();
    password = entity.getPassword();
    status = entity.getStatus();
    balance = entity.getBalance();
  }

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getBalance() {
    return balance;
  }
}
