package br.dev.ferreiras.webcalculatorapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Access Token object, type definition;
 * @author Ricardo Ferreira
 * @version 1.1.0916202401
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessToken {
  String token;
  Long timeout;
}
