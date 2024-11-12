package br.dev.ferreiras.webcalculatorapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="tb_users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long userId;

    @NotBlank
    @NotNull
    @Email
    @Size(min=5, max=40)
    @Column(unique=true)
    private String username;

    @Column (nullable = false)
    @NotBlank
    @Size (min = 10, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String status;

    private BigDecimal balance;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="tb_users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
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

    public void setUsername(@NotBlank @NotNull @Email @Size(min = 5, max = 40) String username) {
        this.username = username;
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
}
