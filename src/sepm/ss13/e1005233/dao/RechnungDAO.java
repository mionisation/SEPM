package sepm.ss13.e1005233.dao;

import java.sql.Timestamp;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;

/**
 * Ein Interface das den korrekten Austausch von Rechnung-entities sicherstellt
 *
 */
public interface RechnungDAO {	
	
	/**
	 * Fügt eine Rechnung ein 
	 * @param r die einzufügende Rechnung
	 */
	public void insertRechnung (Rechnung r);
	
	/**
	 * Gibt die durch date spezifizierte Rechnung zurück
	 * @param date das Datum der gesuchten Rechnung
	 * @return die Rechnung mit dem spezifizierten Datum
	 */
	public Rechnung getRechnung (Timestamp date);
	
	/**
	 * Gibt eine Rechnung r an, welche die durch date spezifizierte Rechnung
	 * ersetzt
	 * @param date die zu ersetzende Rechnung
	 * @param r die neue (aktualisierte) Rechnung
	 */
	public void updateRechnung (Timestamp date, Rechnung r);
	
	/**
	 * Löscht die spezifizierte Rechnung
	 * @param date das Datum der zu löschenden Rechnung
	 */
	public void deleteRechnung (Timestamp date);
}
