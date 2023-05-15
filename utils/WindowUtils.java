package src.utils;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

public class WindowUtils 
{
	private String filepath; 
	private JFileChooser fileChooser;
	public WindowUtils()
	{
    	fileChooser = new JFileChooser();
   		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Portable Document File (*.pdf)", "pdf"));
   		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx"));
   		fileChooser.setAcceptAllFileFilterUsed(true);

		filepath = "";
	}
    public ImageIcon getImage(String link) {
        ImageIcon img = null;
        try {
            img = new ImageIcon(getClass().getResource(link));
            if (img.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image not found at path: " + link);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }
    
    public int[] getWindowSize()
    {
    	int winSize[] = new int[2];
    	
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Dimension dim = toolkit.getScreenSize();
    	winSize[0] = (int) dim.getWidth();
    	winSize[1] = (int) dim.getHeight();
    	System.out.println("Screen width: " + winSize[0]);
        System.out.println("Screen height: " + winSize[1]);
    	return winSize;
    }
    
    
    public static void switchPanel(JPanel cardPanel, String panelName) {
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
	    cardLayout.show(cardPanel, panelName);
	}
    
    public void errStatLabel(JLabel lblErrorStatus, boolean status)
    {
    	lblErrorStatus.setVisible(status);
    	if(status)
    	{
    		lblErrorStatus.setForeground(Color.RED);
    		lblErrorStatus.setText("Please fill up all of the fields");
    	}
    }	
  
	public boolean saveFile()
	{
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			// getting the save file directory
			File selectedFile = fileChooser.getSelectedFile();
			filepath = selectedFile.getAbsolutePath();

			// getting the choosen file filter
			FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
			String[] extensions = filter.getExtensions();
			for(var c : extensions)
				System.out.println(" the extension " + c);

			System.out.println("Filepath " + filepath); 

			if (extensions.length > 0)
			{
				String extension = extensions[0];
				if (!filepath.endsWith("." + extension))
					filepath += "." + extension;
			}
			return true;
		}
		return false;
	}

	public String fpathName()
	{
		String extension = filepath.substring(filepath.lastIndexOf(".") + 1);
		System.out.println("Filepath " + filepath + " the extension " + extension);
    	if (extension.equals("pdf") || extension.equals("xlsx")) 
    	{
    		return filepath;
    	}
    	return extension;
	}
	public void removeFocusPainted(JPanel panel)
	{
		for(Component comp : panel.getComponents())
		{
			if(comp instanceof JCheckBox)
			{
				JCheckBox cbox = (JCheckBox) comp;
				cbox.setFocusPainted(false);
			}
			else if(comp instanceof JRadioButton)
			{
				JRadioButton rd = (JRadioButton) comp;
				rd.setFocusPainted(false);
			}
			else if(comp instanceof JPanel)
			{
				JPanel pan = (JPanel) comp;
				removeFocusPainted(pan);
			}
		}
	}
	public String fileExtension()
	{
		String fileName = filepath;
;
		int dotIndex = fileName.lastIndexOf(".");
		String extension = "";
		if (dotIndex != -1 && dotIndex < fileName.length() - 1)
		    extension = fileName.substring(dotIndex + 1);
		System.out.println("filename " + fileName);
		return extension;
	}
}
