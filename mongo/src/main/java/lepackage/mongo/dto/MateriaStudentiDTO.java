package lepackage.mongo.dto;

import java.io.Serializable;
import java.util.List;

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
public class MateriaStudentiDTO implements Serializable {

	private static final long serialVersionUID = -8924571221756847331L;
	
	private String materia;
	private List<String> studenti;

}
