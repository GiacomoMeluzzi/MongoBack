package lepackage.mongo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lepackage.mongo.documents.Materia;
import lepackage.mongo.dto.MateriaDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.NotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.repositories.MateriaRepository;

@Service
public class MateriaService {
	
	private MateriaRepository repo;
	
	public MateriaService(MateriaRepository repo) {
		this.repo = repo;
	}
	
	public List<MateriaDTO> findMaterieStudente(UtenteDTO utenteDTO) throws Exception {
		try {
			if(utenteDTO.getIndirizziIds().length != 1) {
				System.out.println("Uno studente ha più facoltà o nessuna, questo non dovrebbe mai accadre.");
				throw new NotValidException("Uno studente può appartenere a una soka facoltà.");
			}
			List<Materia> materie = repo.findMaterieUnIndirizzo(utenteDTO.getIndirizziIds()[0], utenteDTO.getUsername()).orElseThrow(NotFoundException::new);
			if (materie.size() == 0) {
				System.out.println("Nessuna materia trovata.");
				throw new NotFoundException();
			}
			System.out.println("Materie trovate. " + materie.size());
			return MateriaDTO.materieToDto(materie, utenteDTO.getRole());
		} catch (Exception e) {
			if(e instanceof NotValidException || e instanceof NotFoundException) {
				throw e;
			}
			System.out.println("Eccezione generica a findMateriaStudente. " + e.getMessage());
			throw new Exception();
		}
	}
	
}
