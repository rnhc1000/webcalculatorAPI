package br.dev.ferreiras.webcalculatorapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_operations")
public class Operations {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column (name = "operation_id")
  private Long operationId;

  @Column (nullable = false)
  private BigDecimal cost;

  @NotBlank
  @Size(min = 7, max = 20)
  @Column (unique = true)
  private String operation;
}