package sepm.ss13.e1005233.dao;


import java.util.List;

import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.exceptions.JDBCRechnungPersistenceException;
import sepm.ss13.e1005233.exceptions.RechnungPersistenceException;

/**
 * Ein Interface das den korrekten Austausch von Rechnung-entities sicherstellt
 *
 */
public interface RechnungDAO {	
	
	/**
	 * Fügt eine Rechnung ein 
	 * @param r die einzufügende Rechnung
	 */
	public void insertRechnung (Rechnung r) throws RechnungPersistenceException;
	
	/**
	 * Gibt die durch date spezifizierte Rechnung zurück
	 * @param date das Datum der gesuchten Rechnung
	 * @return die Rechnung mit dem spezifizierten Datum
	 */
	public Rechnung getRechnung (Rechnung r) throws RechnungPersistenceException;
	
	/**
	 * Findet alle gespeicherten Rechnungen
	 * @return eine Liste aller gespeicherten Rechnungen
	 * @throws JDBCRechnungPersistenceException 
	 */
	public List<Rechnung> findAll() throws RechnungPersistenceException;
	
	/**
	 * Gibt eine Liste der Buchungen, die einem Pferd zugeordnet sind, zurück
	 * @param p das Pferd, dessen Buchungen zurückgegeben werden sollen
	 * @return die Liste der zugeordneten Buchungen
	 * @throws JDBCRechnungPersistenceException wenn etwas während der Rückgabe fehlgeschlagen ist
	 */
	public List<Buchung> getBuchungen(Pferd p) throws RechnungPersistenceException;
	
	/**
	 * Gibt eine Liste der Buchungen, die einer Rechnung zugeordnet sind, zurück
	 * @param r die Rechnung, deren Buchungen zurückgegeben werden sollen
	 * @return die Liste der zugeordneten Buchungen
	 * @throws JDBCRechnungPersistenceException wenn etwas während der Rückgabe fehlgeschlagen ist
	 */
	public List<Buchung> getBuchungen(Rechnung r) throws JDBCRechnungPersistenceException;
}
