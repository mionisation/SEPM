package sepm.ss13.e1005233.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sepm.ss13.e1005233.service.JDBCService;

public class MainFrame extends JFrame implements ChangeListener{

	private JDBCService service;
	private  JTabbedPane tabs;
	private PferdPanel pferde;
	private RechnungPanel rechnungen;
	public void create() {
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pferdetherapie Manager 2013");
        
        tabs = new JTabbedPane();
        service = new JDBCService();
        pferde = new PferdPanel(this, service);
        rechnungen = new RechnungPanel(this, service);
        tabs.addTab("Pferdeliste", pferde);
        setJMenuBar(pferde.getJMenuBar());
        tabs.addTab("Rechnungen", rechnungen);
        tabs.addChangeListener(this);
        add(tabs);
        //Display the window.
        pack();
        setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent ce) {
		switch (tabs.getTitleAt(tabs.getSelectedIndex())) {
		case "Pferdeliste":
			pferde.addMenus();
			break;
		case "Rechnungen":
			rechnungen.findAllAndUpdateRechnungenTable();
			pferde.deleteMenus();
			pferde.removeEditPferd();
			pferde.removeRechnung();
			pferde.removePferdForm();
			pack();
			break;
		default:
			break;
		}
		
	}
}
