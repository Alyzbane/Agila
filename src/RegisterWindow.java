package src;
import java.awt.*;
import java.awt.event.*;

import src.database.*;
import src.utils.WindowUtils;
import src.UI.PassFieldFocusAdapter;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class RegisterWindow extends JFrame implements ActionListener {
    private JLabel lblStatus;
    private JTextField txtUsername;
    private JButton btnRegister, btnCancel;
    private JPanel panel;
    
    
    private DbConn _conn;
    private JLabel lblIcon;
    private WindowUtils utils;
    private ImageIcon icon;
    private Image img;
    private JPanel panelPassword;
    private JPasswordField txtConfirmPassword;
    private JPasswordField txtPassword;
    private JLabel lblRegister;
    

    public RegisterWindow() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(485, 340);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(null);
        utils = new WindowUtils(); // instance of utility handler


        btnRegister = new JButton("Register");
        btnRegister.setForeground(new Color(245, 255, 250));
        btnRegister.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnRegister.setBackground(new Color(255, 140, 0));
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 12));
        btnRegister.setBounds(180, 207, 80, 30);
        btnRegister.setFocusPainted(false);
        btnRegister.addActionListener(this);

        btnCancel = new JButton("Back");
        btnCancel.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCancel.setBounds(120, 207, 50, 30);
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(this);

        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setBounds(40, 243, 220, 30);
        lblStatus.setFocusable(true);
        lblStatus.requestFocusInWindow();
        
        
        ImageIcon logo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/logo_agila.png")));
		Image img = logo.getImage().getScaledInstance(
				25, 25, Image.SCALE_SMOOTH);
		setIconImage(img); 
        //adding image to icon
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/logo_agila.png")));
		img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);       

		lblIcon = new JLabel(icon);
		lblIcon.setBounds(311, 91, 120, 115);
		lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcon.setBackground(new Color(204, 102, 0));
		lblIcon.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		panel.add(lblIcon);

              
        //adding all objects into panel
        panel.add(lblStatus);
        panel.add(btnRegister);
        panel.add(btnCancel);
 
        JPanel panelUser = new JPanel();
        panelUser.setBackground(new Color(255, 255, 255));
        panelUser.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelUser.setBounds(30, 89, 230, 30);
        panel.add(panelUser);
        panel.add(panelUser);
                panelUser.setLayout(null);
        
                txtUsername = new JTextField();
                txtUsername.setBounds(10, 8, 215, 15);
                panelUser.add(txtUsername);
                txtUsername.setToolTipText("Username");
                txtUsername.addFocusListener(new FocusAdapter() {
                	@Override
                	public void focusGained(FocusEvent e) {
                		if(txtUsername.getText().equals("Username"))
                			txtUsername.setText("");
                		else
                			txtUsername.selectAll();
                	}
                	@Override
                	public void focusLost(FocusEvent e) {
                		if(txtUsername.getText().equals(""))
                			txtUsername.setText("Username");
                	}
                });
                txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
                txtUsername.setText("Username");
                txtUsername.setBorder(null);
        
        panelPassword = new JPanel();
        panelPassword.setBackground(new Color(255, 255, 255));
        panelPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelPassword.setBounds(30, 125, 230, 30);
        panel.add(panelPassword);
        panel.add(panelPassword);
                panelPassword.setLayout(null);
        
                txtPassword = new JPasswordField();
                txtPassword.setBounds(10, 8, 215, 15);
                panelPassword.add(txtPassword);
                txtPassword.setToolTipText("Password");
                txtPassword.setText("Password");
                txtPassword.setEchoChar((char)0);
                txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
                txtPassword.setBorder(null);
                txtPassword.addFocusListener(new PassFieldFocusAdapter(txtPassword, "Password"));
        
        JPanel panelPassword_1 = new JPanel();
        panelPassword_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelPassword_1.setBackground(Color.WHITE);
        panelPassword_1.setBounds(30, 166, 230, 30);
        panel.add(panelPassword_1);
        panel.add(panelPassword_1);
        panelPassword_1.setLayout(null);
        
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(10, 8, 215, 15);
        panelPassword_1.add(txtConfirmPassword);
        txtConfirmPassword.setToolTipText("Confirm Password");
        txtConfirmPassword.setText("Confirm Password");
        txtConfirmPassword.setBorder(null);
        txtConfirmPassword.setEchoChar((char)0);
        txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtConfirmPassword.addFocusListener(new PassFieldFocusAdapter(txtConfirmPassword, "Confirm Password"));
        
        lblRegister = new JLabel("Register");
        lblRegister.setForeground(new Color(0, 0, 0));
        lblRegister.setFont(new Font("Arial", Font.PLAIN, 30));
        lblRegister.setBounds(32, 11, 228, 77);
        lblRegister.setFocusable(true);
        lblRegister.requestFocusInWindow();

        panel.add(lblRegister);

        getContentPane().add(panel);
        getContentPane().requestFocusInWindow();
        setVisible(true);
    }
    
    
   public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnRegister) {
        	//connection to database
        	_conn = new DbConn();
        	_conn.oopen();
         
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            int reg = _conn.registerUser(username, password); 
            if (password.equals(confirmPassword)) 
            {
            	switch(reg)
            	{
            		case 1:
            	       JOptionPane.showMessageDialog(this, "Registration successful");
            	       setVisible(false);
                       new LoginWindow();
                       dispose();
                       break;

            		case 0:
                       lblStatus.setForeground(Color.RED);
                       lblStatus.setText(username + " already exist");
                       break;

                	case -1:
                       lblStatus.setForeground(Color.RED);
                       lblStatus.setText("Error registering the user");
                       break;

                    default:
                       lblStatus.setForeground(Color.RED);
                       lblStatus.setText("Error registering the user");
                       break;
            	}
            } 
            else if(confirmPassword.equals("Confirm Password") && reg == -1)
            {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Username or password is empty");
           	
            }
            else 
            {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Passwords do not match");
            }
            _conn.cclose();
        } 
        //going back to previous login window 
        else if (e.getSource() == btnCancel) 
        {
        	setVisible(false);
        	new LoginWindow();
        	dispose();
        }
     }   	

    public static void main(String[] args) {
        new RegisterWindow();
        
    }
}