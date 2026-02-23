package lepackage.mongo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IndirizzoDTO implements Serializable {

	private static final long serialVersionUID = -1044325229658677152L;
	
	private String nome;
	private String[] materieIds;
	private String[] sediIds;
	
}
