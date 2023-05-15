package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

public class  main
{
	public static void main(String[] args)
	{
		new SplashScr(2000);
		LoginWindow reg = new LoginWindow();
	}
}

class SplashScr extends JWindow {
    public SplashScr(int duration) {
    	ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/res/images/splash_agila.png")));
    	Image img = icon.getImage().getScaledInstance(
				icon.getIconWidth() / 2, icon.getIconHeight() / 2, Image.SCALE_SMOOTH);
    	icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        setBackground(new Color(0, 0, 0, 0)); // set transparent background
        getContentPane().add(label, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        	System.err.println(e);
        }
        setVisible(false);
    }
}
