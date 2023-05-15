package src;

import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import src.database.*;
import src.UI.*;
import java.util.*;
import src.utils.*;
import static src.utils.Oras.*;

import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.*;

public class DataHandlingPanel extends JPanel implements ActionListener {
	private JPanel panelMain;
	protected JTextField txtFname;
	protected JTextField txtLname;
	protected JTextField txtMinitial;
	private JPanel panelCounter, panelInsertData, panelLogs, panelNameInfo,
					panelSex, panelAge, panelLogStatus;
	
	private JScrollPane scrollLog;
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
	protected int counter;
	
	//database
	DbConn _conn;
	private JLabel lblErrorStatus;
	private JLabel lblPurok;
	private JLabel lblBirthDay;
	private JPanel panel;
	protected JDateChooser birthChooser;
	private JPanel panelBtns;
	private WindowUtils util;
	protected JSpinner spinPurok;
	
	public DataHandlingPanel(String stateInit) {
		setBackground(new Color(240, 240, 240));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700); //size of the panel
		setLayout(new BorderLayout(0, 0));
		
		panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(null);
		add(panelMain, BorderLayout.CENTER);
		SpringLayout sl_panelMain = new SpringLayout();
		panelMain.setLayout(sl_panelMain);
		util = new WindowUtils();
		
