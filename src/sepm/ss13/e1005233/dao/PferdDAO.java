package sepm.ss13.e1005233.dao;

import java.sql.SQLException;
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
	
	/**
	 * Die Id wird automatisch generiert und betr�gt die Anzahl der Entit�ten (Zeilen) plus eins.
	 * @return eine valide ID des n�chsten Eintrags
	 * @throws SQLException 
	 */
	public int getNewId() throws SQLException;
	
	/**
	 * Gibt eine Liste aller in der Datenbank gespeicherten Pferde zur�ck
	 * @return eine Liste der gespeicherten Pferde
	 * @throws SQLException 
	 */
	public List<Pferd> findAll() throws SQLException;
}
