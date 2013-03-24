package sepm.ss13.e1005233.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.service.JDBCService;
import sepm.ss13.e1005233.service.Service;

import net.miginfocom.swing.MigLayout;

public class PferdPanel extends JPanel implements ActionListener{

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
	private JFrame parent;
	private CustomTable ctm;
	private JTextField preisVonTextField, preisBisTextField, nameTextField, rasseTextField;
	private JMenuItem menuItem;
	private final String[] kinderfreundlich = {"(Egal)", "Ja", "Nein"};
	private final String[] therapieArten = {"(Egal)", "Hippotherapie","Heilpädagogisches Voltigieren","Heilpädagogisches Reiten"};
	private final String[] pferdColumnNames = {"ID#", "Name", "Preis", "Therapieart", "Rasse", "Kinderfreundlich"};
	private final String[] aktivColumnNames = {"ID#", "Name", "Preis", "Stunden"};
	private static final Logger log = Logger.getLogger(PferdPanel.class);
	public PferdPanel(JFrame parent) {
		super(new MigLayout());
		log.debug("Erstelle neues PferdPanel...");
		
		service = new JDBCService();
		this.parent = parent;
		//erstelle Menu
		menuBar = new JMenuBar();
		menu = new JMenu("Aktionen");
		menuBar.add(menu);
		menuItem = new JMenuItem("Neues Pferd...");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Neue Rechnung...");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("NeueRechnung");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Hilfe");
		menu.add(menuItem);
		menuItem = new JMenuItem("Beenden");
		menu.add(menuItem);	
		
		//erstelle Suchpanel
		suchPanel = new JPanel(new MigLayout());
		
		
		descSearch = new JLabel("Name:      ");
		suchPanel.add(descSearch, "split 2");
		nameTextField = new JTextField(13);
		suchPanel.add(nameTextField, "wrap");
		
		descSearch = new JLabel("Rasse:     ");
		suchPanel.add(descSearch, "split 2");
		rasseTextField = new JTextField(13);
		suchPanel.add(rasseTextField, "wrap");
		
		descSearch = new JLabel("Preis:        Von");
		suchPanel.add(descSearch, "split 4");
		preisVonTextField = new JTextField(4);
		preisVonTextField.setText("0");
		suchPanel.add(preisVonTextField, "span");
		descSearch = new JLabel("bis");
		suchPanel.add(descSearch);
		preisBisTextField = new JTextField(4);
		preisBisTextField.setText("0");
		suchPanel.add(preisBisTextField, "wrap");
		
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
				
		suchButton = new JButton("Suchen");
		suchButton.setActionCommand("SUCHEN");
		suchButton.addActionListener(this);
		suchPanel.add(suchButton, "split 2");
		resetButton = new JButton("Reset");
		resetButton.setActionCommand("RESET");
		resetButton.addActionListener(this);
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
		ctm = new CustomTable(updateTable(service.findAllUndeletedPferde()), pferdColumnNames);
		pferde = new JTable(ctm);
		scrollpanepferd = new JScrollPane(pferde);
		add(scrollpanepferd);
		
		
		//Erstelle Panel für neue Rechnungen
		neuRechnungPanel = new JPanel(new MigLayout());
		
		addZuRechnungButton = new JButton("Hinzufügen");
		neuRechnungPanel.add(addZuRechnungButton, "split 2");
		
		rechnungSpeichernButton = new JButton("Rechnung speichern");
		rechnungSpeichernButton.addActionListener(this);
		rechnungSpeichernButton.setActionCommand("RechnungSpeichern");
		neuRechnungPanel.add(rechnungSpeichernButton, "wrap");
		
