package sepm.ss13.e1005233.gui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.service.JDBCService;
import sepm.ss13.e1005233.service.Service;

import net.miginfocom.swing.MigLayout;

public class PferdPanel extends JPanel{

	private Service service;
	private JScrollPane scrollpanepferd;
	private JScrollPane scrollpaneaktiv;
	private JTable pferde;
	private JTable aktiv;
	private JButton deleteButton;
	private JButton addRechnungButton;
	private JButton addPferdButton;
	private String[] pferdColumnNames = {"ID#", "Name", "Preis", "Therapieart", "Rasse", "Kinderfreundlich"};
	private String[] aktivColumnNames = {"ID#", "Name", "Preis", "Stunden"};
	public PferdPanel() {
		super(new MigLayout());
				
		//Erstelle Pferdetabelle, uneditierbar
		service = new JDBCService();
		
		pferde = new CustomTable(updateTable(), pferdColumnNames);
		scrollpanepferd = new JScrollPane(pferde);
		add(scrollpanepferd);
		
		addPferdButton = new JButton();
		addPferdButton.setText("Pferd hinzufügen...");
		add(addPferdButton);
		
		addRechnungButton = new JButton();
		addRechnungButton.setText("Neue Rechnung erstellen...");
		add(addRechnungButton);
		
		deleteButton = new JButton();
		deleteButton.setText("Pferd löschen");
		add(deleteButton);
		
		//TODO: wieder removen wäre eigentlich nice
		Object[][] temp = {};
		aktiv = new CustomTable(temp, aktivColumnNames);
		scrollpaneaktiv = new JScrollPane(aktiv);
		add(scrollpaneaktiv);
		
	}
	
	public Object[][] updateTable(){
		List<Pferd> allPferdeList =	service.findAllUndeletedPferde();
		Object[][] allPferdeArray = new Object[allPferdeList.size()][6];
		int i = 0;
		for(Pferd p : allPferdeList) {
			allPferdeArray[i][0] = p.getId();
			allPferdeArray[i][1] = p.getName();
			allPferdeArray[i][2] = p.getPreis();
			allPferdeArray[i][3] = p.getTherapieart();
			allPferdeArray[i][4] = p.getRasse();
			allPferdeArray[i][5] = p.isKinderfreundlich();
			
			i++;
		}
		return allPferdeArray;
	}

}
