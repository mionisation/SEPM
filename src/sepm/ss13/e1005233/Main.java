//package sepm.ss13.e1005233;
//
//import javax.swing.*;        
//
//import sepm.ss13.e1005233.gui.MainFrame;
//
//public class Main {
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI() {
//        //Create and set up the window.
//        MainFrame frame = new MainFrame();
//        frame.create();
//    }
//
//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
//}



package sepm.ss13.e1005233;


import java.sql.*;

import sepm.ss13.e1005233.dao.ConnectionTool;
import sepm.ss13.e1005233.gui.MainFrame;

public class Main {

	
	public static void main(String[] args) {
		
		MainFrame mf = new MainFrame();
		mf.create();
//		ConnectionTool ct = new ConnectionTool();
//		Connection c = null;
//
//		try {
//			ct.openConnection();
//			c = ct.getConnection();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//
//		try {
//			Statement demo = c.createStatement();
//			ResultSet result = demo.executeQuery("SELECT * FROM PFERDE");
//			
//			while(result.next()) {
//			System.out.println(result.getString(1));
//			System.out.println(result.getString(2));
//			System.out.println(result.getString(3));
//			System.out.println(result.getString(4));
//			System.out.println(result.getString(5));
//			System.out.println(result.getString(6));
//			System.out.println(result.getString(7));
//			System.out.println(result.getString(8));
//
//			}
//			
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
		
	}
	

}
