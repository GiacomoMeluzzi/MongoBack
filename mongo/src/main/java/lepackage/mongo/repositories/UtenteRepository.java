package lepackage.mongo.repositories;

import lepackage.mongo.documents.UtenteEntity;

public interface UtenteRepository {
	
	public UtenteEntity findUtenteByEmailAndPassword(String email, String password);
	
	public UtenteEntity findUtenteByEmail(String email);
	
	public UtenteEntity findUtenteByUsername(String username);	
	
	public void saveUtente(UtenteEntity utente);
	
}
