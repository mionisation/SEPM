package sepm.ss13.e1005233.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomTable extends DefaultTableModel{
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
