package src;

import src.utils.WindowUtils;
import src.UI.PassFieldFocusAdapter;
import src.database.DbConn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginWindow extends JFrame implements ActionListener
{

	private JLabel lblStatus;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JPanel panel;
	private JPanel panelPassword;
	private JButton btnRegister;

	private DbConn _conn;
	private JLabel lblIcon;
	private WindowUtils utils;
	private ImageIcon icon;
	private Image img;

	public LoginWindow()
	{
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(485, 340);
		setResizable(false);
		setLocationRelativeTo(null);

		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(255, 255, 255));
		utils = new WindowUtils(); // instance of utility handler

		JPanel panelUser = new JPanel();
		panelUser.setBounds(30, 91, 235, 52);
		panelUser.setBackground(new Color(255, 255, 255));
		panelUser.setBorder(new LineBorder(new Color(0, 0, 0)));

		panelPassword = new JPanel();
		panelPassword.setBounds(30, 154, 235, 52);
		panelPassword.setBackground(new Color(255, 255, 255));
		panelPassword.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblStatus = new JLabel();
		lblStatus.setBounds(30, 259, 235, 30);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));

		
		ImageIcon logo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/taskbar_icon.png")));
		Image img = logo.getImage().getScaledInstance(
				logo.getIconWidth(), logo.getIconHeight(), Image.SCALE_SMOOTH);
		setIconImage(img);
		// adding icon image
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/logo_agila.png")));
		img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);

		panel.setLayout(null);

		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 12));
		btnRegister.setBackground(new Color(255, 255, 255));
		btnRegister.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnRegister.setBounds(95, 217, 80, 31);
		btnRegister.addActionListener(this);

		lblIcon = new JLabel(icon);
		lblIcon.setBounds(311, 91, 120, 115);
		lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcon.setBackground(new Color(204, 102, 0));
		lblIcon.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		panel.add(panelPassword);
		panelPassword.setLayout(null);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 11, 215, 30);
		panelPassword.add(txtPassword);
		txtPassword.setToolTipText("Password");
		txtPassword.setText("Password");
		txtPassword.setEchoChar((char) 0);
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
		txtPassword.setBorder(null);
		txtPassword.addFocusListener(new PassFieldFocusAdapter(txtPassword, "Password"));
		panel.add(panelUser);
		panelUser.setLayout(null);

		// inputting user informations
		txtUsername = new JTextField();
		txtUsername.setBounds(10, 11, 215, 30);
		panelUser.add(txtUsername);
		txtUsername.setToolTipText("Username");
		txtUsername.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (txtUsername.getText().equals("Username"))
					txtUsername.setText("");
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (txtUsername.getText().equals(""))
					txtUsername.setText("Username");
			}
		});
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
		txtUsername.setText("Username");
		txtUsername.setBorder(null);

		JLabel lblWelcome = new JLabel("Login");
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 30));
		lblWelcome.setBounds(30, 18, 183, 62);
		lblWelcome.setFocusable(true);
		lblWelcome.requestFocusInWindow();
		panel.add(lblStatus);
		panel.add(btnRegister);
		panel.add(lblIcon);
		panel.add(lblWelcome);

		getContentPane().add(panel);

		// end of inputting user information to login

		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 12));
		btnLogin.setBackground(new Color(255, 140, 0));
		btnLogin.setBorder(new LineBorder(Color.BLACK));
		btnLogin.setBounds(185, 217, 80, 30);
		btnLogin.setFocusPainted(false);
		btnRegister.setFocusPainted(false);
		btnLogin.addActionListener(this);

		panel.add(btnLogin);
		getContentPane().requestFocusInWindow();

		_conn = new DbConn();
		_conn.oopen();
		setVisible(true);
		if(_conn.sessionCheck())
		{
			createDashBoard();
		}
		_conn.cclose();
	}
	public void createDashBoard()
	{
		setVisible(false);
		new Dashboard();
		this.dispose();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnLogin)
		{
			// check if the users login successfully
			// connecting to database
			_conn.oopen();
			int test_conn = _conn.authenticateUser(txtUsername.getText(), new String(txtPassword.getPassword()));
			switch (test_conn)
			{
			case -1:
				lblStatus.setForeground(Color.RED);
				lblStatus.setText("Username or password is empty");
				break;
			case 0:
				lblStatus.setForeground(Color.RED);
				lblStatus.setText("Invalid username or password");
				break;
			case 1:
				_conn.cclose();
				createDashBoard();
				break;
			default:
				break;
			}
			_conn.cclose();
		} else if (e.getSource() == btnRegister)
		{
			setVisible(false);
			new RegisterWindow();
			dispose();
		}
	}

	public static void main(String[] args)
	{
		new LoginWindow();
	}
}
