package src;

import javax.swing.border.*;


import java.awt.*;
import javax.swing.*;
import src.utils.*;
import net.miginfocom.swing.MigLayout;

public class HomePanel extends JPanel {

	private JPanel panelMain;
	private WindowUtils utils;
	private ImageIcon logo;
	private JLabel  lblLogo;
	private JLabel lblTiltle;

	public HomePanel() {
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700); //size of the panel
		setLayout(new BorderLayout(0, 0));
		
		panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(null);
		add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new MigLayout("", "[][grow,center][]", "[57.00][][416.00,grow,baseline][]"));
		utils = new WindowUtils();
		
		//getting the image and resizing it
		int wd = 500;
		int ht = 500;
		logo = new ImageIcon();
		logo = utils.getImage("../../res/images/logo_agila.png");
		Image img = logo.getImage().getScaledInstance(wd, ht, Image.SCALE_SMOOTH);
		logo = new ImageIcon(img);
		
		lblTiltle = new JLabel("A   G   I   L   A");
		lblTiltle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiltle.setFont(new Font("Arial", Font.BOLD, 20));
		panelMain.add(lblTiltle, "cell 1 1,alignx center,aligny center");


		//putting the image in the label
		lblLogo = new JLabel(logo);
		lblLogo.setBorder(null);
		lblLogo.setBounds(311, 91, 120, 115);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setBackground(new Color(204, 102, 0));
		lblLogo.setPreferredSize(new Dimension(logo.getIconWidth(), logo.getIconHeight()));

		//modifying the panel to fit image label
		panelMain.add(lblLogo, "cell 1 2,alignx center,aligny center");

		setVisible(true);
	}

	public static void main(String[] args) 
	{
		new HomePanel();
	}
}
