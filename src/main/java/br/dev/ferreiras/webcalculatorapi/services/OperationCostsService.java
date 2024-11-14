package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.dto.OperationCostsDto;
import br.dev.ferreiras.webcalculatorapi.entity.Operations;
import br.dev.ferreiras.webcalculatorapi.repository.OperationsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OperationCostsService {

  private final OperationsRepository operationsRepository;

  public OperationCostsService(OperationsRepository operationsRepository) {
    this.operationsRepository = operationsRepository;
  }

  /**
   * @return list of operators implemented
   */
  public List<OperationCostsDto> getOperationsCost() {
    List<Operations> result = operationsRepository.findAll();

    return result.stream().map(OperationCostsDto::new).toList();
  }


  public BigDecimal getOperationCostByOperation(final String operation) {

    return this.operationsRepository.findOperationsCostByOperation(operation);
  }


}
