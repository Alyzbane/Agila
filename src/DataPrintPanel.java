package src;

import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import src.utils.*;
import src.UI.*;
import src.database.*;
import net.miginfocom.swing.MigLayout;
import static src.utils.WindowUtils.switchPanel;

public class DataPrintPanel extends JPanel implements ActionListener
{
	private JPanel panelMain;

	// database
	private DbConn _conn;
	private IntervalGroupedTableModel previewModel;
	private TableDataHandler tdh;
	private WindowUtils utils;

	private int gcount;

	private TableColumnAppearance thandler;
	private DefaultTableModel model;
	private String ded;
	private JPanel panelCard;
	private JCheckBox bxDeceased;
	private JCheckBox bxFullName;
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
	private JLabel lblGroupCount;
	private JPanel panelGroupCount;
	private JSpinner spinGroupCount;
	private JPanel panelFilter;
	private JPanel panelSort;
	private JLabel lblSort;
	private JPanel panelTitle;
	private JLabel lblPrint;
	private JLabel lblErrorStatus;
	private JRadioButton rbtnContinous;
	private JRadioButton rbtnRandom;
	private ButtonGroup btnSort;
	private JTable tableGrid;
	private TableColumnAdjuster tca;
	private JPanel panelExport;
	private JCheckBox bxUID;
	private JLabel lblLocation;
	private JPanel panelGlobal;
	private JButton btnReturn;
	private JButton btnPreview;
	private JButton btnExport;
	private JPanel panelFileLocation;
	private JTextField txtFileLocation;
	private JButton btnSaveTo;
	private JCheckBox bxGroupNumber;
	private JLabel lblFilter;
	private JLabel lblAge;
	private JLabel lblSex;
	private JLabel lblDeceased;
	private JLabel lblPurok;
	private JRadioButton rbtnNormal;

	public DataPrintPanel()
	{
		setBackground(new Color(240, 240, 240));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700); // size of the panel
		setLayout(new BorderLayout(0, 0));

		utils = new WindowUtils();
		panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(null);
		add(panelMain, BorderLayout.CENTER);
		ded = "No";
		tdh = new TableDataHandler();
		panelMain.setLayout(new MigLayout("", "[595px,grow,center]", "[529px,grow][48.00,grow,bottom]"));

		panelCard = new JPanel();
		panelCard.setBackground(new Color(255, 255, 255));
		panelMain.add(panelCard, "cell 0 0,growx,aligny top");
		panelCard.setLayout(new CardLayout(0, 0));

		panelShow = new JPanel();
		panelShow.setBackground(new Color(255, 255, 255));
		panelCard.add(panelShow, "default");
		panelShow.setLayout(new MigLayout("", "[113.00,grow,left]", "[34.00,baseline][72.00][191.00][118.00][95.00]"));

		panelTitle = new JPanel();
		panelTitle.setBackground(new Color(255, 255, 255));
		panelShow.add(panelTitle, "cell 0 0,grow");
		panelTitle.setLayout(new MigLayout("", "[][grow]", "[60.00]"));

		lblPrint = new JLabel("Print Options    ");
		lblPrint.setFont(new Font("Arial", Font.BOLD, 25));
		panelTitle.add(lblPrint, "cell 0 0,growx,aligny center");

		lblErrorStatus = new JLabel("");
		lblErrorStatus.setFont(new Font("Arial", Font.BOLD, 19));
		panelTitle.add(lblErrorStatus, "cell 1 0,grow");

		panelGroupCount = new JPanel();
		panelShow.add(panelGroupCount, "cell 0 1,growx,aligny center");
		panelGroupCount.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelGroupCount.setBackground(Color.WHITE);
		panelGroupCount.setLayout(new MigLayout("", "[][146.00,grow]", "[45.00]"));

		lblGroupCount = new JLabel("Group Count");
		panelGroupCount.add(lblGroupCount, "cell 0 0");
		lblGroupCount.setFont(new Font("Arial", Font.BOLD, 17));

