package src;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import src.UI.IntervalGroupedTableModel;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class ExcelPrinter
{
	public void exportToExcel(TableModel tableModel, String fileName) {
	    try (Workbook workbook = new XSSFWorkbook())
		{
			Sheet sheet = workbook.createSheet("Data");
			// Create header row
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < tableModel.getColumnCount(); col++) {
			    Cell cell = headerRow.createCell(col);
			    cell.setCellValue(tableModel.getColumnName(col));
			}
			// Add data
			int rowNum = 1;
			for (int row = 0; row < tableModel.getRowCount(); row++) {
			    Row dataRow = sheet.createRow(rowNum++);
			    for (int col = 0; col < tableModel.getColumnCount(); col++) {
			        Cell cell = dataRow.createCell(col);
			        cell.setCellValue(tableModel.getValueAt(row, col).toString());
			    }
			}
			// Write the workbook to a file
			try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			    workbook.write(fileOut);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportToExcel(IntervalGroupedTableModel tableModel, String fileName) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        // Create a sheet for each group
	        for (int group = 1; group <= tableModel.groupCount; group++) {
	            Sheet sheet = workbook.createSheet("Group " + group);

	            // Create header row
	            Row headerRow = sheet.createRow(0);
	            for (int i = 1; i < tableModel.getColumnCount(); i++) {
	                Cell cell = headerRow.createCell(i);
	                cell.setCellValue(tableModel.getColumnName(i));
	                System.out.println("Cell value" + cell);
	            }

	            // Populate data rows
	            int currentRow = 1; //used to keep track of the 
	            					// current group number so
	            					// it will hide the other groupNumber col too
	            for (int i = 0; i < tableModel.getRowCount(); i++) {
	                if (group == (Integer) tableModel.getValueAt(i, 0)) {
	                    Row dataRow = sheet.createRow(currentRow++);
	                    System.out.println();

	                    //skip the first column (group_number)
	                    for (int j = 1; j < tableModel.getColumnCount(); j++) {
	                        Cell cell = dataRow.createCell(j);
	                        cell.setCellValue(tableModel.getValueAt(i, j).toString());
	                        System.out.print(cell);
	                    }
	                }
	            }
	        }
	        // Write the workbook to a file
	        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
	            workbook.write(fileOut);
	        }
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Error in parsing the table");
	    }
	}
}
