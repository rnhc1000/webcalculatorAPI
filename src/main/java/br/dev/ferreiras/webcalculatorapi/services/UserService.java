package br.dev.ferreiras.webcalculatorapi.services;


import br.dev.ferreiras.webcalculatorapi.contracts.IUserService;
import br.dev.ferreiras.webcalculatorapi.entity.Role;
import br.dev.ferreiras.webcalculatorapi.entity.User;
import br.dev.ferreiras.webcalculatorapi.repository.OperationsRepository;
import br.dev.ferreiras.webcalculatorapi.repository.RoleRepository;
import br.dev.ferreiras.webcalculatorapi.repository.UserRepository;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

   private final UserRepository userRepository;

   private final RoleRepository roleRepository;

   private final OperationsRepository operationsRepository;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, OperationsRepository operationsRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.operationsRepository = operationsRepository;
  }

  /**
   * @param id of User entity to be returned
   * @return User Entity given its id
   */
  @Override
  public User getUserId(Long id) {

    return this.userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Resource not found!"));
  }

  /**
   * @param user entity
   */
  @Override
  public void saveUser(final User user) {

    this.userRepository.save(user);
  }

  /**
   * @return List of all registered users
   */
  @Override
  public List<User> findAllUsers() {

    return this.userRepository.findAll();
  }

  /**
   * @param username to be verified
   * @return User entity
   */
  @Override
  public Optional<User> getUsername(String username) {

    return this.userRepository.findByUsername(username);
  }

  /**
   * @return ROLE_USER | ROLE_ADMIN
   */
  @Override
  public Role getRole() {

    return this.roleRepository.findByRole(Role.Roles.ROLE_USER.name());
  }

  /**
   * @param username to update balance
   * @param balance value to be added to the current user balance
   * @return 1 to indicate balance has been updated, 0 otherwise
   */
  @Override
  public int updateBalance(String username, BigDecimal balance) {

    return this.userRepository.saveBalance(username, balance);
  }

  /**
   * @param username to get current balance
   * @return current balance
   */
  @Override
  public BigDecimal getBalance(String username) {

    return this.userRepository.findByUsernameBalance(username);
  }

  /**
   * @param id associated to each operation
   * @return cost of operation
   */
  @Override
  public BigDecimal getOperationCostById(Long id) {

    return this.operationsRepository.findOperationsCostById(id);
  }
}