		spinGroupCount = new JSpinner();
		spinGroupCount.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		spinGroupCount.setFont(new Font("Arial", Font.PLAIN, 15));
		panelGroupCount.add(spinGroupCount, "cell 1 0,grow");

		btnDeceased = new ButtonGroup();

		panelFilter = new JPanel();
		panelFilter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFilter.setBackground(new Color(255, 255, 255));
		panelShow.add(panelFilter, "cell 0 2,growx,aligny center");
		panelFilter.setLayout(new MigLayout("", "[][][][][][][grow]", "[][][][][grow,fill]"));

		// start of checkboxes in options(filter)
		lblShow = new JLabel("Show & Filter");
		panelFilter.add(lblShow, "cell 0 0");
		lblShow.setFont(new Font("Arial", Font.BOLD, 17));

		bxUID = new JCheckBox("UID");
		bxUID.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxUID, "flowx,cell 0 1,growx");
		bxUID.setSelected(true);
		bxUID.setFont(new Font("Arial", Font.PLAIN, 15));
		bxUID.addActionListener(this);

		bxFullName = new JCheckBox("Full Name");
		bxFullName.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxFullName, "cell 1 1,growx");
		bxFullName.setSelected(true);
		bxFullName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxFullName.addActionListener(this);
		bxFullName.setHorizontalTextPosition(SwingConstants.RIGHT);

		bxFirstName = new JCheckBox("First Name");
		bxFirstName.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxFirstName, "cell 2 1");
		bxFirstName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxFirstName.addActionListener(this);

		bxMidName = new JCheckBox("Middle Name");
		bxMidName.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxMidName, "flowx,cell 3 1,growx");
		bxMidName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxMidName.addActionListener(this);

		bxLastName = new JCheckBox("Last Name");
		bxLastName.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxLastName, "cell 4 1");
		bxLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		bxLastName.addActionListener(this);

		bxPurok = new JCheckBox("Purok");
		panelFilter.add(bxPurok, "cell 5 1,growx");
		bxPurok.setBackground(new Color(255, 255, 255));
		bxPurok.setSelected(true);
		bxPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		bxPurok.addActionListener(this);

		bxSex = new JCheckBox("Sex");
		bxSex.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxSex, "cell 1 2,growx");
		bxSex.setSelected(true);
		bxSex.setFont(new Font("Arial", Font.PLAIN, 15));

		bxSex.addActionListener(this);

		bxDeceased = new JCheckBox("Deceased");
		panelFilter.add(bxDeceased, "cell 2 2,growx");

		bxDeceased.setFont(new Font("Arial", Font.PLAIN, 15));
		bxDeceased.setBackground(new Color(255, 255, 255));
		bxDeceased.addActionListener(this);

		bxMidInt = new JCheckBox("Middle Initial");
		bxMidInt.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxMidInt, "cell 3 2,growx,aligny center");
		bxMidInt.setFont(new Font("Arial", Font.PLAIN, 15));
		bxMidInt.addActionListener(this);
		bxMidInt.addActionListener(this);

		bxBday = new JCheckBox("Birthday");
		bxBday.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxBday, "cell 4 2,growx");
		bxBday.setFont(new Font("Arial", Font.PLAIN, 15));

		bxBday.addActionListener(this);

		bxGroupNumber = new JCheckBox("Group Number");
		bxGroupNumber.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxGroupNumber, "cell 5 2");
		bxGroupNumber.setFont(new Font("Arial", Font.PLAIN, 15));

		lblFilter = new JLabel("Filter");
		lblFilter.setFont(new Font("Arial", Font.BOLD, 17));
		panelFilter.add(lblFilter, "cell 0 3");

		panelAge = new JPanel();
		panelFilter.add(panelAge, "cell 0 4,growx");
		panelAge.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelAge.setBackground(new Color(255, 255, 255));
		panelAge.setLayout(new MigLayout("", "[32.00,left][28.00,grow,leading]", "[][29.00][33.00]"));

		lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 17));
		panelAge.add(lblAge, "cell 0 0");

		lblMinAge = new JLabel("Minimum");
		panelAge.add(lblMinAge, "cell 0 1,alignx left");
		lblMinAge.setFont(new Font("Arial", Font.PLAIN, 15));

		spinMinAge = new JSpinner();
		panelAge.add(spinMinAge, "cell 1 1,growx,aligny center");
		spinMinAge.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMinAge.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMaxAge = new JLabel("Maximum");
		lblMaxAge.setFont(new Font("Arial", Font.PLAIN, 15));
		panelAge.add(lblMaxAge, "cell 0 2,alignx left");

		spinMaxAge = new JSpinner();
		panelAge.add(spinMaxAge, "cell 1 2,growx,aligny center");
		spinMaxAge.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinMaxAge.setFont(new Font("Arial", Font.PLAIN, 15));
		spinMinAge.addChangeListener(e -> ageFilter());
		spinMaxAge.addChangeListener(e -> ageFilter());

		panelSex = new JPanel();
		panelFilter.add(panelSex, "cell 1 4");
		panelSex.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSex.setBackground(new Color(255, 255, 255));
		panelSex.setLayout(new MigLayout("", "[45px]", "[23px][][]"));

		lblSex = new JLabel("Sex");
		lblSex.setFont(new Font("Arial", Font.PLAIN, 17));
		panelSex.add(lblSex, "cell 0 0");

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

		// adding listener to the checkboxes
		cboxMale.addItemListener(e -> sexFilter());
		cboxFemale.addItemListener(e -> sexFilter());

		panelDed = new JPanel();
		panelFilter.add(panelDed, "cell 2 4");
		panelDed.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDed.setBackground(new Color(255, 255, 255));
		panelDed.setLayout(new MigLayout("", "[]", "[][][]"));
		rbtnDeceasedYes = new JRadioButton("Yes");
		rbtnDeceasedYes.setActionCommand("Yes");
		// rbtnDeceasedYes.setEnabled(false);
		rbtnDeceasedYes.setBackground(new Color(255, 255, 255));
		rbtnDeceasedYes.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDeceasedNo = new JRadioButton("No");
		rbtnDeceasedYes.setActionCommand("No");
		// rbtnDeceasedNo.setEnabled(false);
		rbtnDeceasedNo.setSelected(true);
		rbtnDeceasedNo.setBackground(new Color(255, 255, 255));
		rbtnDeceasedNo.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDeceasedYes.addActionListener(e -> deadFilter());
		rbtnDeceasedNo.addActionListener(e -> deadFilter());

		btnDeceased.add(rbtnDeceasedYes);
		btnDeceased.add(rbtnDeceasedNo);

		lblDeceased = new JLabel("Deceased");
		lblDeceased.setFont(new Font("Arial", Font.PLAIN, 17));
		panelDed.add(lblDeceased, "cell 0 0");

		panelDed.add(rbtnDeceasedYes, "flowx,cell 0 1,alignx left,aligny baseline");
		panelDed.add(rbtnDeceasedNo, "cell 0 2,alignx left,aligny baseline");
		// end of radio button Deceased

		panelPurok = new JPanel();
		panelFilter.add(panelPurok, "cell 3 4");
		panelPurok.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPurok.setBackground(new Color(255, 255, 255));
		panelPurok.setLayout(new MigLayout("", "[33.00,left][107.00]", "[][36.00,center][35.00]"));

		lblPurok = new JLabel("Purok");
		lblPurok.setFont(new Font("Arial", Font.PLAIN, 17));
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

		bxAge = new JCheckBox("Age");
		bxAge.setBackground(new Color(255, 255, 255));
		panelFilter.add(bxAge, "cell 0 2,growx");
		bxAge.setSelected(true);
		bxAge.setFont(new Font("Arial", Font.PLAIN, 15));
		bxAge.addActionListener(this);
		spinMinPurok.addChangeListener(e -> purokFilter());
		spinMaxPurok.addChangeListener(e -> purokFilter());

		panelSort = new JPanel();
		panelSort.setBackground(new Color(255, 255, 255));
		panelSort.setBorder(null);
		panelShow.add(panelSort, "cell 0 3,grow");
		panelSort.setLayout(new MigLayout("", "[][][][grow][][]", "[48.00,grow][35.00][31.00]"));

		lblSort = new JLabel("Sort Format");
		lblSort.setFont(new Font("Arial", Font.BOLD, 17));
		panelSort.add(lblSort, "cell 0 0");
		btnSort = new ButtonGroup();

		rbtnNormal = new JRadioButton("Normal");
		rbtnNormal.setBackground(new Color(255, 255, 255));
		rbtnNormal.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSort.add(rbtnNormal);
		panelSort.add(rbtnNormal, "cell 0 1,growx");

		rbtnContinous = new JRadioButton("Continous Group");
		rbtnContinous.setBackground(new Color(255, 255, 255));
		rbtnContinous.setFont(new Font("Arial", Font.PLAIN, 15));
		panelSort.add(rbtnContinous, "cell 1 1,growx,aligny center");
		btnSort.add(rbtnContinous);

		rbtnRandom = new JRadioButton("Random");
		rbtnRandom.setBackground(new Color(255, 255, 255));
		rbtnRandom.setFont(new Font("Arial", Font.PLAIN, 15));
		panelSort.add(rbtnRandom, "cell 2 1,growx,aligny center");
		btnSort.add(rbtnRandom);

		panelExport = new JPanel();
		panelExport.setBorder(null);
		panelExport.setBackground(new Color(255, 255, 255));
		panelShow.add(panelExport, "cell 0 4,grow");
		panelExport.setLayout(new MigLayout("", "[grow]", "[41.00,bottom][grow,baseline]"));

		lblLocation = new JLabel("File Location");
		lblLocation.setFont(new Font("Arial", Font.BOLD, 17));
		panelExport.add(lblLocation, "cell 0 0");

		panelFileLocation = new JPanel();
		panelFileLocation.setBorder(null);
		panelFileLocation.setBackground(new Color(255, 255, 255));
		panelExport.add(panelFileLocation, "cell 0 1,grow");
		panelFileLocation.setLayout(new MigLayout("", "[89px,grow][]", "[58.00px,grow]"));

		txtFileLocation = new JTextField();
		txtFileLocation.setFont(new Font("Arial", Font.PLAIN, 15));
		panelFileLocation.add(txtFileLocation, "cell 0 0,grow");
		txtFileLocation.setColumns(10);

		btnSaveTo = new JButton("Save File");
		btnSaveTo.setFont(new Font("Arial", Font.BOLD, 15));
		btnSaveTo.setBackground(new Color(255, 255, 255));
		btnSaveTo.setFocusPainted(false);
		btnSaveTo.addActionListener(this);
		panelFileLocation.add(btnSaveTo, "cell 1 0,alignx center,growy");

		panelGlobal = new JPanel();
		panelGlobal.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelGlobal.setBackground(new Color(255, 255, 255));
		panelMain.add(panelGlobal, "cell 0 1,grow");
		panelGlobal.setLayout(new MigLayout("", "[136.00px,left][546.00,grow][79.00px,grow,right][63px,grow,right]",
				"[43.00px,grow]"));

		btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Arial", Font.BOLD, 15));
		btnReturn.setBackground(new Color(255, 255, 255));
		btnReturn.setFocusPainted(false);
		btnReturn.addActionListener(this);
		panelGlobal.add(btnReturn, "cell 0 0,grow");

		btnPreview = new JButton("Preview");
		btnPreview.setForeground(new Color(255, 255, 255));
		btnPreview.setFont(new Font("Arial", Font.BOLD, 15));
		btnPreview.setBackground(new Color(30, 144, 255));
		btnPreview.addActionListener(this);
		btnPreview.setFocusPainted(false);
		panelGlobal.add(btnPreview, "cell 2 0,grow");

		btnExport = new JButton("Export");
		btnExport.setForeground(new Color(255, 255, 255));
		btnExport.setFont(new Font("Arial", Font.BOLD, 15));
		btnExport.setBackground(new Color(34, 139, 34));
		btnExport.setFocusPainted(false);
		btnExport.addActionListener(this);
		panelGlobal.add(btnExport, "cell 3 0,grow");

		gcount = 0;
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

		// default shown columns (Name, Age, Sex, Purok)
		// hide the remaining columns
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

		// Wrap the text within each cell
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.LEFT);
		cellRenderer.setVerticalAlignment(JLabel.TOP);
		utils.removeFocusPainted(panelShow);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnExport)
		{
			if (!tdh.verifyCheckboxes(panelFilter))
			{
				JOptionPane.showMessageDialog(null, "Select atleast one shown value in the checkboxes");
				return;
			}
			String filepath = utils.fpathName();
			if (filepath.isBlank())
			{
				JOptionPane.showMessageDialog(null, "File location not specified");
				return;
			}
			// creating the table
			if (!createTable())
				return;
			String fext = utils.fileExtension();
			System.out.println("File name " + fext);
			if (fext.equals("pdf"))
			{
				PDFPrinter printer = new PDFPrinter();
				// creating the pdf
				if (!rbtnNormal.isSelected())
					printer.printTable(previewModel, filepath);
				else
					printer.printTable(thandler.getFilteredTableModel(), filepath);
				JOptionPane.showMessageDialog(null, "File saved successfully at " + filepath);

			} else if (fext.equals("xlsx"))
			{
				ExcelPrinter exprinter = new ExcelPrinter();

				if (!rbtnNormal.isSelected())
					exprinter.exportToExcel(previewModel, filepath);
				else
					exprinter.exportToExcel(thandler.getFilteredTableModel(), filepath);
				JOptionPane.showMessageDialog(null, "File saved successfully at " + filepath);
			}
		} else if (e.getSource() == btnSaveTo)
		{
			if (utils.saveFile())
			{
				String filepath = utils.fpathName();
				txtFileLocation.setText(filepath);
			}
		}
		else if (e.getSource() == bxSex)
		{
			boolean bstate = bxSex.isSelected();
			if(cboxMale.isSelected() || cboxFemale.isSelected())
			{
				cboxMale.setEnabled(bstate);
				cboxFemale.setEnabled(bstate);
				tboxShow(bxSex, "Sex");
			}
		} 
		else if (e.getSource() == bxFullName)
			tboxShow(bxFullName, "Name");
		else if (e.getSource() == bxFirstName)
			tboxShow(bxFirstName, "First Name");
		else if (e.getSource() == btnReturn)
			switchPanel(panelCard, "default");
		else if (e.getSource() == bxDeceased)
			tboxShow(bxDeceased, "Deceased");
		else if (e.getSource() == bxMidName)
			tboxShow(bxMidName, "Middle Name");
		else if (e.getSource() == bxUID)
			tboxShow(bxUID, "UID");
		else if (e.getSource() == bxMidInt)
			tboxShow(bxMidInt, "Middle Initial");
		else if (e.getSource() == bxLastName)
			tboxShow(bxLastName, "Last Name");
		else if (e.getSource() == bxAge)
			tboxShow(bxAge, "Age");
		else if (e.getSource() == bxPurok)
			tboxShow(bxPurok, "Purok");
		else if (e.getSource() == bxBday)
			tboxShow(bxBday, "Birthday");
		else if (e.getSource() == btnPreview)
		{
			if (!tdh.verifyCheckboxes(panelFilter))
			{
				JOptionPane.showMessageDialog(null, "Select atleast one shown value in the checkboxes");
				return;
			}
			previewTable();
		}
	}

	private boolean createTable()
	{
		boolean so = rbtnContinous.isSelected() || rbtnRandom.isSelected() || rbtnNormal.isSelected();
		if (!so)
		{
			utils.errStatLabel(lblErrorStatus, true);
			return false;
		}

		utils.errStatLabel(lblErrorStatus, false);
		boolean act = rbtnContinous.isSelected() ? true : false;
		// use the filtered table model from thandler instance
		previewModel = new IntervalGroupedTableModel(thandler.getFilteredTableModel(), gcount, act);
		System.out.println("Created a table");
		return true;
	}

	public void previewTable()
	{
		TablePreview tpv = null;
		if (rbtnNormal.isSelected())
		{
			// create a unfiltered and not grouped columns
			tpv = new TablePreview(thandler.getFilteredTableModel());
		} else
		{
			gcount = (int) spinGroupCount.getValue();
			if (gcount == 0)
			{
				utils.errStatLabel(lblErrorStatus, true);
				return;
			}
			if (!createTable())
				return;
			utils.errStatLabel(lblErrorStatus, false);
			// create a new instance for every click of preview
			// to update the results of filtered table

			tpv = new TablePreview(previewModel);
		}
		panelCard.add(tpv, "table");
		switchPanel(panelCard, "table");
	}

	private void sexFilter()
	{
		if (!bxSex.isSelected())
			return;

		boolean malebox = cboxMale.isSelected();
		boolean fembox = cboxFemale.isSelected();

		System.out.println("sex filter");
		if (malebox && !fembox)
		{
			System.out.println("flter male");
			thandler.filterSex(cboxMale.getText());
		} else if (!malebox && fembox)
		{
			System.out.println("flter female");
			thandler.filterSex(cboxFemale.getText());
		} else if (malebox && fembox)
		{
			System.out.println("flter both");
			thandler.filterSex("Both");
		}
		// }
	}

	private void deadFilter()
	{
		// boolean dead = bxDeceased.isSelected();
		// if(dead)
		// {
		if (rbtnDeceasedYes.isSelected())
			ded = "Yes";
		else
			ded = "No";

		System.out.println("dec filter" + ded);
		thandler.filterDeceased(ded);
		// }
		// System.out.println("Doesnt work!!");
	}

	private void purokFilter()
	{
		// boolean purok = bxPurok.isSelected();
		// if(purok)
		// {
		int min = (int) spinMinPurok.getValue();
		int max = (int) spinMaxPurok.getValue();
		thandler.filterPurok(min, max);
		// }
	}

	private void ageFilter()
	{
		// boolean age = bxAge.isSelected();
		// if(age)
		// {
		int min = (int) spinMinAge.getValue();
		int max = (int) spinMaxAge.getValue();
		thandler.filterAge(min, max);
		// }
	}

	public void updateTable()
	{
		tdh.updateTable(tableGrid, thandler);
	}

	public void resetFields()
	{
		tdh.resetPanelCbox(panelShow);
		spinMinAge.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMaxAge.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinMinPurok.setModel(new SpinnerNumberModel(1, 1, 200, 1));
		spinMaxPurok.setModel(new SpinnerNumberModel(100, 1, 200, 1));
		spinGroupCount.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		txtFileLocation.setText("");

		rbtnDeceasedNo.setSelected(true);
		bxFullName.setSelected(true);
		bxAge.setSelected(true);
		bxSex.setSelected(true);
		bxUID.setSelected(true);
		bxPurok.setSelected(true);
		cboxMale.setSelected(true);
		cboxFemale.setSelected(true);
	}

	private void tboxShow(JCheckBox box, String id)
	{
		if (box.isSelected())
			thandler.showColumn(id);
		else
			thandler.hideColumn(id);
	}

	public static void main(String[] args)
	{
		new DataPrintPanel();
	}
}
