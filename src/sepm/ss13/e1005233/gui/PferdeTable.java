package sepm.ss13.e1005233.gui;

import javax.swing.table.DefaultTableModel;

public class PferdeTable extends DefaultTableModel{
	public PferdeTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public PferdeTable() {
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
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Boolean.class;
            default:
                return String.class;
        }
    }
	
	
}
