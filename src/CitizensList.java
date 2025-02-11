package src;

import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import src.UI.*;
import src.database.*;
import src.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;

public class CitizensList extends JPanel implements ActionListener {
	private JPanel panelMain;

	//database
	private DbConn _conn;

	private TableColumnAppearance thandler;
	private DefaultTableModel model;
	private JTable tableGrid; //table hodling data
	private String ded;
	private WindowUtils util;
	private TableDataHandler tdh;

	private JPanel panelSearch;
	private JTextField textSearch;
	private JPanel panelDataGrid;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JPanel panelOptions;
	private JCheckBox bxDeceased;
	private JCheckBox bxFullName;
	private TableColumnAdjuster tca;
	private JPanel panelShow;
	private JLabel lblShow;
	private JCheckBox bxPurok;
	private JCheckBox bxBday;
	private JCheckBox bxAge;
	private JCheckBox bxSex;
	private JCheckBox bxFirstName;
	private JCheckBox bxMidName;
	private JCheckBox bxMidInt;
	private JCheckBox bxLastName;
	private JPanel panelButtons;
	private JButton btnDelete;
	private JSpinner spinMinAge;
	private JSpinner spinMaxAge;
	private JLabel lblMinAge;
	private JLabel lblMaxAge;
	private JRadioButton rbtnDeceasedYes;
	private JRadioButton rbtnDeceasedNo;
	private JPanel panelAge;
	private JPanel panelSex;
	private JCheckBox cboxMale;
	private JCheckBox cboxFemale;
	private JPanel panelDed;
	private JPanel panelPurok;
	private JLabel lblMinAge_1;
	private JLabel lblMaxAge_1;
	private ButtonGroup btnDeceased;
	private JSpinner spinMinPurok;
	private JSpinner spinMaxPurok;
	private JPanel panel;
	private JCheckBox bxUID;
	private JLabel lblSex;
	private JLabel lblSex_1;
	private JLabel lblDeceased;
	private JLabel lblPurok;
	private JLabel lblFilter;

	public CitizensList() {
		setBackground(new Color(240, 240, 240));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700); //size of the panel
		setLayout(new BorderLayout(0, 0));
		
		panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(null);
		add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new MigLayout("", "[][][][][][grow,center][][][][][][leading]", "[][44.00,center][][220.00,fill][][grow][]"));
		ded = "No";
		util = new WindowUtils();
		tdh = new TableDataHandler();
		
		panelSearch = new JPanel();
		panelSearch.setBackground(new Color(255, 140, 0));
		panelSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelSearch, "cell 5 1,alignx center,growy");
		panelSearch.setLayout(new MigLayout("", "[416.00px,grow,fill][68.00,right]", "[36.00px]"));
	
		//removedCols = new HashMap<String, TableColumn>();
		textSearch = new JTextField();
		textSearch.setFont(new Font("Arial", Font.PLAIN, 15));
		panelSearch.add(textSearch, "cell 0 0,grow");
		textSearch.setColumns(10);
		textSearch.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent e) {
					//filterTable();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					filterTable();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					//noot needed for plain texts
				}
				private void filterTable() {
			        String searchText = textSearch.getText().trim();
			        if (searchText.isEmpty()) {
			            // Reset the table to default
			            thandler.resetTable();
			        } else {
			            thandler.searchTable(searchText);
			        }
			    }
			}
		);
		
		btnSearch = new JButton("Search");
		btnSearch.setBackground(new Color(255, 255, 255));
		btnSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSearch.setFont(new Font("Arial", Font.BOLD, 15));
		btnSearch.setFocusPainted(false);
		btnSearch.addActionListener(this);
		panelSearch.add(btnSearch, "cell 1 0,grow");
		
		panelOptions = new JPanel();
		panelOptions.setBackground(new Color(255, 140, 0));
		panelMain.add(panelOptions, "cell 0 3 12 1,alignx center,growy");
		panelOptions.setLayout(new MigLayout("", "[564.00][223.00,grow]", "[179.00,grow]"));
		
		panelShow = new JPanel();
		panelShow.setBackground(new Color(255, 255, 255));
		panelOptions.add(panelShow, "cell 0 0,grow");
		panelShow.setLayout(new MigLayout("", "[113.00,left][80.00,leading][60.00][142.00][grow,right]", "[][][40.00][][87.00,grow]"));
		
		//start of checkboxes in options(filter)
		lblShow = new JLabel("Show");
		lblShow.setFont(new Font("Arial", Font.BOLD, 20));
		panelShow.add(lblShow, "cell 0 0");
		
		bxFullName = new JCheckBox("Full Name");
		bxFullName.setBackground(new Color(255, 255, 255));
		bxFullName.setSelected(true);
		bxFullName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxFullName.addActionListener(this);
		panelShow.add(bxFullName, "cell 0 1,grow");
		bxFullName.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		bxFirstName = new JCheckBox("First Name");
		bxFirstName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxFirstName.setBackground(new Color(255, 255, 255));
		bxFirstName.addActionListener(this);
		panelShow.add(bxFirstName, "cell 1 1,grow");
		
		bxMidName = new JCheckBox("Middle Name");
		bxMidName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxMidName.setBackground(new Color(255, 255, 255));
		bxMidName.addActionListener(this);
		panelShow.add(bxMidName, "cell 2 1,grow");
		
		bxMidInt = new JCheckBox("Middle Initial");
		bxMidInt.setFont(new Font("Arial", Font.PLAIN, 15));
		bxMidInt.setBackground(new Color(255, 255, 255));
		bxMidInt.addActionListener(this);
		panelShow.add(bxMidInt, "cell 3 1,grow");
		
		bxLastName = new JCheckBox("Last Name");
		bxLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxLastName.setBackground(new Color(255, 255, 255));
		bxLastName.addActionListener(this);
		panelShow.add(bxLastName, "flowx,cell 4 1,grow");
		
		bxAge = new JCheckBox("Age");
		bxAge.setBackground(new Color(255, 255, 255));
		panelShow.add(bxAge, "flowx,cell 0 2,growx");
		bxAge.setSelected(true);
		bxAge.setFont(new Font("Arial", Font.PLAIN, 15));
		bxAge.addActionListener(this);
		
		bxSex = new JCheckBox("Sex");
		bxSex.setBackground(new Color(255, 255, 255));
		panelShow.add(bxSex, "cell 1 2,growx");
		bxSex.setSelected(true);
		bxSex.setFont(new Font("Arial", Font.PLAIN, 15));
		
				bxSex.addActionListener(this);
		
		bxDeceased = new JCheckBox("Deceased");
		panelShow.add(bxDeceased, "cell 2 2");
		
				bxDeceased.setFont(new Font("Arial", Font.PLAIN, 15));
				bxDeceased.setBackground(new Color(255, 255, 255));
				bxDeceased.addActionListener(this);
		
		bxPurok = new JCheckBox("Purok");
		panelShow.add(bxPurok, "flowx,cell 3 2");
		bxPurok.setBackground(new Color(255, 255, 255));
		bxPurok.setSelected(true);
		bxPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		bxPurok.addActionListener(this);
		
		lblFilter = new JLabel("Filter");
		lblFilter.setFont(new Font("Arial", Font.BOLD, 20));
		panelShow.add(lblFilter, "cell 0 3,alignx left,aligny center");
		
		panelAge = new JPanel();
		panelAge.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelAge.setBackground(new Color(255, 255, 255));
		panelShow.add(panelAge, "cell 0 4,alignx left,growy");
		panelAge.setLayout(new MigLayout("", "[32.00,left][28.00,leading]", "[][29.00][33.00]"));
		
		lblSex = new JLabel("Age");
		lblSex.setFont(new Font("Arial", Font.BOLD, 17));
		panelAge.add(lblSex, "cell 0 0");
		
		lblMinAge = new JLabel("Minimum");
		panelAge.add(lblMinAge, "cell 0 1,alignx left");
		lblMinAge.setFont(new Font("Arial", Font.PLAIN, 15));
		
		spinMinAge = new JSpinner();
		panelAge.add(spinMinAge, "cell 1 1,alignx center,aligny center");
		spinMinAge.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMinAge.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMaxAge = new JLabel("Maximum");
		lblMaxAge.setFont(new Font("Arial", Font.PLAIN, 15));
		panelAge.add(lblMaxAge, "cell 0 2,alignx left");
		
		spinMaxAge = new JSpinner();
		panelAge.add(spinMaxAge, "cell 1 2,alignx center,aligny center");
		spinMaxAge.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinMaxAge.setFont(new Font("Arial", Font.PLAIN, 15));
