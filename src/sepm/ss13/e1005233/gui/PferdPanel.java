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
	private JScrollPane scrollpane;
	private JTable pferde;
	private JButton deleteButton;
	private JButton addButton;
	private String[] columnNames = {"ID#", "Name", "Preis", "Therapieart", "Rasse", "Kinderfreundlich"};
	public PferdPanel() {
		super(new MigLayout());
				
		//Erstelle Pferdetabelle, uneditierbar
		service = new JDBCService();
		pferde = new PferdTable(updateTable(), columnNames);
		scrollpane = new JScrollPane(pferde);
		add(scrollpane);
		
		addButton = new JButton();
		addButton.setText("Pferd hinzufügen...");
		add(addButton);
		deleteButton = new JButton();
		deleteButton.setText("Pferd löschen");
		add(deleteButton);
		
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
