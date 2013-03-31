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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.PferdPersistenceException;
import sepm.ss13.e1005233.exceptions.PferdValidationException;
import sepm.ss13.e1005233.service.JDBCService;
import sepm.ss13.e1005233.service.Service;

import net.miginfocom.swing.MigLayout;

public class PferdPanel extends JPanel implements ActionListener{

	private Service service;
	private JScrollPane scrollpanepferd, scrollpaneaktiv;
	private JTable pferde, aktiv;
	private JButton deleteButton, updatePferdButton, suchButton,
	addZuRechnungButton, rechnungSpeichernButton, resetButton, cancelPferdButton,
	addPferdButton, rechnungAbbrechenButton, picButton, editPicButton, editPferdButton;
	private JPanel neuRechnungPanel, suchPanel,	neuPferdPanel, editPferdPanel;
	private JLabel picPferd, pferdLabel, editPicLabel, descSearch, descInsert, picLabel, editInsert;
	private JComboBox<String> therapieAuswahl, kinderAuswahl, therapieForm, editTherapieForm;
	private JMenuBar menuBar;
	private JMenu menu;
	private Pferd p;
	private String selectedPic;
	private JCheckBox insertKinder, editInsertKinder;
	private JFrame parent;
	private PferdeTable ctm;
	private AktivTable atm;
	private JFileChooser picChooser;
	private ListSelectionListener lsl= new ListSelectionListener() {
	      @Override
		public void valueChanged(ListSelectionEvent e) {
	    	  if(pferde.getSelectedRow()<0 || pferde.getSelectedColumn() < 0) {
	    		  return;
	    	  }
	    	  setPferdIcon(service.getPferd(new Pferd((int) pferde.getValueAt(pferde.getSelectedRow(), 0))).getFoto());
	      }
	    };
	private JTextField preisVonTextField, preisBisTextField, nameTextField, rasseTextField, nameForm,
	 editNameForm, editPreisForm, editRasseForm, rasseForm, preisForm;
	private JMenuItem menuItem;
	private String therapieart;
	private final Object[][] temp = {};
	private final String[] loeschen = {"Ja", "Nein"};
	private final String[] kinderfreundlich = {"(Egal)", "Ja", "Nein"};
	private final String[] therapieArten = {"(Egal)", "Hippotherapie","Heilp�dagogisches Voltigieren","Heilp�dagogisches Reiten"};
	private final String[] insertTherapieArten = {"Hippotherapie","Heilp�dagogisches Voltigieren","Heilp�dagogisches Reiten"};
	private final String[] pferdColumnNames = {"ID#", "Name", "Preis", "Therapieart", "Rasse", "F�r Kinder"};
	private final String[] aktivColumnNames = {"ID#", "Name", "Preis", "Stunden"};
	private ListSelectionModel ldm;
	private int editPferdId;
	private static final Logger log = Logger.getLogger(PferdPanel.class);
	public PferdPanel(JFrame parent, JDBCService service) {
		super(new MigLayout());
		log.debug("Erstelle neues PferdPanel...");
		
		this.service = service;
		picChooser = new JFileChooser();
		selectedPic = "";
		picChooser.setFileFilter(new BildFilter());
		this.parent = parent;
		//erstelle Menu
		menuBar = new JMenuBar();
		menu = new JMenu("Aktionen");
		menuBar.add(menu);
		menuItem = new JMenuItem("Neues Pferd...");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("NeuesPferdForm");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Neue Rechnung...");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("NeueRechnung");
		menu.add(menuItem);
		
		//TODO vielleicht hilfe schreiben
		/*menuItem = new JMenuItem("Hilfe");
		menu.add(menuItem);*/
		
		menuItem = new JMenuItem("Beenden");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("Beenden");
		menu.add(menuItem);	

		//erstelle Suchpanel
		suchPanel = new JPanel(new MigLayout());
		
		descSearch = new JLabel("SUCHE:");
		suchPanel.add(descSearch, " wrap");
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
		picPferd = new JLabel("Pferd ausw�hlen um Bild anzuzeigen.");
		setPferdIcon("Profilbilder/logo.jpg");
		descSearch = new JLabel("Profilbild:");
		suchPanel.add(descSearch, "wrap, gaptop 35");
		suchPanel.add(picPferd, "span");
		
		add(suchPanel, "dock west");
		
		//Erstelle Buttons f�r Pferdfunktionalit�ten
		pferdLabel = new JLabel("Ausgew�hltes Pferd: ");
		add(pferdLabel, "split 3, gapleft 5");
		
		updatePferdButton = new JButton("Bearbeiten");
		updatePferdButton.setActionCommand("PferdBearbeiten");
		updatePferdButton.addActionListener(this);
		add(updatePferdButton, "gapleft 5, gaptop 7");
		
		deleteButton = new JButton("L�schen");
		deleteButton.setActionCommand("PferdL�schen");
		deleteButton.addActionListener(this);
		add(deleteButton, "wrap, gaptop 5");
				
		//Erstelle Pferdetabelle, uneditierbar
		ctm = new PferdeTable(updateTable(service.findAllUndeletedPferde()), pferdColumnNames);
		pferde = new JTable(ctm);
		ldm = pferde.getSelectionModel();
		ldm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ldm.addListSelectionListener(lsl);
		scrollpanepferd = new JScrollPane(pferde);
		add(scrollpanepferd);
		
		
		//Erstelle Panel f�r neue Rechnungen
		neuRechnungPanel = new JPanel(new MigLayout());
		
		addZuRechnungButton = new JButton("Hinzuf�gen");
		addZuRechnungButton.addActionListener(this);
		addZuRechnungButton.setActionCommand("RechnungHinzuf�gen");
		neuRechnungPanel.add(addZuRechnungButton, "split 3");
		
		rechnungSpeichernButton = new JButton("Rechnung speichern");
		rechnungSpeichernButton.addActionListener(this);
		rechnungSpeichernButton.setActionCommand("RechnungSpeichern");
		neuRechnungPanel.add(rechnungSpeichernButton);
		
		rechnungAbbrechenButton = new JButton("Abbrechen");
		rechnungAbbrechenButton.addActionListener(this);
		rechnungAbbrechenButton.setActionCommand("RechnungAbbrechen");
		neuRechnungPanel.add(rechnungAbbrechenButton, "wrap");
		
		atm = new AktivTable(temp, aktivColumnNames);
		aktiv = new JTable(atm);
		scrollpaneaktiv = new JScrollPane(aktiv);
		ldm = aktiv.getSelectionModel();
		ldm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		neuRechnungPanel.add(scrollpaneaktiv);
		add(neuRechnungPanel, "dock east");
		removeRechnung();
		
		//Erstelle Panel f�r neue Pferde
		neuPferdPanel = new JPanel(new MigLayout());
		
		descInsert = new JLabel("Neues Pferd hinzuf�gen:");
		neuPferdPanel.add(descInsert, "wrap");
		
		descInsert = new JLabel("Name:  ");
		neuPferdPanel.add(descInsert, "split 2");
		nameForm = new JTextField(13);
		neuPferdPanel.add(nameForm, "wrap");		
		
		descInsert = new JLabel("Rasse: ");
		neuPferdPanel.add(descInsert, "split 2");
		rasseForm = new JTextField(13);
		neuPferdPanel.add(rasseForm, "wrap");
		
		descInsert = new JLabel("Stundenpreis:  ");
		neuPferdPanel.add(descInsert, "split 2");
		preisForm = new JTextField(9);
		neuPferdPanel.add(preisForm, "wrap");
		
		descInsert = new JLabel("Therapieart:");
		neuPferdPanel.add(descInsert, "wrap");
		therapieForm = new JComboBox<String>(insertTherapieArten);
		neuPferdPanel.add(therapieForm, "wrap");
		therapieForm.setSelectedIndex(-1);
		
		picButton = new JButton("W�hle Profilbild");
		picButton.addActionListener(this);
		picButton.setActionCommand("PicAuswahl");
		neuPferdPanel.add(picButton, "wrap");
		picLabel = new JLabel("Kein Bild ausgew�hlt.");
		neuPferdPanel.add(picLabel, "wrap");
		
		insertKinder = new JCheckBox("Kinderfreundlich");
		neuPferdPanel.add(insertKinder, "wrap");
		
		addPferdButton = new JButton("Speichern");
		addPferdButton.addActionListener(this);
		addPferdButton.setActionCommand("NeuesPferdSpeichern");
		neuPferdPanel.add(addPferdButton, "split 2");
		
		cancelPferdButton = new JButton("Abbrechen");
		cancelPferdButton.addActionListener(this);
		cancelPferdButton.setActionCommand("NeuesPferdAbbrechen");
		neuPferdPanel.add(cancelPferdButton, "wrap");
		
		add(neuPferdPanel, "dock east");
		removePferdForm();
		
		
		//erstelle Panel zum Bearbeiten von Pferden
		editPferdPanel = new JPanel(new MigLayout());
		
		editInsert = new JLabel("Pferd bearbeiten:");
		editPferdPanel.add(editInsert, "wrap");
		
		editInsert = new JLabel("Name:  ");
		editPferdPanel.add(editInsert, "split 2");
		editNameForm = new JTextField(13);
		editPferdPanel.add(editNameForm, "wrap");		
		
		editInsert = new JLabel("Rasse: ");
		editPferdPanel.add(editInsert, "split 2");
		editRasseForm = new JTextField(13);
		editPferdPanel.add(editRasseForm, "wrap");
		
		editInsert = new JLabel("Stundenpreis:  ");
		editPferdPanel.add(editInsert, "split 2");
		editPreisForm = new JTextField(9);
		editPferdPanel.add(editPreisForm, "wrap");
		
		editInsert = new JLabel("Therapieart:");
		editPferdPanel.add(editInsert, "wrap");
		editTherapieForm = new JComboBox<String>(insertTherapieArten);
		editPferdPanel.add(editTherapieForm, "wrap");
		editTherapieForm.setSelectedIndex(-1);
		
		editPicButton = new JButton("W�hle Profilbild");
		editPicButton.addActionListener(this);
		editPicButton.setActionCommand("PicAuswahl");
		editPferdPanel.add(editPicButton, "wrap");
		editPicLabel = new JLabel("Kein Bild ausgew�hlt.");
		editPferdPanel.add(editPicLabel, "wrap");
		
		editInsertKinder = new JCheckBox("Kinderfreundlich");
		editPferdPanel.add(editInsertKinder, "wrap");
		
		editPferdButton = new JButton("Speichern");
		editPferdButton.addActionListener(this);
		editPferdButton.setActionCommand("PferdBearbeitenSpeichern");
		editPferdPanel.add(editPferdButton, "split 2");
		
		cancelPferdButton = new JButton("Abbrechen");
		cancelPferdButton.addActionListener(this);
		cancelPferdButton.setActionCommand("PferdBearbeitenAbbrechen");
		editPferdPanel.add(cancelPferdButton, "wrap");
		
		add(editPferdPanel, "dock east");
		removeEditPferd();
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
	 * �ndert das angezeigte Bild mit dem in path angegebenen.
	 * @param path der Dateipfad zum Bild
	 */
	public void setPferdIcon(String path) {
		log.info("Bereite �nderung vom Bild vor...");
		BufferedImage myPicture = null;
		Image im = null;
		//reset Text and Icon
		picPferd.setIcon(null);
		picPferd.setText("");

		try {
			myPicture = ImageIO.read(new File(path));
			log.debug("Image size is " + myPicture.getWidth() + " x " + myPicture.getHeight());
			im = myPicture.getScaledInstance(200, (int) (200 * ((double)myPicture.getHeight()/myPicture.getWidth())), Image.SCALE_SMOOTH);
			if(myPicture.getHeight() > myPicture.getWidth())
				im = im.getScaledInstance((int) (170 * ((double)myPicture.getWidth()/myPicture.getHeight())), 170, Image.SCALE_SMOOTH);
			log.info("Bild vom Pferd geladen!");
			picPferd.setIcon(new ImageIcon(im));
		} catch (IOException e) {
			log.info("Kein zugeh�riges Bild zum Pferd gespeichert!");
			picPferd.setText("Bild konnte nicht geladen werden.");
		}
	}
	/**
	 * Gibt das Menue zur�ck
	 * @return
	 */
	public JMenuBar getJMenuBar() {
		return menuBar;
	}
	/**
	 * L�scht den Rechnungsteil aus dem GUI
	 */
	public void removeRechnung() {
		log.info("L�sche Rechnungdialog...");
		remove(neuRechnungPanel);
	}
	/**
	 * F�gt den Rechnungsteil zum GUI hinzu
	 */
	public void addRechnung() {
		log.info("F�ge Rechnungdialog hinzu...");
		add(neuRechnungPanel, "dock east");
	}
	/**
	 * F�gt das Pferdformular zum GUI hinzu
	 */
	public void addPferdForm() {
		log.info("F�ge Pferdformular hinzu...");
		add(neuPferdPanel, "dock east");
	}
	/**
	 * L�scht das Pferdformular aus dem GUI
	 */
	public void removePferdForm() {
		log.info("L�sche Pferdformular...");
		remove(neuPferdPanel);
	}
	/**
	 * F�gt das Bearbeitenformular zum GUI hinzu
	 */
	public void addEditPferd() {
		log.info("F�ge Pferdformular zur Bearbeitung hinzu...");
		add(editPferdPanel, "dock east");
	}
	/**
	 * L�scht das Bearbeitenformular aus dem GUI
	 */
	public void removeEditPferd() {
		log.info("L�sche Formular zur Bearbeitung...");
		remove(editPferdPanel);
	}
	
	/**
	 * Aktualisiert den Frame
	 */
	public void updateFrame() {
		log.info("Aktualisiere Frame...");
		repaint();
		parent.repaint();
		parent.pack();
	}
	
	/**
	 * Durchsucht die Datenbank mit den Kriterien, die im Suchfeld angegeben sind.
	 * Aktualisiert dann die Tabelle mit den gefundenen Ergebnissen
	 */
	public void startSuche() {
		log.info("Starte Suchvorgang...");
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

		try {
			ctm = new PferdeTable(updateTable(service.findBy(new SuchPferd(nameTextField.getText(), therapieart,
					rasseTextField.getText(), Double.parseDouble(preisVonTextField.getText()),
					Double.parseDouble(preisBisTextField.getText()), kinderfreundlich))), pferdColumnNames);
		    pferde.setModel(ctm);
		    ctm.fireTableDataChanged();
		    ctm.fireTableStructureChanged();
		} catch (NumberFormatException e2) {
			log.debug("Eingabe ist zu lang!");
			JOptionPane.showMessageDialog(this,"Preisangabe muss eine (Dezimal-)Zahl sein.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	/**
	 * Gibt das in der Tabelle ausgew�hlte Pferd zur�ck
	 * @return das ausgew�hlte Pferd
	 */
	public Pferd getSelectedPferd() {
		int id =(int)pferde.getValueAt(pferde.getSelectedRow(), 0);
		log.info("Gebe ausgew�hltes Pferd zur�ck mit der ID: " + id);
		return new Pferd(id);
	}
	
	/**
	 * Setzt das Formular zum Einf�gen neuer Pferde zur�ck
	 */
	public void resetInsertPferdForm() {
		log.debug("Setze Pferdformular zur�ck...");
		nameForm.setText("");
		rasseForm.setText("");
		preisForm.setText("");
		insertKinder.setSelected(false);
		therapieForm.setSelectedIndex(-1);
	}
	/**
	 * EventHandler. F�hrt Aktionen aus wenn Buttons
	 * und Men�punkte gedr�ckt werden
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "SUCHEN":
			startSuche();
			break;
		case "RESET":
			log.info("Resette Eingabefeld...");
			preisVonTextField.setText("0");
			preisBisTextField.setText("0");
			nameTextField.setText(null);
			rasseTextField.setText(null);
			setPferdIcon("Profilbilder/logo.jpg");
			therapieAuswahl.setSelectedIndex(0);
			kinderAuswahl.setSelectedIndex(0);
			startSuche();
			break;
		case "NeueRechnung":
			log.info("Erstelle Rechnungsfeld...");
			removePferdForm();
			removeEditPferd();
			addRechnung();
			updateFrame();
			break;
		case "RechnungSpeichern":
			log.info("Speichere Rechnung ab...");
			removeRechnung();
			updateFrame();
			break;
		case "RechnungHinzuf�gen":
			int id = (int) pferde.getValueAt(pferde.getSelectedRow(), 0);
			log.info("F�ge Pferd zur Rechnung hinzu mit der ID " + id);
			//TODO stundenabfrage
			
			atm = new AktivTable(temp, aktivColumnNames);
			aktiv.setModel(atm);
			atm.fireTableDataChanged();
			//TODO erase, do shit
			//aktiv.setValueAt(id, 0, 0);
			
			break;
		case "RechnungAbbrechen":
			log.info("Breche Anlegen neuer Rechnung ab...");
			removeRechnung();
			updateFrame();
			break;
		case "Beenden":
			log.debug("Beende Programm...");
			System.exit(0);
			break;
		case "NeuesPferdForm":
			log.info("Bereite Form f�r neues Pferd vor...");
			removeRechnung();
			removeEditPferd();
			addPferdForm();
			picChooser.setSelectedFile(new File(""));
			updateFrame();
			break;
		case "NeuesPferdAbbrechen":
			log.info("Breche Vorgang zur Erstellung des neuen Pferds ab, l�sche Pferdformular...");
			resetInsertPferdForm();
			removePferdForm();
			updateFrame();
			break;
		case "NeuesPferdSpeichern":
			log.info("Speichere neu angelegtes Pferd ab...");
			
			therapieart = "";
			switch (therapieForm.getSelectedIndex()) {
			case -1:
				JOptionPane.showMessageDialog(this,"Gebe eine Therapieart an.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
				return;
			case 0:
				therapieart = "Hippotherapie";
				break;
			case 1:
				therapieart = "HPV";
				break;
			case 2:
				therapieart = "HPR";
				break;
			}
			if(selectedPic.isEmpty() || selectedPic == null) {
				JOptionPane.showMessageDialog(this,"Bitte gebe ein Bild an.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
			p = new Pferd(service.getNewId(), nameForm.getText(), selectedPic, Double.parseDouble(preisForm.getText()),
					therapieart, rasseForm.getText(), insertKinder.isSelected(), false);
			service.insertPferd(p);
			} catch (NumberFormatException e3) {
				log.debug("Eingabe ist keine Dezimalzahl!");
				JOptionPane.showMessageDialog(this,"Preisangabe muss eine (Dezimal-)Zahl sein.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			} catch (PferdValidationException e4) {
				log.debug("Eingabe darf nicht leer sein!");
				JOptionPane.showMessageDialog(this,"Name darf nicht leer sein.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			} catch(PferdPersistenceException e5) {
				log.debug("Eingabe ist zu lang!");
				JOptionPane.showMessageDialog(this,"Eingabe ist zu lang!","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			}
						
			resetInsertPferdForm();
			removePferdForm();
			startSuche();
			updateFrame();
			break;
		case "PferdL�schen":
			log.info("L�sche ausgew�hltes Pferd...");
			if(pferde.getSelectedRow() < 0 || pferde.getSelectedColumn() < 0) {
				JOptionPane.showMessageDialog(this,"W�hle eine Zeile aus.","Auswahlfehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int dec = JOptionPane.showOptionDialog(parent, "Wollen sie das Pferd wirklich l�schen?",
					"Pferd l�schen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, loeschen, loeschen[0]);
			log.debug("Entscheidung ist: " + dec);
			if(dec != 0)
				return;
			service.deletePferd(getSelectedPferd());
			startSuche();
			updateFrame();
			break;
		case "PferdBearbeiten":
			log.info("Bearbeite ausgew�hltes Pferd...");
			if(pferde.getSelectedRow() < 0 || pferde.getSelectedColumn() < 0) {
				JOptionPane.showMessageDialog(this,"W�hle eine Zeile aus.","Auswahlfehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			removeRechnung();
			removePferdForm();
			addEditPferd();
			updateFrame();
			Pferd p = service.getPferd(getSelectedPferd());
			editPferdId = p.getId();
			editNameForm.setText(p.getName());
			editPreisForm.setText(Double.toString(p.getPreis()));
			editRasseForm.setText(p.getRasse());
			editInsertKinder.setSelected(p.isKinderfreundlich());
			picChooser.setSelectedFile(new File(p.getFoto()));
			selectedPic = p.getFoto();
			editPicLabel.setText(BildFilter.getFileName(p.getFoto()));
			switch (p.getTherapieart()) {
			case "Hippotherapie":
				editTherapieForm.setSelectedIndex(0);
				break;
			case "HPV":
				editTherapieForm.setSelectedIndex(1);
				break;
			case  "HPR":
				editTherapieForm.setSelectedIndex(2);
				break;
			}
			break;
		case "PicAuswahl":
			log.info("W�hle Bild aus...");
			int val = picChooser.showOpenDialog(this);
			if (val == JFileChooser.APPROVE_OPTION) {
				selectedPic = picChooser.getSelectedFile().getAbsolutePath();
				picLabel.setText("Profilbild ausgew�hlt.");
			}
			break;
		case "PferdBearbeitenAbbrechen":
			log.info("Breche Vorgang zur Bearbeitung ab...");
			removeEditPferd();
			updateFrame();
			break;
		case "PferdBearbeitenSpeichern":
			log.info("Speichere bearbeitetes Pferd ab...");
			therapieart = "";
			switch (editTherapieForm.getSelectedIndex()) {
			case -1:
				JOptionPane.showMessageDialog(this,"Gebe eine Therapieart an.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
				return;
			case 0:
				therapieart = "Hippotherapie";
				break;
			case 1:
				therapieart = "HPV";
				break;
			case 2:
				therapieart = "HPR";
				break;
			}
			if(selectedPic.isEmpty() || selectedPic == null) {
				JOptionPane.showMessageDialog(this,"Bitte gebe ein Bild an.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
			p = new Pferd(editPferdId, editNameForm.getText(), selectedPic, Double.parseDouble(editPreisForm.getText()),
					therapieart, editRasseForm.getText(), editInsertKinder.isSelected(), false);
			service.updatePferd(p);
			} catch (NumberFormatException e3) {
				log.debug("Eingabe ist keine Dezimalzahl!");
				JOptionPane.showMessageDialog(this,"Preisangabe muss eine (Dezimal-)Zahl sein.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			} catch (PferdValidationException e4) {
				log.debug("Eingabe darf nicht leer sein!");
				JOptionPane.showMessageDialog(this,"Name darf nicht leer sein.","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			} catch(PferdPersistenceException e5) {
				log.debug("Eingabe ist zu lang!");
				JOptionPane.showMessageDialog(this,"Eingabe ist zu lang!","Eingabefehler", JOptionPane.ERROR_MESSAGE);
				break;
			}
			removeEditPferd();
			startSuche();
			updateFrame();
			break;
		default:
			break;
		}
		
	}

	public void addMenus() {		
		menuItem = new JMenuItem("Neues Pferd...");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("NeuesPferdForm");
		menu.add(menuItem, 0);
		
		menuItem = new JMenuItem("Neue Rechnung...");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("NeueRechnung");
		menu.add(menuItem, 1);
		
	}

	public void deleteMenus() {
		menu.remove(menu.getMenuComponent(0));
		menu.remove(menu.getMenuComponent(0));
		
		
	}

}
