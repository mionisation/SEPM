package sepm.ss13.e1005233.dao;

import java.util.List;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.PferdPersistenceException;

/**
 * Ein Interface das den korrekten Austausch von Pferd-entities sicherstellt
 *
 */
public interface PferdDAO {
	
	/**
	 * F�gt ein Pferd ein 
	 * @param p das einzuf�gende Pferd
	 * @throws Exception wird geworfen wenn ein Fehler beim Einf�gen auftritt
	 */
	public void insertPferd (Pferd p) throws PferdPersistenceException;
	
	/**
	 * Gibt das durch die id spezifizierte Pferd zur�ck
	 * @param p das Pferd mit dem eindeutigen Key
	 * @return das Pferd mit der angegebenen Id
	 * @throws Exception wird geworfen wenn ein Fehler bei der R�ckgabe auftritt
	 */
	public Pferd getPferd (Pferd p) throws PferdPersistenceException;
	
	/**
	 * Gibt ein Pferd p an, welches das durch die id spezifizierte Pferd
	 * ersetzt
	 * @param id das zu ersetzende Pferd
	 * @param p das neue (aktualisierte) Pferd
	 * @throws Exception wird geworfen wenn ein Fehler beim Aktualisieren auftritt
	 */
	public void updatePferd (Pferd p) throws PferdPersistenceException;
	
	/**
	 * L�scht das spezifizierte Pferd
	 * @param id die Id des zu l�schenden Pferds
	 * @throws Exception wird geworfen wenn ein Fehler beim L�schen auftritt
	 */
	public void deletePferd(Pferd p) throws PferdPersistenceException;
	
	/**
	 * Die Id wird automatisch generiert und betr�gt die Anzahl der Entit�ten (Zeilen) plus eins.
	 * @return eine valide ID des n�chsten Eintrags
	 * @throws Exception wird geworfen wenn ein Fehler beim Vorgang auftritt
	 */
	public int getNewId() throws PferdPersistenceException;
	
	/**
	 * Gibt eine Liste aller gespeicherten Pferde zur�ck
	 * @return eine Liste der gespeicherten Pferde
	 * @throws Exception wird geworfen wenn ein Fehler beim Vorgang auftritt
	 */
	public List<Pferd> findAll() throws PferdPersistenceException;
	
	/**
	 * Gibt eine Liste aller gespeicherten und nicht 'gel�schten' Pferde zur�ck
	 * @return eine Liste der gespeicherten und nicht gel�schten Pferde
	 * @throws Exception wird geworfen wenn ein Fehler beim Vorgang auftritt
	 */
	public List<Pferd> findAllUndeleted() throws PferdPersistenceException;
	
	/**
	 * Gibt eine Liste aller Pferde zur�ck, die den Kriterien des Such-Pferds
	 * entsprechen
	 * @param sh ein Suchpferd mit erw�nschten Attributen
	 * @return eine Liste aller den Suchkriterien entsprechenden Pferde
	 */
	public List<Pferd> findBy(SuchPferd sp) throws PferdPersistenceException;
}
