package br.dev.ferreiras.webcalculatorapi.config;

import br.dev.ferreiras.webcalculatorapi.entity.Role;
import br.dev.ferreiras.webcalculatorapi.entity.User;
import br.dev.ferreiras.webcalculatorapi.repository.RoleRepository;
import br.dev.ferreiras.webcalculatorapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;




/** This class provides a user with administrator privileges with an initial balance of $1000.00
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */
@Configuration
public class AdminWebConfiguration implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(AdminWebConfiguration.class);

  private RoleRepository roleRepository;

  private UserRepository userRepository;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public AdminWebConfiguration() {
  }

  @Autowired
  public AdminWebConfiguration(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   *
   * Override run method from CommandLineRunner interface to create a
   * user with Role:Admin if it already does not exist persisted into the database
   * @throws Exception Database Exception
   */

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    final Role roleAdmin = this.roleRepository.findByRole(Role.Roles.ROLE_ADMIN.name());

    AdminWebConfiguration.logger.info("RoleAdmin:-> {}", roleAdmin);

    final var userAdmin = this.userRepository.findByUsername("admin@webcalculator.com");

    AdminWebConfiguration.logger.info("UserAdmin:-> {}", userAdmin);

    userAdmin.ifPresentOrElse(
        user -> AdminWebConfiguration.logger.info("Administrator already exists!"),
        () -> {

          final var user = new User();

          user.setUsername("admin@webcalculator.com");
          user.setPassword(this.bCryptPasswordEncoder.encode("@c4lc5l4t0r@"));
          user.setRoles(Set.of(roleAdmin));
          user.setBalance(new BigDecimal("1000.00"));
          user.setStatus("ACTIVE");
          user.setCreatedAt(Instant.now());

          this.userRepository.save(user);
          AdminWebConfiguration.logger.info("Administrator created");
        }
    );
  }
}
