package src;

import javax.swing.border.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;
import src.UI.IntervalGroupedTableModel;
import src.UI.TableColumnAdjuster;
import src.UI.TableColumnAppearance;

public class TablePreview extends JPanel {
	private JPanel panelMain;

	private JTable tableGrid; //table hodling data
	private JPanel panelDataGrid;
	private JScrollPane scrollPane;
	private TableColumnAdjuster tca;
	private JLabel labelPreview;
	private IntervalGroupedTableModel igtm;
	private DefaultTableModel dtm;

	public TablePreview(DefaultTableModel dtm)
	{
		this.dtm = dtm;
		tableGrid = new JTable(this.dtm);
		tableModelUI();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public TablePreview(IntervalGroupedTableModel igtm) {
		this.igtm = igtm;
		tableGrid = new JTable(this.igtm);
		tableModelUI();
	}
	public void tableModelUI()
	{
		setBackground(new Color(240, 240, 240));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700); //size of the panel
		setLayout(new BorderLayout(0, 0));
		
		panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(null);
		add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new MigLayout("", "[][][][][][][][][][570.00,grow][]", "[][169.00,grow]"));
	
		labelPreview = new JLabel("Table Preview");
		labelPreview.setFont(new Font("Arial", Font.BOLD, 20));
		panelMain.add(labelPreview, "cell 1 0");
		//end of checkboxes

		panelDataGrid = new JPanel(new BorderLayout());
		panelDataGrid.setBackground(new Color(255, 165, 0));
		panelDataGrid.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelDataGrid, "cell 1 1 9 1,grow");

		tableGrid.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableGrid.setBackground(new Color(255, 255, 255));
		tableGrid.setFillsViewportHeight(true);
		tableGrid.setFont(new Font("Arial", Font.PLAIN, 12));
		tableGrid.getTableHeader().setReorderingAllowed(true);//set reorderin of the table columns allowed

		tableGrid.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tca = new TableColumnAdjuster(tableGrid);
		tca.adjustColumns();

		// Wrap the text within each cell
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.LEFT);
		cellRenderer.setVerticalAlignment(JLabel.TOP);
		tableGrid.setDefaultRenderer(String.class, cellRenderer);
		tableGrid.setRowHeight(tableGrid.getRowHeight() + 5);
		
		scrollPane = new JScrollPane(tableGrid);
		scrollPane.setForeground(new Color(0, 0, 0));
		scrollPane.setBackground(new Color(0, 0, 0));
		panelDataGrid.add(scrollPane, BorderLayout.CENTER);
		setVisible(true);	
	}
}
