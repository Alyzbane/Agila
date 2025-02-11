package src;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import src.database.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.JTextPane;

public class SettingPanel extends JPanel implements ActionListener
{
	private JButton btnRecover;
	private JLabel lblRecover;
	private JButton btnDelete;
	private JLabel lblDelete;
	private JButton btnAbout;
	private JTextPane txtAbout;
	private boolean stateAbout;
	private JPanel panel;
	private JPanel panel_1;
	private DbConn _conn;
	private JPanel panelAbout;
	private JLabel lblIcon;

	public SettingPanel()
	{
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(100, 100, 930, 700);
		setLayout(new MigLayout("",
				"[grow][][][][][][175.00,center][grow][][][][][][][][][][][][][][][][][][][][][grow]",
				"[61.00][][bottom][100.00][][][bottom][100.00,center][grow][][60.00,center][grow][][][][][][grow][][][][bottom]"));

		_conn = new DbConn();
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(null);
		add(panel_1, "cell 6 3,grow");
		panel_1.setLayout(new MigLayout("", "[57px,grow]", "[21px][grow,center]"));
		lblRecover = new JLabel("Recover all deleted datas");
		panel_1.add(lblRecover, "cell 0 0,grow");
		lblRecover.setFont(new Font("Arial", Font.BOLD, 17));

		btnRecover = new JButton("Recover");
		panel_1.add(btnRecover, "cell 0 1,grow");
		btnRecover.setForeground(new Color(245, 255, 250));
		btnRecover.setBackground(new Color(30, 144, 255));
		btnRecover.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnRecover.setFont(new Font("Arial", Font.BOLD, 15));
		btnRecover.setFocusPainted(false);
		btnRecover.addActionListener(this);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(255, 255, 255));
		add(panel, "cell 6 7,grow");
		panel.setLayout(new MigLayout("", "[342px][101.00px]", "[24px][56.00,grow]"));

		lblDelete = new JLabel("Delete permanently all deleted datas");
		panel.add(lblDelete, "cell 0 0,alignx left,aligny top");
		lblDelete.setFont(new Font("Arial", Font.BOLD, 17));

		btnDelete = new JButton("Delete");
		panel.add(btnDelete, "cell 0 1,grow");
		btnDelete.setForeground(new Color(245, 255, 250));
		btnDelete.setBackground(new Color(220, 20, 60));
		btnDelete.setFont(new Font("Arial", Font.BOLD, 15));
		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnDelete.setFocusPainted(false);
		btnDelete.addActionListener(this);

		btnAbout = new JButton("About");
		btnAbout.setBackground(new Color(255, 255, 255));
		btnAbout.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAbout.setFont(new Font("Arial", Font.BOLD, 15));
		btnAbout.setFocusPainted(false);
		btnAbout.addActionListener(this);
		add(btnAbout, "cell 6 10,grow");

		panelAbout = new JPanel();
		panelAbout.setVisible(false);
		panelAbout.setBackground(new Color(255, 255, 255));
		add(panelAbout, "cell 6 11 16 11,grow");
		panelAbout.setLayout(new MigLayout("", "[470px][grow]", "[222px]"));

		txtAbout = new JTextPane();
		panelAbout.add(txtAbout, "cell 0 0,grow");
		txtAbout.setFont(new Font("Arial", Font.PLAIN, 15));
		txtAbout.setText(
				"ITECC06 FINAL PROJECT \r\nAGILA - Automation Group Identification and List Allocation System (1.0)\r\n(c) 2023 - Made with love by:\r\n\r\nLead Developer:\r\nPrince Daniel D. Mampusti\r\n\r\nDevelopment Team:\r\nDhan Eldrin Mabilangan\r\n\r\nExternal Libraries and Frameworks:\r\niTextPdf\r\nApache POI");
		txtAbout.setEditable(false);
		txtAbout.setVisible(true);

		// adding icon image
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/splash_agila.png")));
		Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		lblIcon = new JLabel(icon);
		panelAbout.add(lblIcon, "cell 1 0");
		stateAbout = false;

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnDelete)
		{
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all data?",
					"Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION)
			{
				_conn.oopen();
				int rupdt = _conn.permanentDeleteDatas();
				JOptionPane.showMessageDialog(null, rupdt + " data has been deleted!");
				_conn.cclose();

			}

		} else if (e.getSource() == btnRecover)
		{
			int result = JOptionPane.showConfirmDialog(null, "Do you wish to recover all deleted data?",
					"Confirm Recovery", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION)
			{
				_conn.oopen();
				int rupdt = _conn.recoverDeletedDatas();
				JOptionPane.showMessageDialog(null, rupdt + " data has been recovered!");
				_conn.cclose();
			}

		} else if (e.getSource() == btnAbout)
		{
			panelAbout.setVisible(!stateAbout);
			stateAbout = !stateAbout;
		}
	}

	public void resetState()
	{
		stateAbout = false;
		panelAbout.setVisible(stateAbout);
	}

}
