package sepm.ss13.e1005233.dao;

import java.util.List;

import sepm.ss13.e1005233.domain.Pferd;

/**
 * Ein Interface das den korrekten Austausch von Pferd-entities sicherstellt
 *
 */
public interface PferdDAO {
	
	/**
	 * F�gt ein Pferd ein 
	 * @param p das einzuf�gende Pferd
	 * @throws Exception 
	 */
	public void insertPferd (Pferd p) throws Exception;
	
	/**
	 * Gibt das durch die id spezifizierte Pferd zur�ck
	 * @param id der eindeutige Key zur Identifikation
	 * @return das Pferd mit der angegebenen Id
	 * @throws Exception 
	 */
	public Pferd getPferd (Pferd p) throws Exception;
	
	/**
	 * Gibt ein Pferd p an, welches das durch die id spezifizierte Pferd
	 * ersetzt
	 * @param id das zu ersetzende Pferd
	 * @param p das neue (aktualisierte) Pferd
	 * @throws Exception 
	 */
	public void updatePferd (Pferd p) throws Exception;
	
	/**
	 * L�scht das spezifizierte Pferd
	 * @param id die Id des zu l�schenden Pferds
	 * @throws Exception 
	 */
	public void deletePferd(Pferd p) throws Exception;
	
	/**
	 * Die Id wird automatisch generiert und betr�gt die Anzahl der Entit�ten (Zeilen) plus eins.
	 * @return eine valide ID des n�chsten Eintrags
	 * @throws Exception 
	 */
	public int getNewId() throws Exception;
	
	/**
	 * Gibt eine Liste aller gespeicherten Pferde zur�ck
	 * @return eine Liste der gespeicherten Pferde
	 * @throws Exception 
	 */
	public List<Pferd> findAll() throws Exception;
}
