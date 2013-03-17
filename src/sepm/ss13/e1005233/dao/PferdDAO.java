package sepm.ss13.e1005233.dao;

import sepm.ss13.e1005233.domain.Pferd;

/**
 * Ein Interface das den korrekten Austausch von Pferd-entities sicherstellt
 *
 */
public interface PferdDAO {
	
	/**
	 * Fügt ein Pferd ein 
	 * @param p das einzufügende Pferd
	 */
	public void insertPferd (Pferd p);
	
	/**
	 * Gibt das durch die id spezifizierte Pferd zurück
	 * @param id der eindeutige Key zur Identifikation
	 * @return das Pferd mit der angegebenen Id
	 */
	public Pferd getPferd (Pferd p);
	
	/**
	 * Gibt ein Pferd p an, welches das durch die id spezifizierte Pferd
	 * ersetzt
	 * @param id das zu ersetzende Pferd
	 * @param p das neue (aktualisierte) Pferd
	 */
	public void updatePferd (Pferd p);
	
	/**
	 * Löscht das spezifizierte Pferd
	 * @param id die Id des zu löschenden Pferds
	 */
	public void deletePferd(Pferd p);
}
