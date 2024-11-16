package br.dev.ferreiras.webcalculatorapi.services;


import br.dev.ferreiras.webcalculatorapi.contracts.IUserService;
import br.dev.ferreiras.webcalculatorapi.dto.LoadBalanceResponseDto;
import br.dev.ferreiras.webcalculatorapi.dto.UserDto;
import br.dev.ferreiras.webcalculatorapi.dto.UserRequestDto;
import br.dev.ferreiras.webcalculatorapi.dto.UserResponseDto;
import br.dev.ferreiras.webcalculatorapi.entity.Role;
import br.dev.ferreiras.webcalculatorapi.entity.User;
import br.dev.ferreiras.webcalculatorapi.repository.OperationsRepository;
import br.dev.ferreiras.webcalculatorapi.repository.RoleRepository;
import br.dev.ferreiras.webcalculatorapi.repository.UserRepository;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.NoSuchElementException;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.ResourceNotFoundException;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.UserAlreadyExistsException;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserService implements IUserService {

   private final UserRepository userRepository;

   private final RoleRepository roleRepository;

   private final OperationsRepository operationsRepository;

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

   private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public UserService(
      UserRepository userRepository, RoleRepository roleRepository,
      OperationsRepository operationsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.operationsRepository = operationsRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
  public List<UserDto> findAllUsers() {

    List<User> users = userRepository.findAll();
    System.out.println(users);
    return users.stream().map(user -> new UserDto(Optional.ofNullable(user))).toList();
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

  public LoadBalanceResponseDto addNewUser(final UserResponseDto userDto) {
    if (this.getUsername(userDto.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    } else {
      final var userRole = this.getRole();
      final var user = new User();
      user.setUsername(userDto.getUsername());
      user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));
      user.setBalance(userDto.getBalance());
      user.setStatus(userDto.getStatus());
      user.setRoles(Set.of(userRole));
      this.saveUser(user);

      return new LoadBalanceResponseDto(userDto.getUsername(), userDto.getBalance());
    }
  }


  /**
   * @param userRequestDto ( username, status )
   * @return status ACTIVE or INACTIVE
   */
  public UserResponseDto activateUser(final UserRequestDto userRequestDto) {

    String update;
    if(userRequestDto.username().isEmpty()) {
      throw new UsernameNotFoundException("Username not found!");
    } else {

      update = this.userRepository.updateStatus(userRequestDto.username(), userRequestDto.status());

    }

    return new UserResponseDto(userRequestDto.username(), update);
  }

  public String authenticated() {

    try {

      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      final var user = this.userRepository.findByUsername(authentication.getName());
      final String currentUserName = user.orElseThrow().getUsername();
      logger.info("CurrentUsername -> {}", currentUserName);
      return currentUserName;

    } catch (RuntimeException ex ){
      throw new NoSuchElementException("Element does not exist");
    }
  }

}
