package sepm.ss13.e1005233.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.service.JDBCService;

import net.miginfocom.swing.MigLayout;

public class RechnungPanel extends JPanel{

	private static final Logger log = Logger.getLogger(RechnungPanel.class);
	private JDBCService service;
	private final String[] rechnungColumnNames = {"Datum", "Name", "Gesamtpreis", "Gesamtstunden", "Zahlungsart", "Telefon"};
	private RechnungTable ctm;
	private JTable rechnungen;
	private ListSelectionModel ldm;
	private JScrollPane scrollpanerechnung;
	
	public RechnungPanel(JFrame parent, JDBCService service) {
		super(new MigLayout());
		log.debug("Erstelle neues RechnungPanel...");
		this.service = service;
		
		
		ctm = new RechnungTable((updateTable(service.findAllRechnungen())), rechnungColumnNames);
		rechnungen = new JTable(ctm);
		ldm = rechnungen.getSelectionModel();
		ldm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//TODO maybe not needed
		//ldm.addListSelectionListener(lsl);
		scrollpanerechnung = new JScrollPane(rechnungen);
		add(scrollpanerechnung);
		
		
	}
	
	public Object[][] updateTable(List<Rechnung> rechnungList){
		log.debug("Aktualisiere Tabelle mit Rechnungen...");
		Object[][] allRechnungenArray = new Object[rechnungList.size()][6];
		int i = 0;
		for(Rechnung r : rechnungList) {
			allRechnungenArray[i][0] = r.getDate();
			allRechnungenArray[i][1] = r.getName();
			allRechnungenArray[i][2] = r.getGesamtpreis();
			allRechnungenArray[i][3] = r.getGesamtstunden();
			allRechnungenArray[i][4] = r.getZahlungsart();
			//TODO change?
			if(r.getTelefon() != 0)
				allRechnungenArray[i][5] = r.getTelefon();
			
			i++;
		}
		return allRechnungenArray;
	}
	
	
	//TODO implement rechnungpanel
	
}
