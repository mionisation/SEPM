package sepm.ss13.e1005233.gui;

import javax.swing.JTable;

public class CustomTable extends JTable{
	public CustomTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public CustomTable() {
		super();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
