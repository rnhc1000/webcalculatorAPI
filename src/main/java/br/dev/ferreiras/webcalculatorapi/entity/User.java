package br.dev.ferreiras.webcalculatorapi.entity;

import br.dev.ferreiras.webcalculatorapi.dto.LoginRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  @NotBlank
  @NotNull
  @Email
  @Size(min = 5, max = 40)
  @Column(unique = true)
  private String username;

  @Column(nullable = false)
  @NotBlank
  @Size(min = 10, max = 100)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String status;

  private BigDecimal balance;

  @CreationTimestamp
  private Instant createdAt;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "tb_users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;


  public User() {
  }

  public User(Long userId, String username, String password, String status, BigDecimal balance, Instant createdAt, Set<Role> roles) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.status = status;
    this.balance = balance;
    this.createdAt = createdAt;
    this.roles = roles;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public @NotBlank @NotNull @Email @Size(min = 5, max = 40) String getUsername() {
    return username;
  }

  /**
   * @return
   */
  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  /**
   * @return
   */
  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  /**
   * @return
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  /**
   * @return
   */
  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

  public void setUsername(@NotBlank @NotNull @Email @Size(min = 5, max = 40) String username) {
    this.username = username;
  }

  /**
   * @return
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  public @NotBlank @Size(min = 10, max = 100) String getPassword() {
    return password;
  }

  public void setPassword(@NotBlank @Size(min = 10, max = 100) String password) {
    this.password = password;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public boolean isLoginCorrect(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {

    return passwordEncoder.matches(loginRequestDto.password(), this.password);
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", status='" + status + '\'' +
        ", balance=" + balance +
        ", createdAt=" + createdAt +
        ", roles=" + roles +
        '}';
  }
}
