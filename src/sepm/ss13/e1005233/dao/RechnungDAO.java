package sepm.ss13.e1005233.dao;


import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.exceptions.RechnungPersistenceException;

/**
 * Ein Interface das den korrekten Austausch von Rechnung-entities sicherstellt
 *
 */
public interface RechnungDAO {	
	
	/**
	 * F�gt eine Rechnung ein 
	 * @param r die einzuf�gende Rechnung
	 */
	public void insertRechnung (Rechnung r) throws RechnungPersistenceException;
	
	/**
	 * Gibt die durch date spezifizierte Rechnung zur�ck
	 * @param date das Datum der gesuchten Rechnung
	 * @return die Rechnung mit dem spezifizierten Datum
	 */
	public Rechnung getRechnung (Rechnung r) throws RechnungPersistenceException;
	
	/**
	 * Gibt eine Rechnung r an, welche die durch date spezifizierte Rechnung
	 * ersetzt
	 * @param date die zu ersetzende Rechnung
	 * @param r die neue (aktualisierte) Rechnung
	 */
	public void updateRechnung (Rechnung r) throws RechnungPersistenceException;
	
	/**
	 * L�scht die spezifizierte Rechnung
	 * @param date das Datum der zu l�schenden Rechnung
	 */
	public void deleteRechnung (Rechnung r) throws RechnungPersistenceException;
}
