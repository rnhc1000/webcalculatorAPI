package br.dev.ferreiras.webcalculatorapi.repository;

import br.dev.ferreiras.webcalculatorapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Transactional
  @Modifying
  @Query("""
      UPDATE User u SET u.balance = :balance WHERE u.username = :username
      """
  )
  int saveBalance(String username, BigDecimal balance);

  @Query("""
      SELECT balance b from User u WHERE u.username = :username
      """)
  BigDecimal findByUsernameBalance(String username);

  @Transactional
  @Modifying
  @Query("""
      UPDATE User u SET u.status = :status WHERE u.username = :username
      """)
  String updateStatus(String username, String status);

}

