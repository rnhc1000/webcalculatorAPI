package br.dev.ferreiras.webcalculatorapi.dto;

import br.dev.ferreiras.webcalculatorapi.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

  public UserResponseDto(@NotBlank @NotNull @Email @Size(min = 5, max = 40) String username, @NotBlank @Size(min = 10, max = 100) String password, String status, BigDecimal balance) {
  }

  public UserResponseDto(String username, String update) {
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
