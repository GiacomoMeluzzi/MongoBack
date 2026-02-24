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

	private String _id;
	private String nome;
	
	public MateriaDTO (MateriaEntity materiaDaConvertire) {
		this._id = materiaDaConvertire.get_id();
		this.nome = materiaDaConvertire.getNome();
	}

	public static List<MateriaDTO> listaMaterieToListaDto(List<MateriaEntity> listaMaterieDaConvertire, Role role) throws EmptyFieldsException {
		try {
			if (listaMaterieDaConvertire == null || listaMaterieDaConvertire.size() == 0) {
				throw new EmptyFieldsException("lista materie");
			}
			List<MateriaDTO> listaMaterieDTO = new ArrayList<MateriaDTO>();
			
			for (MateriaEntity materiaDaConvertire : listaMaterieDaConvertire) {
				listaMaterieDTO.add(new MateriaDTO(materiaDaConvertire));
			}
			return listaMaterieDTO;
			
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}

}
