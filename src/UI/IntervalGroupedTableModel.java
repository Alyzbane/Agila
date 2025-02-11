package src.UI;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class IntervalGroupedTableModel extends AbstractTableModel
{
	private DefaultTableModel model;
	public int groupCount;
	private boolean evenDistribution;

	public IntervalGroupedTableModel(DefaultTableModel model, int groupCount, boolean evenDistribution)
	{
		this.model = model;
		this.groupCount = groupCount;
		this.evenDistribution = evenDistribution;
	}

	public void setEvenDistribution(boolean evenDistribution)
	{
		this.evenDistribution = evenDistribution;
	}

	@Override
	public int getRowCount()
	{
		return model.getRowCount();
	}

	@Override
	public int getColumnCount()
	{
		return model.getColumnCount() + 1; // add one for the group_number column
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return "group_number";
		} else
		{
			return model.getColumnName(column - 1);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0)
		{
			if (evenDistribution) //normal sort
			{
				int rowsPerGroup = (int) Math.ceil((double) getRowCount() / groupCount);
				return Math.min((rowIndex / rowsPerGroup) + 1, groupCount);
			} 
			else //random sort
			{
				return (rowIndex % groupCount) + 1;
			}
		} else
		{
			return model.getValueAt(rowIndex, columnIndex - 1);
		}
	}
}