		panelInsertData = new JPanel();
		sl_panelMain.putConstraint(SpringLayout.WEST, panelInsertData, 10, SpringLayout.WEST, panelMain);
		panelInsertData.setBackground(new Color(255, 255, 255));
		sl_panelMain.putConstraint(SpringLayout.NORTH, panelInsertData, 10, SpringLayout.NORTH, panelMain);
		sl_panelMain.putConstraint(SpringLayout.SOUTH, panelInsertData, -10, SpringLayout.SOUTH, panelMain);
		panelInsertData.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelInsertData);
		
		panelLogs = new JPanel();
		sl_panelMain.putConstraint(SpringLayout.EAST, panelInsertData, -26, SpringLayout.WEST, panelLogs);
		sl_panelMain.putConstraint(SpringLayout.WEST, panelLogs, 581, SpringLayout.WEST, panelMain);
		sl_panelMain.putConstraint(SpringLayout.EAST, panelLogs, -10, SpringLayout.EAST, panelMain);
		sl_panelMain.putConstraint(SpringLayout.NORTH, panelLogs, 10, SpringLayout.NORTH, panelMain);
		sl_panelMain.putConstraint(SpringLayout.SOUTH, panelLogs, -10, SpringLayout.SOUTH, panelMain);
		panelInsertData.setLayout(new MigLayout("", "[147px,grow][27px][86px,grow][65px][94px,grow][23.00px][95px]", "[35.00px][175px][20.00px][][20.00px][60.00][20.00px][40px][][grow][55.00][][][grow][36.00,bottom][]"));
		
		panelNameInfo = new JPanel();
		panelNameInfo.setBackground(new Color(255, 255, 255));
		panelNameInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelNameInfo, "cell 0 1 7 1,growx,aligny top");
		panelNameInfo.setLayout(new MigLayout("", "[99px][395px]", "[27.00px][40px][40px][40px]"));
		
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
		
		
		//side panel filled with logs
		panelLogs.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMain.add(panelLogs);
		panelLogs.setLayout(new BorderLayout(0, 0));
		
		panelLogStatus = new JPanel();
		panelLogStatus.setBorder(null);
		panelLogStatus.setPreferredSize(new Dimension(0, 35));
		panelLogs.add(panelLogStatus, BorderLayout.SOUTH);
		
		btnClearLogs = new JButton("Clear Logs");
		btnClearLogs.setForeground(new Color(255, 255, 255));
		btnClearLogs.setBackground(new Color(220, 20, 60));
		btnClearLogs.setFocusPainted(false);
		
		lblErrorStatus = new JLabel("");
		lblErrorStatus.setFont(new Font("Arial", Font.PLAIN, 15));
		panelInsertData.add(lblErrorStatus, "cell 2 0 5 1,grow");
		
		panelSex = new JPanel();
		panelSex.setBackground(new Color(255, 255, 255));
		panelSex.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelSex, "cell 0 3 7 1,grow");
		panelSex.setLayout(new MigLayout("", "[172px][172px][172px]", "[48px]"));
		
		lblSex = new JLabel("Sex");
		lblSex.setBackground(new Color(255, 255, 255));
		lblSex.setFont(new Font("Arial", Font.BOLD, 15));
		panelSex.add(lblSex, "cell 0 0,grow");
		
		//radiobuttons (Male and Female sex)
		rdbtnMale =  new JRadioButton("Male");
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
		//end of radiobuttons selection
		
		panelAge = new JPanel();
		panelAge.setBackground(new Color(255, 255, 255));
		panelAge.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInsertData.add(panelAge, "cell 0 5 7 1,grow");
		panelAge.setLayout(new MigLayout("", "[65.00px][][196.00px][75.00px][][185.00]", "[40px]"));
		
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
			public void propertyChange(PropertyChangeEvent evt) {
				if("date".equals(evt.getPropertyName()))
				{
					Date cdt = (Date) evt.getNewValue();
					if(cdt != null)
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
		panel.setLayout(new MigLayout("", "[85.00px][249.00px]", "[45.00px]"));
		
		lblPurok = new JLabel("Purok");
		panel.add(lblPurok, "cell 0 0,alignx left,aligny center");
		lblPurok.setFont(new Font("Arial", Font.BOLD, 15));
		
		spinPurok = new JSpinner();
		spinPurok.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		spinPurok.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(spinPurok, "cell 1 0,grow");
		
		btnDeceased = new ButtonGroup();
		
		panelBtns = new JPanel();
		panelBtns.setBackground(new Color(255, 255, 255));
		panelBtns.setForeground(new Color(255, 255, 255));
		panelBtns.setBorder(null);
		panelInsertData.add(panelBtns, "cell 3 14 4 2,alignx right,aligny bottom");
		panelBtns.setLayout(new MigLayout("", "[80.00][][1.00][115.00]", "[60.00]"));
		
		btnReset = new JButton("Reset");
		btnReset.setForeground(new Color(0, 0, 0));
		panelBtns.add(btnReset, "cell 0 0 2 1,grow");
		btnReset.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnReset.setBackground(new Color(255, 255, 255));
		btnReset.setFont(new Font("Arial", Font.BOLD, 15));
		btnReset.setFocusPainted(false);
		
		//bottom buttons
		btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(new Color(255, 255, 255));
		panelBtns.add(btnSubmit, "cell 3 0,grow");
		btnSubmit.setBackground(new Color(30, 144, 255));
		btnSubmit.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 15));
		btnSubmit.addActionListener(this);
		btnSubmit.setFocusPainted(false);

		btnReset.addActionListener(this);
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
		
		_conn = new DbConn();
		_conn.oopen();
		counter = _conn.citizensCount();
		_conn.cclose();
		lblCounter = new JLabel(Integer.toString(counter));
		lblCounter.setFont(new Font("Arial", Font.BOLD, 12));
		panelCounter.add(lblCounter);

		txtPaneLogs = new JTextArea();
		txtPaneLogs.setBorder(null);
		txtPaneLogs.setEditable(false);
		txtPaneLogs.setFont(new Font("Arial", Font.PLAIN, 12));
		txtPaneLogs.setLineWrap(true);
		txtPaneLogs.setWrapStyleWord(true);
		txtPaneLogs.setEditable(false);

		scrollLog = new JScrollPane(txtPaneLogs);
		scrollLog.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelLogs.add(scrollLog, BorderLayout.CENTER);
		util.removeFocusPainted(panelSex);
		setVisible(true);
	}
	
	
    public void actionPerformed(ActionEvent e) 
    {
    		if(e.getSource() == btnSubmit)
    		{
    			submitProcess();
    		}
    		else if(e.getSource() == btnReset)
    		{
    			resetFields();
    		}
    		else if(e.getSource() == btnClearLogs)
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
    	if((fieldsId()) == false) 
    	{
    		return; //error occured in parsing the texts 
    	}

    	_conn = new DbConn();
    	_conn.oopen();
    	//get the name of radiobutton selected
    	String rad_selected = btnSex.getSelection().getActionCommand();

		logging = new TxtLogger(txtPaneLogs);

		//walang middle name
		if(txtMinitial.getText().isEmpty()) txtMinitial.setText(""); 

		String name =  txtFname.getText() + " " + txtMinitial.getText() + " " + txtLname.getText();
		int valp = (int) spinPurok.getValue();
		String prval = String.valueOf(valp);

    	 if(_conn.InsertData(txtFname.getText(), 
    		txtMinitial.getText(), txtLname.getText(), 
    		rad_selected, (int) spinAge.getValue(), prval,
    		birthChooser.getDate()))
    	 //data successfully inserted
    	  {
		    logging.keyLogger("Successfully inserted", name,"in the database");
		    counter = _conn.citizensCount();
    		lblCounter.setText(Integer.toString(counter));
    		errStatLabel(false); //will not shhow the error status label
    	  }
    	  else
    	  {
    		logging.keyLogger("Error enountered ", name, "cannot be "
    				+ "inserted in the database");
    	  }
    	_conn.cclose();
    }
    
    
    protected void errStatLabel(boolean status)
    {

    	lblErrorStatus.setVisible(status);
    	if(status)
    	{
    		lblErrorStatus.setForeground(Color.RED);
    		lblErrorStatus.setText("Please fill up all of the fields");
    	}
    }
 
    
    public boolean fieldsId() {
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

        if((txt_fields && rads && cur_age && purok && date) == false)
        {
        	errStatLabel(true);
    		return false;
    	}
    	return true;
    }
    
	public static void main(String[] args) 
	{
		new DataHandlingPanel(null);
	}
}