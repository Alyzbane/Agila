package src;

import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import src.UI.*;
import src.database.*;

import java.util.*;
import static src.utils.Oras.*;
import src.utils.*;

import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.*;

public class DataEditPanel extends JPanel implements ActionListener
{

	private int uid;

	private JPanel panelMain;
	protected JTextField txtFname;
	protected JTextField txtLname;
	protected JTextField txtMinitial;
	private JPanel panelCounter, panelInsertData, panelLogs, panelNameInfo, panelSex, panelAge, panelLogStatus;
	protected JTextArea txtPaneLogs;
	protected JLabel lblCounter;
	private JLabel lblTextCounter;
	private JButton btnClearLogs, btnReset, btnSubmit;
	private JLabel lblPersonalInformation_1, lblPersonalInformation, lblAge;
	protected JSpinner spinAge;
	protected ButtonGroup btnSex;
	protected ButtonGroup btnDeceased;
	private JRadioButton rdbtnFemale, rdbtnMale;
	private JLabel lblFname, lblLname, lblMiddleInitial, lblSex;
	protected TxtLogger logging;
	private int counter;
	private WindowUtils utils;

	// database
	private DbConn _conn;
	private JLabel lblErrorStatus;
	private JLabel lblPurok;
	private JLabel lblBirthDay;
	private JPanel panel;
	private JDateChooser birthChooser;
	private JPanel panelBtns;
	private JSpinner spinPurok;
	private JPanel panelDeceased;
	private JLabel lblDeceased;
	private JRadioButton rbtnDYes;
	private JRadioButton rbtnDNo;
	private JLabel lblNewLabel;
	private JPanel panelSearch;
	private JLabel lblSearch;
	private JComboBox<String> searchComboBox;

	public DataEditPanel()
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
		panelMain.setLayout(new MigLayout("", "[609.00px,grow,center][383px,center]", "[38px,center][666.00px]"));

		panelLogs = new JPanel();

