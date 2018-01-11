package fr.cfi.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import fr.cfi.model.TableModel;

public class Table extends JPanel{
	private static final long serialVersionUID = -2162370977935676387L;
	JTable table= null;

	public Table() {
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new BorderLayout());
		table=new JTable();
		JScrollPane scrPane =new JScrollPane(table);
		add(scrPane, BorderLayout.CENTER);
	}
	
	
	
	public void setModel(final AbstractTableModel model){
		table.setModel(model);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("JTable test");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TableModel tableModel = new TableModel();
		Table tableExemple = new Table();
		tableExemple.setModel(tableModel);
		
		frame.setContentPane(tableExemple);
		frame.setVisible(true);
		
		tableModel.startMonitoring();
		
		
	}
}
