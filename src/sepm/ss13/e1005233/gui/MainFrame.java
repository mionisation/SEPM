package sepm.ss13.e1005233.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame{

	public void create() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        getContentPane().add(label);

        //Display the window.
        pack();
        setVisible(true);
	}
}