		// side panel filled with logs
		panelLogs.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelLogs, "cell 1 1,grow");
		panelLogs.setLayout(new BorderLayout(0, 0));

		panelLogStatus = new JPanel();
		panelLogStatus.setBorder(null);
		panelLogStatus.setPreferredSize(new Dimension(0, 35));
		panelLogs.add(panelLogStatus, BorderLayout.SOUTH);

		btnClearLogs = new JButton("Clear Logs");
		btnClearLogs.setForeground(new Color(255, 255, 255));
		btnClearLogs.setBackground(new Color(220, 20, 60));
		btnClearLogs.setFocusPainted(false);
		panelLogStatus.setLayout(new GridLayout(0, 2, 0, 0));
		panelLogStatus.add(btnClearLogs);
		btnClearLogs.setBorder(new LineBorder(null));
		btnClearLogs.setFont(new Font("Arial", Font.BOLD, 15));
		btnClearLogs.addActionListener(this);

		panelCounter = new JPanel();
		panelCounter.setBackground(new Color(255, 255, 255));
		panelCounter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelLogStatus.add(panelCounter);
		panelCounter.setLayout(new GridLayout(0, 2, 0, 0));

		lblTextCounter = new JLabel(" Data Count");
		lblTextCounter.setFont(new Font("Arial", Font.BOLD, 12));
		panelCounter.add(lblTextCounter);
		lblCounter = new JLabel(Integer.toString(counter));
		lblCounter.setFont(new Font("Arial", Font.BOLD, 12));
		panelCounter.add(lblCounter);

		txtPaneLogs = new JTextArea();
		panelLogs.add(txtPaneLogs, BorderLayout.CENTER);
		txtPaneLogs.setBorder(null);
		txtPaneLogs.setEditable(false);
		txtPaneLogs.setFont(new Font("Arial", Font.PLAIN, 17));
		txtPaneLogs.setLineWrap(true);
		txtPaneLogs.setWrapStyleWord(true);
		txtPaneLogs.setEditable(false);

		panelInsertData = new JPanel();
		panelInsertData.setBackground(new Color(255, 255, 255));
		panelInsertData.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelInsertData, "cell 0 1,growx,aligny top");
		panelInsertData.setLayout(new MigLayout("", "[147px][86px][65px][95px,grow]",
				"[35.00px][175px][20.00px][][20.00px][60.00][20.00px][40px][][grow][55.00][][][grow][36.00,bottom][]"));

		panelNameInfo = new JPanel();
		panelNameInfo.setBackground(new Color(255, 255, 255));
		panelNameInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelNameInfo, "cell 0 1 4 1,growx,aligny top");
		panelNameInfo.setLayout(new MigLayout("", "[99px][448.00px,grow]", "[27.00px][40px][40px][40px]"));

		txtMinitial = new JTextField();
		txtMinitial.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMinitial.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelNameInfo.add(txtMinitial, "cell 1 2,grow");
		txtMinitial.setColumns(10);

		txtFname = new JTextField();
		txtFname.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFname.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelNameInfo.add(txtFname, "cell 1 1,grow");
		txtFname.setColumns(10);

		txtLname = new JTextField();
		txtLname.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLname.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtLname.setColumns(10);
		panelNameInfo.add(txtLname, "cell 1 3,grow");

		lblFname = new JLabel("First Name");
		lblFname.setFont(new Font("Arial", Font.PLAIN, 15));
		panelNameInfo.add(lblFname, "cell 0 1,growx,aligny center");

		lblLname = new JLabel("Last Name");
		lblLname.setFont(new Font("Arial", Font.PLAIN, 15));
		panelNameInfo.add(lblLname, "cell 0 3,growx,aligny center");

		lblMiddleInitial = new JLabel("Middle Name");
		lblMiddleInitial.setFont(new Font("Arial", Font.PLAIN, 15));
		panelNameInfo.add(lblMiddleInitial, "cell 0 2,alignx left,aligny center");

		btnSex = new ButtonGroup();
		lblPersonalInformation = new JLabel("Personal Information");

		lblPersonalInformation_1 = new JLabel("Name");
		lblPersonalInformation_1.setFont(new Font("Arial", Font.BOLD, 15));
		panelNameInfo.add(lblPersonalInformation_1, "cell 0 0,growx,aligny top");
		panelInsertData.add(lblPersonalInformation, "cell 0 0,alignx left,aligny center");
		lblPersonalInformation.setFont(new Font("Arial", Font.BOLD, 15));

		lblErrorStatus = new JLabel("");
		lblErrorStatus.setFont(new Font("Arial", Font.PLAIN, 15));
		panelInsertData.add(lblErrorStatus, "flowx,cell 1 0 3 1,grow");

		panelSex = new JPanel();
		panelSex.setBackground(new Color(255, 255, 255));
		panelSex.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelSex, "cell 0 3 4 1,grow");
		panelSex.setLayout(new MigLayout("", "[172px][172px][172px,grow]", "[48px]"));

		lblSex = new JLabel("Sex");
		lblSex.setBackground(new Color(255, 255, 255));
		lblSex.setFont(new Font("Arial", Font.BOLD, 15));
		panelSex.add(lblSex, "cell 0 0,grow");

		// radiobuttons (Male and Female sex)
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBorder(null);
		rdbtnMale.setBackground(new Color(255, 255, 255));
		rdbtnMale.setFont(new Font("Arial", Font.PLAIN, 15));
		rdbtnMale.setActionCommand("Male");
		panelSex.add(rdbtnMale, "cell 1 0,grow");

		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBackground(new Color(255, 255, 255));
		rdbtnFemale.setBorder(null);
		rdbtnFemale.setFont(new Font("Arial", Font.PLAIN, 15));
		rdbtnFemale.setActionCommand("Female");
		panelSex.add(rdbtnFemale, "cell 2 0,grow");
		btnSex.add(rdbtnMale);
		btnSex.add(rdbtnFemale);
		// end of radiobuttons selection

		panelAge = new JPanel();
		panelAge.setBackground(new Color(255, 255, 255));
		panelAge.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelAge, "cell 0 5 4 1,grow");
		panelAge.setLayout(new MigLayout("", "[65.00px][][196.00px,grow][75.00px][][185.00,grow]", "[40px]"));

		lblBirthDay = new JLabel("Birthday");
		panelAge.add(lblBirthDay, "cell 0 0,alignx center,aligny center");
		lblBirthDay.setFont(new Font("Arial", Font.BOLD, 15));

		birthChooser = new JDateChooser();
		birthChooser.getCalendarButton().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		birthChooser.setDateFormatString("M-d-yyyy");
		birthChooser.setFont(new Font("Arial", Font.PLAIN, 15));
		birthChooser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		birthChooser.setDate(null);
		panelAge.add(birthChooser, "cell 2 0,grow");
		birthChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("date".equals(evt.getPropertyName()))
				{
					Date cdt = (Date) evt.getNewValue();
					if (cdt != null)
					{
						HashMap<String, Integer> todt = dateDiff(cdt);
						spinAge.setValue(todt.get("Years"));
					}
				}
			}
		});

		lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial", Font.BOLD, 15));
		panelAge.add(lblAge, "cell 3 0,alignx right,aligny center");

		spinAge = new JSpinner();
		spinAge.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spinAge.setFont(new Font("Arial", Font.PLAIN, 15));
		panelAge.add(spinAge, "cell 5 0,grow");
		spinAge.setModel(new SpinnerNumberModel(0, 0, 1000, 1));

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panel, "cell 0 7 4 2,growx,aligny top");
		panel.setLayout(new MigLayout("", "[85.00px][249.00px,grow]", "[45.00px]"));

		lblPurok = new JLabel("Purok");
		panel.add(lblPurok, "cell 0 0,alignx left,aligny center");
		lblPurok.setFont(new Font("Arial", Font.BOLD, 15));

		spinPurok = new JSpinner();
		spinPurok.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		spinPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(spinPurok, "cell 1 0,grow");

		panelDeceased = new JPanel();
		panelDeceased.setBackground(new Color(255, 255, 255));
		panelDeceased.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelDeceased, "cell 0 10 4 1,grow");
		panelDeceased.setLayout(new MigLayout("", "[175px][175px,grow][175px,grow]", "[44px]"));

		lblDeceased = new JLabel("Deceased");
		lblDeceased.setFont(new Font("Arial", Font.BOLD, 15));
		panelDeceased.add(lblDeceased, "cell 0 0,grow");

		rbtnDYes = new JRadioButton("Yes");
		rbtnDYes.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDYes.setBorder(null);
		rbtnDYes.setBackground(Color.WHITE);
		rbtnDYes.setActionCommand("Yes");
		panelDeceased.add(rbtnDYes, "cell 1 0,grow");

		rbtnDNo = new JRadioButton("No");
		rbtnDNo.setFont(new Font("Arial", Font.PLAIN, 15));
		rbtnDNo.setBorder(null);
		rbtnDNo.setActionCommand("No");

		btnDeceased = new ButtonGroup();
		btnDeceased.add(rbtnDNo);
		btnDeceased.add(rbtnDYes);

		panelDeceased.add(rbtnDNo, "cell 2 0,grow");

		panelBtns = new JPanel();
		panelBtns.setBackground(new Color(255, 255, 255));
		panelBtns.setForeground(new Color(255, 255, 255));
		panelBtns.setBorder(null);
		panelInsertData.add(panelBtns, "cell 2 14 2 2,alignx right,aligny bottom");
		panelBtns.setLayout(new MigLayout("", "[80.00][][1.00][115.00]", "[60.00]"));

		btnReset = new JButton("Reset");
		btnReset.setForeground(new Color(0, 0, 0));
		panelBtns.add(btnReset, "cell 0 0 2 1,grow");
		btnReset.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnReset.setBackground(new Color(255, 255, 255));
		btnReset.setFont(new Font("Arial", Font.BOLD, 15));
		btnReset.setFocusPainted(false);

		// bottom buttons
		btnSubmit = new JButton("Update");
		btnSubmit.setForeground(new Color(255, 255, 255));
		panelBtns.add(btnSubmit, "cell 3 0,grow");
		btnSubmit.setBackground(new Color(30, 144, 255));
		btnSubmit.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 15));
		btnSubmit.setFocusPainted(false);

		lblNewLabel = new JLabel("");
		panelInsertData.add(lblNewLabel, "cell 3 0,grow");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);

		_conn = new DbConn();
		_conn.oopen();
		counter = _conn.citizensCount();
		lblCounter.setText(Integer.toString(counter));

		panelSearch = new JPanel();
		panelSearch.setBackground(new Color(255, 140, 0));
		panelMain.add(panelSearch, "cell 0 0 2 1,grow");
		panelSearch.setLayout(new MigLayout("", "[][grow]", "[91.00]"));

		lblSearch = new JLabel("Search  ");
		lblSearch.setFont(new Font("Arial", Font.BOLD, 15));
		panelSearch.add(lblSearch, "cell 0 0,alignx trailing");

		searchComboBox = new JComboBox<String>();
		searchComboBox.setBorder(new EmptyBorder(0, 0, 0, 0));

		// add an action listener to listen for selection changes in the JComboBox
		searchComboBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_conn.oopen();
				// get the selected item from the JComboBox
				String selectedItem = (String) searchComboBox.getSelectedItem();

				// find the start and end indices of the uid value in the string
				int start = selectedItem.indexOf("(") + 1;
				int end = selectedItem.indexOf(")");

				// extract the uid value from the string
				String uidString = selectedItem.substring(start, end);

				// parse the uid value as an integer
				uid = Integer.parseInt(uidString);

				// call the getDataComboBox method to get the data for the specified uid
				DefaultTableModel model = _conn.getDataComboBox(uid);

				// check if any data was returned
				if (model.getRowCount() > 0)
				{
					// get the data from the first row of the table model
					String firstName = (String) model.getValueAt(0, 0);
					String middleName = (String) model.getValueAt(0, 1);
					String lastName = (String) model.getValueAt(0, 2);
					String sex = (String) model.getValueAt(0, 3);
					String birthday = (String) model.getValueAt(0, 4);
					int age = (int) model.getValueAt(0, 5);
					String purok = (String) model.getValueAt(0, 6);
					String deceased = (String) model.getValueAt(0, 7);

					// set the values of the text fields
					txtFname.setText(firstName);
					txtLname.setText(lastName);
					txtMinitial.setText(middleName);

					// set the value of the JSpinner
					spinAge.setValue(age);

					// set the selected radio button for sex
					if (sex.equals("Male"))
					{
						rdbtnMale.setSelected(true);
					} else if (sex.equals("Female"))
					{
						rdbtnFemale.setSelected(true);
					}

					// set the selected radio button for deceased
					if (deceased.equals("Yes"))
					{
						rbtnDYes.setSelected(true);
					} else
					{
						rbtnDNo.setSelected(true);
					}
					// set the value of the JCalendar
					Date date = formatDate(birthday);
					birthChooser.setDate(date);

					spinPurok.setValue(Integer.parseInt(purok));
				}
				;
				_conn.cclose();
			}
		});
		searchComboBox.setModel(_conn.getcboxModel());

		searchComboBox.setFont(new Font("Arial", Font.PLAIN, 17));
		panelSearch.add(searchComboBox, "cell 1 0,grow");
		_conn.cclose();
		utils.removeFocusPainted(panel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnSubmit)
		{
			submitProcess();
		} else if (e.getSource() == btnReset)
		{
			resetFields();
		} else if (e.getSource() == btnClearLogs)
		{
			txtPaneLogs.setText("");
		}
	}

	public void resetFields()
	{
		txtFname.setText("");
		txtMinitial.setText("");
		txtLname.setText("");
		btnSex.clearSelection();
		spinAge.setValue(0);
		birthChooser.setDate(null);
		spinPurok.setValue(0);
		txtFname.requestFocus();
		txtFname.requestFocusInWindow();
	}

	public void submitProcess()
	{
		if ((fieldsId()) == false)
		{
			return; // error occured in parsing the texts
		}

		_conn = new DbConn();
		_conn.oopen();
		logging = new TxtLogger(txtPaneLogs);

		// walang middle name
		if (txtMinitial.getText().isEmpty())
			txtMinitial.setText("");
		// Update data in citizens and status tables

		int valp = (int) spinPurok.getValue();
		String prval = String.valueOf(valp);
		String rad_selected = btnDeceased.getSelection().getActionCommand();
		String sex_selected = btnSex.getSelection().getActionCommand();
		boolean deceased = rad_selected.equals("Yes") ? true : false;
		String name = txtFname.getText() + " " + txtMinitial.getText() + " " + txtLname.getText();
		System.out.println("This is my uid " + uid);
		System.out.println("Is he dead? " + deceased);

		if (_conn.updateCitizenAndStatus(uid, deceased, txtFname.getText(), txtMinitial.getText(), txtLname.getText(),
				sex_selected, (int) spinAge.getValue(), prval, birthChooser.getDate()))
		{
			logging.keyLogger("Successfully updated", name, "in the database");
			counter = _conn.citizensCount();
			lblCounter.setText(Integer.toString(counter));
			utils.errStatLabel(lblErrorStatus, false); // will not show the error status label
		} else
		{
			logging.keyLogger("Error enountered ", name, "cannot be " + "inserted in the database");
		}
		_conn.cclose();
	}

	public boolean fieldsId()
	{
		String[] name = new String[4];
		name[0] = txtFname.getText();
		name[1] = txtLname.getText();
		name[2] = txtMinitial.getText();

		Date sel_date = birthChooser.getDate();

		boolean txt_fields = !(name[0].isEmpty() && name[1].isEmpty());
		boolean rads = (rdbtnMale.isSelected() || rdbtnFemale.isSelected());
		boolean cur_age = ((int) spinAge.getValue() > 0);
		boolean purok = ((int) spinPurok.getValue() > 0);
		boolean date = (sel_date != null);
		boolean shinda = (rbtnDYes.isSelected() || rbtnDNo.isSelected());

		if ((txt_fields && rads && cur_age && purok && date && shinda) == false)
		{
			utils.errStatLabel(lblErrorStatus, true);
			return false;
		}
		return true;
	}
	public void update()
	{
		_conn.oopen();
		searchComboBox.setModel(_conn.getcboxModel());
		_conn.cclose();
	}

	public static void main(String[] args)
	{
		new DataEditPanel();
	}

}