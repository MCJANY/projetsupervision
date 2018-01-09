package fr.cfi.model;

import java.io.IOException;

public interface MyInterface {
	
	
	
		TableModelCsv test = new TableModelCsv(); 
		public String getColumnName(int column); 
		public int getColumnCount();
		public boolean isCellEditable(int row, int column);
		public int getRowCount();
		public Object getValueAt(int row, int column);
		public Object getTable();
		public int[] getSelectedRows();
		public void powerShell() throws IOException;
		public void readData();
		public void startMonitoring();
		public void stopMonitoring();

}
