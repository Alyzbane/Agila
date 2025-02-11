package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import src.database.*;
import src.utils.WindowUtils;
import static src.utils.WindowUtils.switchPanel;

import javax.swing.border.LineBorder;

public class Dashboard extends JFrame implements ActionListener
{

	private int[] dimWindow = new int[2];
	private WindowUtils util;
	private SettingPanel setPanel;
	private DbConn _conn;
	private JButton btnLogOut;
	private JButton btnCreate;
	private JButton btnList;
	private JButton btnHome;
	private JPanel panelCard;
	private JPanel panelFrame;
	private JPanel panelNav;
	private DataHandlingPanel dhPanel;
	private CitizensList listPanel;
	private DataEditPanel editPanel;
	private DataPrintPanel printPanel;
	private JButton btnEdit;
	private JButton btnPrint;
	private HomePanel homePanel;
	private ImageIcon logo;
	private WindowUtils utils;
	private JButton btnSettings;
	private JLabel lblTitleSide;
	private JButton[] btngrp;
	private groupButtons gbtn;
	private JLabel lblNewLabel;

	public Dashboard()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/logo_agila.png")));
		// setUndecorated(true);
		// setResizable(false);
		setTitle("Agila");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		util = new WindowUtils();
		dimWindow = util.getWindowSize();
		_conn = new DbConn();

		setBounds(100, 100, dimWindow[0], dimWindow[1]);
		setLocationRelativeTo(null);

		setMinimumSize(new Dimension(1080, 720));
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelFrame = new JPanel();
		panelFrame.setBorder(null);
		panelFrame.setBackground(new Color(255, 255, 255));
		panelFrame.setLayout(new BorderLayout());

		panelNav = new JPanel();

		panelNav.setPreferredSize(new Dimension(150, 0)); // set preferred width of panel

		panelNav.setBackground(new Color(255, 140, 0));
		panelFrame.add(panelNav, BorderLayout.WEST);

		// getting the image and resizing it
		utils = new WindowUtils();
		int wd = 100;
		int ht = 100;

		logo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/logo_agila.png")));
		Image img = logo.getImage().getScaledInstance(wd, ht, Image.SCALE_SMOOTH);
		logo = new ImageIcon(img);
		GridBagLayout gbl_panelNav = new GridBagLayout();
		gbl_panelNav.columnWidths = new int[] { 136, 0 };
		gbl_panelNav.rowHeights = new int[] { 0, 103, 33, 41, 40, 40, 40, 40, 40, 40, 240, 40, 0 };
		gbl_panelNav.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelNav.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panelNav.setLayout(gbl_panelNav);

		btnPrint = new JButton("Print");
		btnPrint.setForeground(new Color(255, 255, 255));
		btnPrint.setBorder(null);
		btnPrint.setBackground(new Color(255, 140, 0));
		btnPrint.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPrint.addActionListener(this);

		btnList = new JButton("List");
		btnList.setForeground(new Color(255, 255, 255));
		btnList.setBorder(null);
		btnList.setBackground(new Color(255, 140, 0));
		btnList.setFont(new Font("Arial", Font.PLAIN, 15));
		btnList.addActionListener(this);

		btnHome = new JButton("Home");
		btnHome.setForeground(new Color(255, 255, 255));
		btnHome.setBorder(null);
		btnHome.setBackground(new Color(255, 140, 0));
		btnHome.setFont(new Font("Arial", Font.PLAIN, 15));
		btnHome.addActionListener(this);

		JLabel lblSideLogo = new JLabel(logo);
		lblSideLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideLogo.setPreferredSize(new Dimension(logo.getIconWidth() / 2, logo.getIconHeight() / 2));
		GridBagConstraints gbc_lblSideLogo = new GridBagConstraints();
		gbc_lblSideLogo.fill = GridBagConstraints.BOTH;
		gbc_lblSideLogo.insets = new Insets(0, 0, 5, 0);
		gbc_lblSideLogo.gridx = 0;
		gbc_lblSideLogo.gridy = 1;
		panelNav.add(lblSideLogo, gbc_lblSideLogo);

