package sepm.ss13.e1005233.service;

import java.util.List;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.PferdValidationException;
import sepm.ss13.e1005233.exceptions.RechnungValidationException;

/**
 * Dieses Interface l�gt alle Methoden fest, die f�r eine
 * funktionierende Serviceschicht implementiert werden m�ssen
 */
public interface Service {

	/**
	 * Diese Methode erstellt ein neues Pferd
	 * @param p das neue Pferd
	 * @throws PferdValidationException wird bei unzul�ssigen Werten geworfen
	 */
	public void insertPferd(Pferd p) throws PferdValidationException;
	
	/**
	 * Diese Methode erstellt eine neue Rechnung
	 * @param r die neue Rechnung
	 * @throws RechnungValidationException wird bei unzul�ssigen Werten geworfen
	 */
	public void createRechnung(Rechnung r) throws RechnungValidationException;
	
	/**
	 * Diese Methode gibt das gesuchte Pferd zur�ck
	 * @param p das Pferd, welches die id des gesuchten Pferdes beinhaltet
	 * @return das gesuchte Pferd
	 */
	public Pferd getPferd(Pferd p);
	
	/**
	 * Diese Methode gibt eine Liste von Pferden zur�ck, die den
	 * Kriterien im Suchpferd entsprechen
	 * @param sp ein Suchpferd mit Attributen nach denen gesucht werden soll
	 * @return eine Liste mit passenden Pferden
	 */
	public List<Pferd> findBy(SuchPferd sp);
	/**
	 * Diese Methode aktualisiert ein Pferd
	 * @param p das Pferd das aktualisiert werden soll
	 */
	public void updatePferd(Pferd p) throws PferdValidationException;
	
	/**
	 * Diese Methode l�scht ein Pferd
	 * @param p das zu l�schende Pferd
	 */
	public void deletePferd(Pferd p);
	
	/**
	 * Diese Methode gibt alle Pferde zur�ck
	 * @return eine Liste aller gespeicherten Pferde
	 */
	public List<Pferd> findAllPferde();
	
	/**
	 * Diese Methode gibt die drei beliebtesten Pferde zur�ck
	 * @return eine Liste der drei beliebtesten Pferde
	 */
	public List<Pferd> getPopularPferde();
	
	/**
	 * Diese Methode verteuert alle Pferde aus einer Liste um 5%
	 * pro Stunde
	 * @param lp die Liste der Pferde
	 */
	public void verteurePferde(List<Pferd> lp);
	
	/**
	 * Diese Methode gibt eine Rechnung zur�ck
	 * @param r die Rechnung, die das Datum der gesuchten Rechnung beinhaltet
	 * @return die gesuchte Rechnung
	 */
	public Rechnung getRechnung(Rechnung r);
	
	/**
	 * Diese Methode gibt alle Rechnungen zur�ck
	 * @return eine Liste aller gespeicherten Rechnungen
	 */
	public List<Rechnung> findAllRechnungen();
}