;
	
		panelSex = new JPanel();
		panelSex.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSex.setBackground(new Color(255, 255, 255));
		panelShow.add(panelSex, "cell 1 4,growx,aligny center");
		panelSex.setLayout(new MigLayout("", "[96.00px]", "[23px][][]"));
		
		lblSex_1 = new JLabel("Sex");
		lblSex_1.setFont(new Font("Arial", Font.BOLD, 17));
		panelSex.add(lblSex_1, "cell 0 0");
		
		cboxMale = new JCheckBox("Male");
		cboxMale.setBackground(new Color(255, 255, 255));
		cboxMale.setSelected(true);
		cboxMale.setFont(new Font("Arial", Font.PLAIN, 15));
		panelSex.add(cboxMale, "cell 0 1,growx,aligny center");
		
		cboxFemale = new JCheckBox("Female");
		cboxFemale.setBackground(new Color(255, 255, 255));
		cboxFemale.setSelected(true);
		cboxFemale.setFont(new Font("Arial", Font.PLAIN, 15));
		panelSex.add(cboxFemale, "cell 0 2,growx,aligny center");
		
		//adding listener to the checkboxes 
		cboxMale.addItemListener(e -> sexFilter());
		cboxFemale.addItemListener(e -> sexFilter());	
		
		panelDed = new JPanel();
		panelDed.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDed.setBackground(new Color(255, 255, 255));
		panelShow.add(panelDed, "cell 2 4,alignx center,growy");
		panelDed.setLayout(new MigLayout("", "[]", "[][][]"));
		btnDeceased = new ButtonGroup();
		rbtnDeceasedYes = new JRadioButton("Yes");
//		rbtnDeceasedYes.setEnabled(false);
		rbtnDeceasedYes.setBackground(new Color(255, 255, 255));
		rbtnDeceasedYes.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDeceasedNo = new JRadioButton("No");