		lblTitleSide = new JLabel("Agila");
		lblTitleSide.setForeground(new Color(250, 235, 215));
		lblTitleSide.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleSide.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));
		GridBagConstraints gbc_lblTitleSide = new GridBagConstraints();
		gbc_lblTitleSide.anchor = GridBagConstraints.NORTH;
		gbc_lblTitleSide.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitleSide.gridx = 0;
		gbc_lblTitleSide.gridy = 2;
		panelNav.add(lblTitleSide, gbc_lblTitleSide);
		
		lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		panelNav.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.BOTH;
		gbc_btnHome.insets = new Insets(0, 0, 5, 0);
		gbc_btnHome.gridx = 0;
		gbc_btnHome.gridy = 4;
		panelNav.add(btnHome, gbc_btnHome);
		GridBagConstraints gbc_btnList = new GridBagConstraints();
		gbc_btnList.fill = GridBagConstraints.BOTH;
		gbc_btnList.insets = new Insets(0, 0, 5, 0);
		gbc_btnList.gridx = 0;
		gbc_btnList.gridy = 5;
		panelNav.add(btnList, gbc_btnList);

		btnEdit = new JButton("Edit");
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setBorder(null);
		btnEdit.setBackground(new Color(255, 140, 0));
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEdit.addActionListener(this);

		btnCreate = new JButton("Create");
		btnCreate.setForeground(new Color(255, 255, 255));
		btnCreate.setBorder(null);
		btnCreate.setBackground(new Color(255, 140, 0));
		btnCreate.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCreate.addActionListener(this);
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.fill = GridBagConstraints.BOTH;
		gbc_btnCreate.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreate.gridx = 0;
		gbc_btnCreate.gridy = 6;
		panelNav.add(btnCreate, gbc_btnCreate);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.BOTH;
		gbc_btnEdit.insets = new Insets(0, 0, 5, 0);
		gbc_btnEdit.gridx = 0;
		gbc_btnEdit.gridy = 7;
		panelNav.add(btnEdit, gbc_btnEdit);
		GridBagConstraints gbc_btnPrint = new GridBagConstraints();
		gbc_btnPrint.fill = GridBagConstraints.BOTH;
		gbc_btnPrint.insets = new Insets(0, 0, 5, 0);
		gbc_btnPrint.gridx = 0;
		gbc_btnPrint.gridy = 8;
		panelNav.add(btnPrint, gbc_btnPrint);

		btnSettings = new JButton("Settings");
		btnSettings.setForeground(new Color(255, 255, 255));
		btnSettings.setBackground(new Color(255, 140, 0));
		btnSettings.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSettings.setBorder(null);
		btnSettings.addActionListener(this);
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnSettings.gridx = 0;
		gbc_btnSettings.gridy = 9;
		panelNav.add(btnSettings, gbc_btnSettings);

		btnLogOut = new JButton("Log Out");
		btnLogOut.setForeground(new Color(255, 255, 255));
		btnLogOut.setBackground(new Color(178, 34, 34));
		btnLogOut.setBorder(null);
		btnLogOut.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLogOut.addActionListener(this);

		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.fill = GridBagConstraints.BOTH;
		gbc_btnLogOut.gridx = 0;
		gbc_btnLogOut.gridy = 11;
		panelNav.add(btnLogOut, gbc_btnLogOut);

		getContentPane().add(panelFrame, BorderLayout.CENTER);

		panelCard = new JPanel();
		panelFrame.add(panelCard, BorderLayout.CENTER);
		panelCard.setLayout(new CardLayout(0, 0));
		
		//grouping the buttons
		btngrp = new JButton[6];
		btngrp[0] = btnCreate;
		btngrp[1] = btnHome;
		btngrp[2] = btnList;
		btngrp[3] = btnSettings;
		btngrp[4] = btnEdit;
		btngrp[5] = btnPrint;
		
		gbtn = new groupButtons(btngrp);
		
		for(JButton btn : btngrp)
		{
			btn.setFocusPainted(false);
		}
		btnLogOut.setFocusPainted(false);
		
		//adding the panels into cardlayou
		homePanel = new HomePanel();
		dhPanel = new DataHandlingPanel(null);
		listPanel = new CitizensList();
		editPanel = new DataEditPanel();
		printPanel = new DataPrintPanel();
		setPanel = new SettingPanel();

		panelCard.add(homePanel, "home");
		panelCard.add(listPanel, "list");
		panelCard.add(dhPanel, "create");
		panelCard.add(editPanel, "edit");
		panelCard.add(printPanel, "print");
		panelCard.add(setPanel, "settings");

		setVisible(true);
	}
	protected void processWindowEvent(WindowEvent e) {
	        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	            int result = JOptionPane.showConfirmDialog(
	                this,
	                "Are you sure you want to exit?",
	                "Exit",
	                JOptionPane.YES_NO_OPTION
	            );
	            if (result == JOptionPane.YES_OPTION) {
	                // User clicked "Yes", so exit the program
	                System.exit(0);
	            }
	        } else {
	            super.processWindowEvent(e);
	        }
	    }
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnHome)
		{
			switchPanel(panelCard, "home");
		} else if (e.getSource() == btnLogOut)
		{
			_conn.oopen();
			_conn.deleteSession();
			new LoginWindow();
			dispose();
			_conn.cclose();
		} else if (e.getSource() == btnCreate)
		{
			dhPanel.resetFields();
			switchPanel(panelCard, "create");
		} else if (e.getSource() == btnList)
		{
			listPanel.resetFields();
			listPanel.updateTable();
			switchPanel(panelCard, "list");
		} else if (e.getSource() == btnEdit)
		{
			editPanel.update();
			switchPanel(panelCard, "edit");
		} else if (e.getSource() == btnPrint)
		{
			printPanel.resetFields();
			printPanel.updateTable();
			switchPanel(panelCard, "print");
		} else if (e.getSource() == btnSettings)
		{
			setPanel.resetState();
			switchPanel(panelCard, "settings");
		}
	}

	public static void main(String[] args)
	{
		new Dashboard();
	}
}

class groupButtons {
    private JButton clickedButton = null;

    public groupButtons(JButton[] buttons) {
    	//default clicked button is Home
		buttons[1].setForeground(Color.black);
		buttons[1].setBackground(Color.white);
		clickedButton = buttons[1];

        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (clickedButton != null) {
                        // Change the previously clicked button back to its default state
                        clickedButton.setForeground(Color.white);
                        clickedButton.setBackground(null);
                    }
                    // Change the foreground and background color of the clicked button
                    button.setForeground(Color.BLACK);
                    button.setBackground(Color.WHITE);
                    // Store a reference to the clicked button
                    clickedButton = button;
                }
            });
        }
    }
}

