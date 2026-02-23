package lepackage.mongo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.varie.Role;
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
public class MateriaDTO implements Serializable {

	private static final long serialVersionUID = 5969299433196369544L;

	private String id;
	private String nome;
	private String[] indirizziIds;
	private String[] utentiIds;

	public static List<MateriaDTO> materieToDto(List<MateriaEntity> materie, Role role) throws EmptyFieldsException {
		try {
			if (materie == null || materie.size() == 0) {
				throw new EmptyFieldsException("materie");
			}
			List<MateriaDTO> materieDTO = new ArrayList<MateriaDTO>();
			return materieDTO;
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}

}
