package sepm.ss13.e1005233.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.service.JDBCService;
import sepm.ss13.e1005233.service.Service;

import net.miginfocom.swing.MigLayout;

public class PferdPanel extends JPanel{

	private Service service;
	private JScrollPane scrollpanepferd, scrollpaneaktiv;
	private JTable pferde, aktiv;
	private JButton deleteButton, updatePferdButton, suchButton, addZuRechnungButton, rechnungSpeichernButton, resetButton;
	private JPanel neuRechnungPanel, suchPanel;
	private JLabel picPferd;
	private JLabel descSearch;
	private JComboBox<String> therapieAuswahl, kinderAuswahl;
	private JMenuBar menuBar;
	private JMenu menu;
	private JTextField textField;
	private JMenuItem menuItem;
	private final String[] kinderfreundlich = {"(Egal)", "Ja", "Nein"};
	private final String[] therapieArten = {"(Egal)", "Hippotherapie","Heilpädagogisches Voltigieren","Heilpädagogisches Reiten"};
	private final String[] pferdColumnNames = {"ID#", "Name", "Preis", "Therapieart", "Rasse", "Kinderfreundlich"};
	private final String[] aktivColumnNames = {"ID#", "Name", "Preis", "Stunden"};
	private static final Logger log = Logger.getLogger(PferdPanel.class);
	public PferdPanel() {
		super(new MigLayout());
				
		service = new JDBCService();
		
		//erstelle Menu
		menuBar = new JMenuBar();
		menu = new JMenu("Aktionen");
		menuBar.add(menu);
		menuItem = new JMenuItem("Neues Pferd...");
		menu.add(menuItem);
		menuItem = new JMenuItem("Neue Rechnung...");
		menu.add(menuItem);
		menuItem = new JMenuItem("Hilfe");
		menu.add(menuItem);
		menuItem = new JMenuItem("Beenden");
		menu.add(menuItem);		
		
		//erstelle Suchpanel
		suchPanel = new JPanel(new MigLayout());
		
		
		descSearch = new JLabel("Name:      ");
		suchPanel.add(descSearch, "split 2");
		textField = new JTextField(13);
		suchPanel.add(textField, "wrap");
		
		descSearch = new JLabel("Rasse:     ");
		suchPanel.add(descSearch, "split 2");
		textField = new JTextField(13);
		suchPanel.add(textField, "wrap");
		
		descSearch = new JLabel("Preis:        Von");
		suchPanel.add(descSearch, "split 4");
		textField = new JTextField(4);
		suchPanel.add(textField, "span");
		descSearch = new JLabel("bis");
		suchPanel.add(descSearch);
		textField = new JTextField(4);
		suchPanel.add(textField, "wrap");
		
		descSearch = new JLabel("Therapieart:");
		suchPanel.add(descSearch, "wrap");
		therapieAuswahl = new JComboBox<String>(therapieArten);
		suchPanel.add(therapieAuswahl, "wrap");
		therapieAuswahl.setSelectedIndex(0);
		
		descSearch = new JLabel("Kinderfreundlich:");
		suchPanel.add(descSearch, "wrap");
		kinderAuswahl = new JComboBox<String>(kinderfreundlich);
		suchPanel.add(kinderAuswahl, "wrap");
		therapieAuswahl.setSelectedIndex(0);
				
		//TODO reset button
		suchButton = new JButton("Suchen");
		suchPanel.add(suchButton, "split 2");
		resetButton = new JButton("Reset");
		suchPanel.add(resetButton, "wrap");
		
		//erstelle Pferd-profilbild
		picPferd = new JLabel("Pferd auswählen um Bild anzuzeigen.");
		setPferdIcon("C:\\Users\\mion\\Dropbox\\Studium\\4.Semester\\SEPM\\Angabe Einzelbeispiel SS2013\\Beispielfotos\\HeilpaedagogischesVoltigieren(HPV).jpg");
		suchPanel.add(picPferd, "span, gaptop 50");
		
		add(suchPanel, "dock west");
		
		//Erstelle Buttons für Pferdfunktionalitäten		
		updatePferdButton = new JButton("Bearbeiten");
		add(updatePferdButton, "split 2, gapleft 5, gaptop 7");
		
		deleteButton = new JButton("Löschen");
		add(deleteButton, "wrap, gaptop 5");
				
		//Erstelle Pferdetabelle, uneditierbar
		pferde = new CustomTable(updateTable(), pferdColumnNames);
		scrollpanepferd = new JScrollPane(pferde);
		add(scrollpanepferd);
		
		
		//Erstelle Panel für neue Rechnungen
		neuRechnungPanel = new JPanel(new MigLayout());
		
		addZuRechnungButton = new JButton("Hinzufügen");
		neuRechnungPanel.add(addZuRechnungButton, "split 2");
		
		rechnungSpeichernButton = new JButton("Rechnung speichern");
		neuRechnungPanel.add(rechnungSpeichernButton, "wrap");
		
		//TODO: wieder removen wäre eigentlich nice
		Object[][] temp = {};
		aktiv = new CustomTable(temp, aktivColumnNames);
		scrollpaneaktiv = new JScrollPane(aktiv);
		neuRechnungPanel.add(scrollpaneaktiv);
		add(neuRechnungPanel, "dock east");

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
	
	public void setPferdIcon(String path) {
		
		BufferedImage myPicture = null;
		Image im = null;
		//reset Text and Icon
		picPferd.setIcon(null);
		picPferd.setText("");

		try {
			myPicture = ImageIO.read(new File(path));
			log.debug("Image size is " + myPicture.getWidth() + " x " + myPicture.getHeight());
			im = myPicture.getScaledInstance(200, (int) (200 * ((double)myPicture.getHeight()/myPicture.getWidth())), Image.SCALE_SMOOTH);
			picPferd.setIcon(new ImageIcon(im));
		} catch (IOException e) {
			picPferd.setText("Bild konnte nicht geladen werden.");
		}
	}
	
	public JMenuBar getJMenuBar() {
		return menuBar;
	}
	
	//TODO implement
	public void removeRechnung() {
		remove(neuRechnungPanel);
	}
	
	public void addRechnung() {
		
		add(neuRechnungPanel, "dock east");
	}

}
