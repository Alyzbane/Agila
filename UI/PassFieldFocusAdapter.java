package src.UI;
import javax.swing.*;
import java.awt.event.*;

public class PassFieldFocusAdapter extends FocusAdapter 
{

	  private JPasswordField passwordField;
	  private String defaultText;

	    public PassFieldFocusAdapter(JPasswordField passwordField, String password) {
	        this.passwordField = passwordField;
	        this.defaultText  = password;
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	        if (new String(passwordField.getPassword()).equals(defaultText))
	        {
	            passwordField.setEchoChar('●');
	            passwordField.setText("");
	        }
	    }

	    @Override
	    public void focusLost(FocusEvent e) 
	    {
	       if (new String(passwordField.getPassword()).equals("")) 
	       {
	            passwordField.setEchoChar((char) 0);
	            passwordField.setText(defaultText);
	       }
	    }
	    
	    
}