		Object[][] temp = {};
		aktiv = new JTable(new CustomTable(temp, aktivColumnNames));
		scrollpaneaktiv = new JScrollPane(aktiv);
		neuRechnungPanel.add(scrollpaneaktiv);
		add(neuRechnungPanel, "dock east");
		removeRechnung();
	}
	
	public Object[][] updateTable(List<Pferd> pferdeList){
		log.debug("Aktualisiere Tabelle mit Pferden...");
		Object[][] allPferdeArray = new Object[pferdeList.size()][6];
		int i = 0;
		for(Pferd p : pferdeList) {
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
	
	/**
	 * Ändert das angezeigte Bild mit dem in path angegebenen.
	 * @param path der Dateipfad zum Bild
	 */
	public void setPferdIcon(String path) {
		log.info("Bereite Änderung vom Bild vor...");
		BufferedImage myPicture = null;
		Image im = null;
		//reset Text and Icon
		picPferd.setIcon(null);
		picPferd.setText("");

		try {
			myPicture = ImageIO.read(new File(path));
			log.debug("Image size is " + myPicture.getWidth() + " x " + myPicture.getHeight());
			im = myPicture.getScaledInstance(200, (int) (200 * ((double)myPicture.getHeight()/myPicture.getWidth())), Image.SCALE_SMOOTH);
			log.info("Bild vom Pferd geladen!");
			picPferd.setIcon(new ImageIcon(im));
		} catch (IOException e) {
			log.info("Kein zugehöriges Bild zum Pferd gespeichert!");
			picPferd.setText("Bild konnte nicht geladen werden.");
		}
	}
	/**
	 * Gibt das Menue zurück
	 * @return
	 */
	public JMenuBar getJMenuBar() {
		return menuBar;
	}
	/**
	 * Löscht den Rechnungsteil aus dem GUI
	 */
	public void removeRechnung() {
		remove(neuRechnungPanel);
	}
	/**
	 * Fügt den Rechnungsteil zum GUI hinzu
	 */
	public void addRechnung() {
		add(neuRechnungPanel, "dock east");
	}
	/**
	 * EventHandler. Führt Aktionen aus wenn Buttons
	 * und Menüpunkte gedrückt werden
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "SUCHEN":
			log.info("Starte Suchvorgang...");
			System.out.println(nameTextField.getText() + "  " + rasseTextField.getText() + "   " + therapieAuswahl.getSelectedIndex());
			String therapieart = "";
			String kinderfreundlich = "";
			switch (therapieAuswahl.getSelectedIndex()) {
			case 0:
				therapieart = "";	
				break;
			case 1:
				therapieart = "Hippotherapie";
				break;
			case 2:
				therapieart = "HPV";
				break;
			case 3:
				therapieart = "HPR";
				break;
			}
			switch (kinderAuswahl.getSelectedIndex()) {
			case 0:
				kinderfreundlich = "";
				break;
			case 1:
				kinderfreundlich = "TRUE";
				break;
			case 2:
				kinderfreundlich = "FALSE";
				break;
			}

			ctm = new CustomTable(updateTable(service.findBy(new SuchPferd(nameTextField.getText(), therapieart,
					rasseTextField.getText(), Double.parseDouble(preisVonTextField.getText()),
					Double.parseDouble(preisBisTextField.getText()), kinderfreundlich))), pferdColumnNames);
		    pferde.setModel(ctm);
		    ctm.fireTableDataChanged();
		    ctm.fireTableStructureChanged();
			
			break;
		case "RESET":
			log.info("Resette Eingabefeld...");
			preisVonTextField.setText("0");
			preisBisTextField.setText("0");
			nameTextField.setText(null);
			rasseTextField.setText(null);
			therapieAuswahl.setSelectedIndex(0);
			kinderAuswahl.setSelectedIndex(0);
			
			break;
		case "NeueRechnung":
			log.info("Erstelle Rechnungsfeld...");
			addRechnung();
			repaint();
			parent.repaint();
			parent.pack();
			break;
		case "RechnungSpeichern":
			log.info("Speichere Rechnung ab...");
			removeRechnung();
			repaint();
			parent.repaint();
			parent.pack();
			break;
		default:
			break;
		}
		
	}

}
