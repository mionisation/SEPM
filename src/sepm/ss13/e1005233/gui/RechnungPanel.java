package sepm.ss13.e1005233.gui;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.service.JDBCService;

import net.miginfocom.swing.MigLayout;

public class RechnungPanel extends JPanel {

	private static final Logger log = Logger.getLogger(RechnungPanel.class);
	private JDBCService service;
	private final String[] rechnungColumnNames = {"Datum", "Name", "Gesamtpreis", "Gesamtstunden", "Zahlungsart", "Telefon"};
	private final String[] buchungColumnNames = {"Pferd-Id", "Preis","Stunden"};
	private RechnungTable ctm;
	private BuchungTable ctb;
	private JTable rechnungen;
	private ListSelectionModel ldm;
	private JScrollPane scrollpanerechnung;
	private JPanel detailPanel;
	private final Object[][] temp = {};
	private JLabel rLabel;
	private ListSelectionListener lsl= new ListSelectionListener() {
	      @Override
		public void valueChanged(ListSelectionEvent e) {
	    	  int x = rechnungen.getSelectedRow();
	    	  if(x < 0) {
	    		  return;
	    	  }
	    	 ctb = new BuchungTable(updateBuchungTable( service.getBuchungen(new Rechnung((Timestamp) rechnungen.getValueAt(x, 0)))), buchungColumnNames);
			 buchungen.setModel(ctb);
			 ctb.fireTableDataChanged();
	      }
	    };
	private JTable buchungen;
	private JScrollPane scrollpanebuchung;
	private JPanel rechnungPanel;	
	
	public RechnungPanel(JFrame parent, JDBCService service) {
		super(new MigLayout());
		log.debug("Erstelle neues RechnungPanel...");
		this.service = service;
		
		//erstelle Rechnungstabelle
		ctm = new RechnungTable((updateRechnungenTable(service.findAllRechnungen())), rechnungColumnNames);
		rechnungen = new JTable(ctm);
		ldm = rechnungen.getSelectionModel();
		ldm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ldm.addListSelectionListener(lsl);
		rLabel = new JLabel("Alle Rechnungen:");
		scrollpanerechnung = new JScrollPane(rechnungen);
		rechnungPanel = new JPanel(new MigLayout());
		rechnungPanel.add(rLabel, "wrap");
		rechnungPanel.add(scrollpanerechnung);
		add(rechnungPanel);
		//erstelle Detailtabelle		
		detailPanel = new JPanel(new MigLayout());
		rLabel = new JLabel("Details zur gewählten Rechnung:");
		ctb = new BuchungTable(temp, buchungColumnNames);
		buchungen = new JTable(ctb);
		buchungen.getColumnModel().getColumn(0).setPreferredWidth(25);
		buchungen.getColumnModel().getColumn(1).setPreferredWidth(50);
		buchungen.getColumnModel().getColumn(2).setPreferredWidth(50);
		scrollpanebuchung = new JScrollPane(buchungen);
		scrollpanebuchung.setPreferredSize(new Dimension(200, 400));
		detailPanel.add(rLabel, "wrap");
		detailPanel.add(scrollpanebuchung);
		add(detailPanel);
		
	}
	
	public Object[][] updateRechnungenTable(List<Rechnung> rechnungList){
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
	
	public Object[][] updateBuchungTable(List<Buchung> buchungList) {
		log.debug("Aktualisiere Tabelle mit Buchungen...");
		Object[][] allBuchungenArray = new Object[buchungList.size()][3];
		int i = 0;
		for(Buchung b : buchungList) {
			allBuchungenArray[i][0] = b.getPferd().getId();
			allBuchungenArray[i][1] = b.getPreis();
			allBuchungenArray[i][2] = b.getStunden();
			i++;
		}
		return allBuchungenArray;
	}	
}
