package sepm.ss13.e1005233.service;

import java.util.List;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.PferdValidationException;
import sepm.ss13.e1005233.exceptions.RechnungValidationException;

/**
 * Dieses Interface lägt alle Methoden fest, die für eine
 * funktionierende Serviceschicht implementiert werden müssen
 */
public interface Service {

	/**
	 * Diese Methode erstellt ein neues Pferd
	 * @param p das neue Pferd
	 * @throws PferdValidationException wird bei unzulässigen Werten geworfen
	 */
	public void insertPferd(Pferd p) throws PferdValidationException;
	
	/**
	 * Diese Methode erstellt eine neue Rechnung
	 * @param r die neue Rechnung
	 * @throws RechnungValidationException wird bei unzulässigen Werten geworfen
	 */
	public void createRechnung(Rechnung r) throws RechnungValidationException;
	
	/**
	 * Diese Methode gibt das gesuchte Pferd zurück
	 * @param p das Pferd, welches die id des gesuchten Pferdes beinhaltet
	 * @return das gesuchte Pferd
	 */
	public Pferd getPferd(Pferd p);
	
	/**
	 * Diese Methode gibt eine Liste von Pferden zurück, die den
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
	 * Diese Methode löscht ein Pferd
	 * @param p das zu löschende Pferd
	 */
	public void deletePferd(Pferd p);
	
	/**
	 * Diese Methode gibt alle Pferde zurück
	 * @return eine Liste aller gespeicherten Pferde
	 */
	public List<Pferd> findAllPferde();
	
	/**
	 * Diese Methode gibt die drei beliebtesten Pferde zurück
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
	 * Diese Methode gibt eine Rechnung zurück
	 * @param r die Rechnung, die das Datum der gesuchten Rechnung beinhaltet
	 * @return die gesuchte Rechnung
	 */
	public Rechnung getRechnung(Rechnung r);
	
	/**
	 * Diese Methode gibt alle Rechnungen zurück
	 * @return eine Liste aller gespeicherten Rechnungen
	 */
	public List<Rechnung> findAllRechnungen();
}
