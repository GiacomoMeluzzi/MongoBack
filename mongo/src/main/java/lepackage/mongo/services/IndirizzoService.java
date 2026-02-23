package lepackage.mongo.services;

import org.springframework.stereotype.Service;

import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IndirizzoNotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.repositories.IndirizzoRepository;

@Service
public class IndirizzoService {

	private IndirizzoRepository repo;

	public IndirizzoService(IndirizzoRepository repo) {
		this.repo = repo;
	}

	public boolean checkIndirizziExist(UtenteDTO utenteDaControllare) throws Exception {
		try {
			if (utenteDaControllare.getIndirizziIds() == null || utenteDaControllare.getIndirizziIds().length == 0) {
				System.out.println("Campo indirizziIds del DTO vuoto, impossibile proseguire con registrazione.");
				throw new EmptyFieldsException("indirizzi");
			}
			if (utenteDaControllare.getRole().toString().equals("STUDENTE")
					&& utenteDaControllare.getIndirizziIds().length > 1) {
				throw new NotValidException("Uno studente può iscriversi ad una sola facoltà.");
			}
			for (String indirizzoDaControllare : utenteDaControllare.getIndirizziIds()) {
				System.out.println(
						"Indirizzo in entrata in IndirizzoService per check esistenza: " + indirizzoDaControllare);
				repo.findById(indirizzoDaControllare).orElseThrow(IndirizzoNotFoundException::new);
				System.out.println("IndirizzoService ha validato l'esistenza dell'indirizzo.");
			}
			System.out.println("Controlli indirizzi in DB superati.");
			return true;
		} catch (Exception e) {
			if (e instanceof IndirizzoNotFoundException) {
				throw e;
			}
			System.out.println("IndirizzoService lancia un'eccezione generica. -> " + e.getMessage());
			throw new Exception("Errore generico in IndirizzoService", e);
		}
	}

}
