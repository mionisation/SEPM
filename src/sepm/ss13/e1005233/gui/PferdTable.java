package sepm.ss13.e1005233.gui;

import javax.swing.JTable;

public class PferdTable extends JTable{
	public PferdTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public PferdTable() {
		super();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
