package src.UI;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import src.database.*;

public class TableDataHandler
{
	DbConn _conn;

	public TableDataHandler()
	{
		_conn = new DbConn();
	}

	public void updateTable(JTable tableGrid, TableColumnAppearance thandler)
	{
		_conn.oopen();
		_conn.data_fetch();
		tableGrid.setModel(_conn.getModel());
		thandler.updateTable(tableGrid);
		thandler.hideColumn("First Name");
		thandler.hideColumn("Middle Name");
		thandler.hideColumn("Middle Initial");
		thandler.hideColumn("Last Name");
		thandler.hideColumn("Deceased");
		thandler.hideColumn("Birthday");
		_conn.cclose();
	}

	public void resetPanelCbox(JPanel panel)
	{

		for (Component comp : panel.getComponents())
		{
			if (comp instanceof JCheckBox)
			{
				JCheckBox cbox = (JCheckBox) comp;
				cbox.setSelected(false);
			}
			if (comp instanceof JPanel)
			{
				JPanel ipanel = (JPanel) comp;
				resetPanelCbox(ipanel);
			}
		}

	}

	public boolean verifyCheckboxes(JPanel panelFilter)
	//checks if atleast one checkbox is selected
	{
		for (Component comp : panelFilter.getComponents())
		{
			if (comp instanceof JCheckBox)
			{
				JCheckBox cbox = (JCheckBox) comp;
				if (cbox.isSelected())
					return true;
			} else if (comp instanceof JPanel)
			{
				//recursion if theres a panel inside this
				//panel and theres a checkbox selected inside
				JPanel panel = (JPanel) comp;
				if (verifyCheckboxes(panel))
					return true;
			}
		}
		return false; //no checkbox were  selected
	}

}
