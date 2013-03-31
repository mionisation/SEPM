package sepm.ss13.e1005233.gui;

import javax.swing.table.DefaultTableModel;

public class AktivTable extends DefaultTableModel{
	public AktivTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
	}

	public AktivTable() {
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
                return Integer.class;
            default:
                return String.class;
        }
    }
	
	
}