//		rbtnDeceasedNo.setEnabled(false);
		rbtnDeceasedNo.setSelected(true);
		rbtnDeceasedNo.setBackground(new Color(255, 255, 255));
		rbtnDeceasedNo.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDeceasedYes.addActionListener(e -> deadFilter());
		rbtnDeceasedNo.addActionListener(e -> deadFilter());

		btnDeceased.add(rbtnDeceasedYes);
		btnDeceased.add(rbtnDeceasedNo);
		
		lblDeceased = new JLabel("Deceased");
		lblDeceased.setFont(new Font("Arial", Font.BOLD, 17));
		panelDed.add(lblDeceased, "cell 0 0");

		panelDed.add(rbtnDeceasedYes, "flowx,cell 0 1,alignx left,aligny baseline");
		panelDed.add(rbtnDeceasedNo, "cell 0 2,alignx left,aligny baseline");
		//end of radio button Deceased
		
		panelPurok = new JPanel();
		panelPurok.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPurok.setBackground(new Color(255, 255, 255));
		panelShow.add(panelPurok, "cell 3 4,grow");
		panelPurok.setLayout(new MigLayout("", "[33.00,left][107.00]", "[][36.00,center][35.00]"));
		
		lblPurok = new JLabel("Purok");
		lblPurok.setFont(new Font("Arial", Font.BOLD, 17));
		panelPurok.add(lblPurok, "cell 0 0");
		
		lblMinAge_1 = new JLabel("Minimum");
		lblMinAge_1.setFont(new Font("Arial", Font.PLAIN, 15));
		panelPurok.add(lblMinAge_1, "cell 0 1,alignx left,aligny center");
		
		spinMinPurok = new JSpinner();
		spinMinPurok.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMinPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		panelPurok.add(spinMinPurok, "cell 1 1,growx");
		
		lblMaxAge_1 = new JLabel("Maximum");
		lblMaxAge_1.setFont(new Font("Arial", Font.PLAIN, 15));
		panelPurok.add(lblMaxAge_1, "cell 0 2,alignx left,aligny center");
		
		spinMaxPurok = new JSpinner();
		spinMaxPurok.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinMaxPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		panelPurok.add(spinMaxPurok, "cell 1 2,growx");
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(null);
		panelShow.add(panel, "cell 4 2,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		bxUID = new JCheckBox("ID");
		bxUID.setBackground(new Color(255, 255, 255));
		bxUID.setSelected(true);
		panel.add(bxUID);
		bxUID.setFont(new Font("Arial", Font.PLAIN, 15));
		bxUID.addActionListener(this);
		
		bxBday = new JCheckBox("Birthday");
		bxBday.setBackground(new Color(255, 255, 255));
		panelShow.add(bxBday, "cell 3 2");
		bxBday.setBorder(null);
		bxBday.setFont(new Font("Arial", Font.PLAIN, 15));
		bxBday.addActionListener(this);
		
		panelButtons = new JPanel();
		panelButtons.setBackground(new Color(255, 255, 255));
		panelOptions.add(panelButtons, "cell 1 0,grow");
		panelButtons.setLayout(new MigLayout("", "[248.00,fill]", "[149.00,grow]"));
		
		btnDelete = new JButton("Delete");
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setBackground(new Color(178, 34, 34));
		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelButtons.add(btnDelete, "cell 0 0,grow");
		btnDelete.setFont(new Font("Arial", Font.BOLD, 15));
		btnDelete.setFocusPainted(false);
		btnDelete.addActionListener(this);
		//end of checkboxes

		panelDataGrid = new JPanel(new BorderLayout());
		panelDataGrid.setBackground(new Color(255, 165, 0));
		panelDataGrid.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelDataGrid, "cell 0 5 12 2,grow");
		
		_conn = new DbConn();
		_conn.oopen();
		_conn.data_fetch();

		model = _conn.getModel();

		tableGrid = new JTable(model);
		tableGrid.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableGrid.setBackground(new Color(255, 255, 255));
		tableGrid.setFillsViewportHeight(true);
		tableGrid.setFont(new Font("Arial", Font.PLAIN, 15));
		thandler = new TableColumnAppearance(tableGrid);

		//default shown columns (Name, Age, Sex, Purok) 
		//hide the remaining columns 
		thandler.hideColumn("First Name");
		thandler.hideColumn("Middle Name");
		thandler.hideColumn("Middle Initial");
		thandler.hideColumn("Last Name");
		thandler.hideColumn("Deceased");
		thandler.hideColumn("Birthday");

		_conn.cclose();

		// Set the table's auto resize mode
		tableGrid.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tca = new TableColumnAdjuster(tableGrid);
		tca.adjustColumns();
		
		
		/*********** adding listener to age and purok **/

		spinMinAge.addChangeListener(e -> ageFilter());
		spinMaxAge.addChangeListener(e -> ageFilter());
		spinMinPurok.addChangeListener(e -> purokFilter());
		spinMaxPurok.addChangeListener(e -> purokFilter()); 

		/*********** end of adding listener to age and purok **/

		// Wrap the text within each cell
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.LEFT);
		cellRenderer.setVerticalAlignment(JLabel.TOP);
		tableGrid.setDefaultRenderer(String.class, cellRenderer);
		tableGrid.setRowHeight(tableGrid.getRowHeight() + 5);
		
		scrollPane = new JScrollPane(tableGrid);
		scrollPane.setForeground(new Color(0, 0, 0));
		scrollPane.setBackground(new Color(0, 0, 0));
		thandler.printTable();

		panelDataGrid.add(scrollPane, BorderLayout.CENTER);
		util.removeFocusPainted(panelShow);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSearch)
		{
			if(textSearch.getText().isEmpty()) thandler.resetTable();
			else thandler.searchTable(textSearch.getText());
		}
	
		
		else if(e.getSource() ==  btnDelete)
		{
			int[] selectedRows = tableGrid.getSelectedRows();
			System.out.println("It didn't removie the row!!" + selectedRows.length);
	        if (selectedRows.length > 0) {
	            DefaultTableModel model = (DefaultTableModel) tableGrid.getModel();
	            for (int i = selectedRows.length - 1; i >= 0; i--) {
	                int row = selectedRows[i];
	                int uid = (int) model.getValueAt(row, 0);
	                _conn.oopen();
	                _conn.setDeleted(uid);
	                _conn.cclose();
	                model.removeRow(row);
	            }
	        }
		}
		

		else if(e.getSource() == bxSex)		
		{
			boolean bstate = bxSex.isSelected();
			boolean sexstate = cboxMale.isSelected() || cboxFemale.isSelected();
			if(sexstate)
			{
				cboxMale.setEnabled(bstate);
				cboxFemale.setEnabled(bstate);
				tboxShow(bxSex, "Sex");
			}
		}
		else if(e.getSource() == bxAge) tboxShow(bxAge, "Age");
		else if(e.getSource() == bxFullName) tboxShow(bxFullName, "Name");
		else if(e.getSource() == bxFirstName)tboxShow(bxFirstName, "First Name");
		else if(e.getSource() == bxDeceased) tboxShow(bxDeceased, "Deceased");
		else if(e.getSource() == bxUID) tboxShow(bxUID, "UID");
		else if(e.getSource() == bxMidName) tboxShow(bxMidName, "Middle Name");
		else if(e.getSource() == bxMidInt) tboxShow(bxMidInt, "Middle Initial");
		else if(e.getSource() == bxLastName) tboxShow(bxLastName, "Last Name");
		else if(e.getSource() == bxAge) tboxShow(bxAge, "Age");
		else if(e.getSource() == bxPurok) tboxShow(bxPurok, "Purok");
		else if(e.getSource() == bxBday) tboxShow(bxBday, "Birthday");
	}
	
	private void sexFilter()
	{
			boolean malebox = cboxMale.isSelected();
			boolean fembox = cboxFemale.isSelected();

			System.out.println("sex filter");
			if(malebox && !fembox)
			{
				System.out.println("flter male");
				thandler.filterSex(cboxMale.getText());
			}
			else if(!malebox && fembox)
			{
				System.out.println("flter female");
				thandler.filterSex(cboxFemale.getText());
			}
			else if (malebox && fembox)
			{
				System.out.println("flter both");
				thandler.filterSex("Both");
			}
	}
	
	private void deadFilter()
	{
			if(rbtnDeceasedYes.isSelected())  
				ded = "Yes";
			else ded = "No";

			System.out.println("dec filter" + ded);
			thandler.filterDeceased(ded);
	} 
	private void purokFilter()
	{
		 int min = (int) spinMinPurok.getValue();
         int max = (int) spinMaxPurok.getValue();
		 thandler.filterPurok(min, max);
	}
	private void ageFilter()
	{
         int min = (int) spinMinAge.getValue();
         int max = (int) spinMaxAge.getValue();
		 thandler.filterAge(min, max);
	}
	private void tboxShow(JCheckBox box, String id)
	{
		if(box.isSelected())
			thandler.showColumn(id);
		else
			thandler.hideColumn(id);;
	} 
	public void updateTable()
	{
		//will update the table and hide the non-default shown columns 
		tdh.updateTable(tableGrid, thandler);
	}
	
	public void resetFields()
	{
		tdh.resetPanelCbox(panelShow);
		spinMinAge.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMaxAge.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinMinPurok.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMaxPurok.setModel(new SpinnerNumberModel(100, 1, 200, 1));

		rbtnDeceasedNo.setSelected(true);
		bxFullName.setSelected(true);
		bxAge.setSelected(true);
		bxSex.setSelected(true);
		bxUID.setSelected(true);
		bxPurok.setSelected(true);
		cboxMale.setSelected(true);
		cboxFemale.setSelected(true);
	}
	
	public static void main(String[] args) 
	{
		new CitizensList();
	}
}
