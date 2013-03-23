package sepm.ss13.e1005233.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

	public void create() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pferdetherapiemanager 2013");
        
        JTabbedPane tabs = new JTabbedPane();
        PferdPanel pferde = new PferdPanel();
        RechnungPanel rechnungen = new RechnungPanel();
        tabs.addTab("Pferdeliste", pferde);
        tabs.addTab("Rechnungen", rechnungen);
        add(tabs);
        //Display the window.
        pack();
        //setSize(300, 300);
        setVisible(true);
	}
}
