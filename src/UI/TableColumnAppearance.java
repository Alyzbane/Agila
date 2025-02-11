package src.UI;

import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.*;
import javax.swing.RowSorter.SortKey;

public class TableColumnAppearance {

	private HashMap<String, TableColumn> removedCols;
	private TableColumnModel colModel;
	private TableColumn removedColumn; 
	private JTable tableGrid;
	private TableRowSorter<TableModel> sorter; 

	public TableColumnAppearance(JTable tableGrid)
	{
		this.tableGrid = tableGrid;
		removedCols = new HashMap<String, TableColumn>();
		sorter = new TableRowSorter<TableModel>(tableGrid.getModel());
		sorter.setSortsOnUpdates(true);
		tableGrid.setRowSorter(sorter);
	}
	
	public void updateTable(JTable tableGrid)
	{
		this.tableGrid = tableGrid;
		removedCols = new HashMap<String, TableColumn>();
		sorter = new TableRowSorter<TableModel>(tableGrid.getModel());
		sorter.setSortsOnUpdates(true);
		tableGrid.setRowSorter(sorter);
	}

	public void hideColumn(String colName) {
		if(this.tableGrid == null) return;
	    // Get the table column model and column index to remove
	    colModel = tableGrid.getColumnModel();
	    int index = colModel.getColumnIndex(colName); // replace with the index of the column to remove

	    removedColumn = colModel.getColumn(index);
	    colModel.removeColumn(removedColumn); // Remove the column
	    System.out.println("Removed Column " + removedColumn.getHeaderValue().toString());
	    removedCols.put(colName, removedColumn); // save it to a map for later use
	}

	public void showColumn(String colName) {
		if(this.tableGrid == null) return;
	    colModel = tableGrid.getColumnModel();
	    removedColumn = removedCols.get(colName);

	    if (removedColumn != null) {
	        colModel.addColumn(removedColumn);
	        int index = colModel.getColumnCount() - 1;

	        colModel.moveColumn(index, removedColumn.getModelIndex());
	        removedCols.remove(colName);
	        System.out.println("__Show Column " + index + " " + colName);
	    }
	    tableGrid.setRowSorter(sorter);
	}	
	public void printTable()
	{
	 for (int i = 0; i < tableGrid.getColumnCount(); i++) {
            System.out.print(tableGrid.getColumnModel().getColumn(i).getHeaderValue().toString());
            System.out.print("\t");
	 }
	 System.out.println();
	 for (int i = 0; i < tableGrid.getRowCount(); i++) {
	    for (int j = 0; j < tableGrid.getColumnCount(); j++) {
	        Object value = tableGrid.getValueAt(i, j);
	        System.out.print(value + "\t"); // print the value with a tab separator
	    }
	    System.out.println(); // move to the next line
	 }
	}
	
	public void filterAge(int min, int max)
	{
		TableColumnModel tcm =  tableGrid.getColumnModel();
		int ageIndex = tcm.getColumnIndex("Age");

		System.out.println("******I am  ageIndex " + ageIndex + " inside filter age " + min + " " + max);
		RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
			@Override
			public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
				int age = (Integer) entry.getValue(8);
				if (age < min || age > max) return false;

			return true;
			}
		};
		resetTable();
		sorter.setRowFilter(filter);
	}
	public void filterPurok(int min, int max)
	{
		System.out.println("I am inside filter purok " + min + " " + max);
		RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
			@Override
			public boolean include(Entry<? extends TableModel, ? extends Object> entry) {

			String purokValue = (String) entry.getValue(9);
			int purok = Integer.parseInt(purokValue);
			if((purok < min || purok > max)) return false;

			return true;
			}
		};
		resetTable();
		sorter.setRowFilter(filter);
	}
	
	
	public void filterSex(String sex) {
	    RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
	        @Override
	        public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
	            String sexValue = (String) entry.getValue(6);
	            if ("Both".equals(sex)) return true;
	            else if (sex != null && !sex.isEmpty() && !sexValue.equals(sex)) return false;

	           	return true;
	        }
	    };
	    resetTable();
	    sorter.setRowFilter(filter);
	}

	public void filterDeceased(String deceased)
	{
		System.out.println("I am inside filter ded");
		RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
			@Override
			public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
				String deceasedValue = (String) entry.getValue(10);

				if (deceasedValue.compareToIgnoreCase(deceased) != 0) {
			            return false;
			    }
				return true;
			}
		};
		resetTable();
		sorter.setRowFilter(filter);
	}
		
	

	public void resetTable()
	{
		sorter.setRowFilter(null);
	}
	
	
	public DefaultTableModel getFilteredTableModel()
	{
		int columnCount = tableGrid.getColumnCount();
		int rowCount = tableGrid.getRowCount();
		Object[][] data = new Object[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				data[i][j] = tableGrid.getValueAt(i, j);
			}
		}
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = tableGrid.getColumnName(i);
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames);

		return model;
	}
	
	public void searchTable(String query) {
	    	if(query.isEmpty()) return;

	        tableGrid.setRowSorter(sorter);
	        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
	}
}

