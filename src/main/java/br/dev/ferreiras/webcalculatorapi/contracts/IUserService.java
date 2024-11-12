package br.dev.ferreiras.webcalculatorapi.contracts;

import br.dev.ferreiras.webcalculatorapi.entity.Role;
import br.dev.ferreiras.webcalculatorapi.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User getUserId(Long id);
    void saveUser(User user);
    List<User> findAllUsers();
    Optional<User> getUsername(String username);
    Role getRole();
    int updateBalance(String username, BigDecimal balance);
    BigDecimal getBalance(String username);
    BigDecimal getOperationCostById(Long id);
//    LoadBalanceResponseDto addNewUser(UserResponseDto dto);
//    UserResponseDto activateUser(UserRequestDto userRequestDto);
  }

