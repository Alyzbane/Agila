package src;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.table.TableModel;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;

import src.UI.IntervalGroupedTableModel;

public class PDFPrinter
{
	public void printTable(TableModel tableModel, String fileName) {
		//for pinting a pdf in normal mode
	    System.out.println("Filename " + fileName);
	    // Create a new document
	    PdfDocument pdfDoc = null;
	    try {
			pdfDoc = new PdfDocument(new PdfWriter(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Document doc = new Document(pdfDoc);
	    float wd = 1000f; //width of tables in the document maximized
	    // Create a table to hold the data
	    Table table = new Table(tableModel.getColumnCount());
	    table.setHorizontalAlignment(HorizontalAlignment.CENTER);
	    // Add column headers
	    for (int col = 0; col < tableModel.getColumnCount(); col++) {
	        Cell cell = new Cell().add(new Paragraph(tableModel.getColumnName(col)));
	        cell.setMaxWidth(wd);
	        cell.setTextAlignment(TextAlignment.CENTER);
	        table.addHeaderCell(cell);
	        System.out.println("Column Headers + " + tableModel.getColumnName(col));
	    }
	    // Add data
	    for (int row = 0; row < tableModel.getRowCount(); row++) {
	        // Add row data
	        for (int col = 0; col < tableModel.getColumnCount(); col++) {
	            table.addCell(new Cell().add(new Paragraph(tableModel.getValueAt(row, col).toString())));
	        }
	    }
	    // Add the table to the document
	    doc.add(table);
	    // Close the document
	    doc.close();
	}

	public void printTable(IntervalGroupedTableModel tableModel, String fileName) {
	    System.out.println("Filename " + fileName);

	    // Create a new document
	    PdfDocument pdfDoc = null;
	    try {
	        pdfDoc = new PdfDocument(new PdfWriter(fileName));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    String ctext = "Group ";
	    Document doc = new Document(pdfDoc);
	    float wd = 1000f; //width of tables in the document maximized

	    // Add a new page for each group
	    for (int group = 1; group <= tableModel.groupCount; group++) {
	        // Add a new page for the current group
	        pdfDoc.addNewPage();

	        //create a title introductory specified by the user
	        //if none go back to default value
	        Paragraph ctextp = new Paragraph(ctext + group);
	        ctextp.setHorizontalAlignment(HorizontalAlignment.LEFT);
	        doc.add(ctextp);

	        // Create a table to hold the data for the current group
	        Table table = new Table(tableModel.getColumnCount() - 1);
	        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

	        // Add column headers (skip the first column)
	        for (int col = 1; col < tableModel.getColumnCount(); col++) {
	            Cell cell = new Cell().add(new Paragraph(tableModel.getColumnName(col)));
	            cell.setMaxWidth(wd);
	            cell.setTextAlignment(TextAlignment.CENTER);
	            table.addHeaderCell(cell);
	            System.out.println("Column Headers + " + tableModel.getColumnName(col));
	        }

	        System.out.println();

	        // Add data for the current group
	        boolean newPageAdded = false;
	        for (int row = 0; row < tableModel.getRowCount(); row++) {
	            System.out.println(" Row bool " + tableModel.getValueAt(row, 0).equals(group));

	            if (tableModel.getValueAt(row, 0).equals(group)) {
	                System.out.println(" Printing group " + tableModel.getValueAt(row, 0));

	                // Check if we need to add a new page for overflow
	                if (table.getNumberOfRows() >= 40 && !newPageAdded) {
	                    doc.add(table);
	                    pdfDoc.addNewPage();
	                    table = new Table(tableModel.getColumnCount() - 1);
	                    newPageAdded = true;
	                }

	                // Add row data (skip the first column)
	                for (int col = 1; col < tableModel.getColumnCount(); col++) {
	                    table.addCell(new Cell().add(new Paragraph(tableModel.getValueAt(row, col).toString())));
	                }
	            }
	        }

	        // Add the table to the document
	        doc.add(table);
	        if (group < tableModel.groupCount) {
	            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	        }
	    }

	    // Close the document
	    doc.close();
	}
}