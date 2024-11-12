package br.dev.ferreiras.webcalculatorapi.repository;

import br.dev.ferreiras.webcalculatorapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByRole(String username);
}
