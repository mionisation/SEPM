package sepm.ss13.e1005233.dao;

import sepm.ss13.e1005233.domain.Pferd;

/**
 * Ein Interface das den korrekten Austausch von Pferd-entities sicherstellt
 *
 */
public interface PferdDAO {
	
	/**
	 * F�gt ein Pferd ein 
	 * @param p das einzuf�gende Pferd
	 */
	public void insertPferd (Pferd p);
	
	/**
	 * Gibt das durch die id spezifizierte Pferd zur�ck
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
	 * L�scht das spezifizierte Pferd
	 * @param id die Id des zu l�schenden Pferds
	 */
	public void deletePferd(Pferd p);
}
