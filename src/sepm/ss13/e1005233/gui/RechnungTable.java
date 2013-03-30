package sepm.ss13.e1005233.gui;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

public class RechnungTable extends DefaultTableModel{
	public RechnungTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public RechnungTable() {
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
                return Timestamp.class;
            case 1:
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return Integer.class;
            case 4:
                return String.class;
            case 5:
                return Long.class;
            default:
                return String.class;
        }
    }
	
	
}
