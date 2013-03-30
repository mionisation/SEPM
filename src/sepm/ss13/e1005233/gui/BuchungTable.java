package sepm.ss13.e1005233.gui;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

public class BuchungTable extends DefaultTableModel{
	public BuchungTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public BuchungTable() {
		super();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return Integer.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
            default:
                return String.class;
        }
    }
	
	
}
