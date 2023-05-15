package src.UI;

import javax.swing.*;
import javax.swing.text.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.awt.Color;
import java.text.*;

public class TxtLogger {
	private JTextArea log_pane;
	private String cur_dt;
	private String cur_time;
	
	
	public TxtLogger(JTextArea log_pane)
	{
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
		this.log_pane = log_pane;
		cur_dt = curDateTime();
		cur_time =  curTime();
	}
	
	private String curDateTime()
	{
		DateTimeFormatter formattedDT = 
				DateTimeFormatter.ofPattern("yyyy-MM-dd - hh:mm:ss a");
		
		//get the cur DT 
		LocalDateTime curDateTime =  LocalDateTime.now();
		
		return curDateTime.format(formattedDT);
	}
	private String curTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		dateFormat.setTimeZone(TimeZone.getDefault());
		return  dateFormat.format(new Date());
	}

	
	
	public void keyLogger (String status, String what, String endStatus)
	{
		Document doc = log_pane.getDocument();
		String cur_text = null;
		try
		{
			cur_text = doc.getText(0, doc.getLength());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String log_msg = cur_time + " " + status + "  [ " + what + " ] " +
						endStatus + "\n";
		String new_text = cur_text + log_msg;
		
		log_pane.setText(new_text);
	}
}
