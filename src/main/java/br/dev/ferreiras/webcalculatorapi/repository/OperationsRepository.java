package br.dev.ferreiras.webcalculatorapi.repository;

import br.dev.ferreiras.webcalculatorapi.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OperationsRepository extends JpaRepository<Operations, Long> {

  @Query("""
          SELECT cost c FROM Operations o WHERE operationId = :operationId
          """)
  BigDecimal findOperationsCostById(Long operationId);

  @Query ("""
          SELECT cost c FROM Operations o WHERE operation = :operation
          """)
  BigDecimal findOperationsCostByOperation(String operation);

}
