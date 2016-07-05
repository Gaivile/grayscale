import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;  
import java.awt.*;
import java.awt.image.BufferedImage;

public class Inverted {
	public static void main(String[] args)
	{
		// create a new object to get image source, make sure file is chosen
		Inverted inverted = new Inverted();
		String imgSrc = inverted.choose();
		if(imgSrc == null)
		{
			System.out.println("No file chosen!");
			return;
		}
		
		// create and open BufferedImage to map into an array of pixels
		BufferedImage img = null;
		try 
		{
		    img = ImageIO.read(new File(imgSrc));
		} 
		catch (IOException e) 
		{
			System.out.println("Could not open.");
		}
		
		// get width and height of an image
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		
		// loop over each pixel, get it's RGB value, calculate opposite color
		// and set new value at the same coordinates
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				Color c = new Color(img.getRGB(j, i));
  	            int red = 255 - (int)c.getRed();
  	            int green = 255 - (int)c.getGreen();
  	            int blue = 255 - (int)c.getBlue();
  	            Color opposite = new Color(red, green, blue);
  	            img.setRGB(j, i, opposite.getRGB());
			}
		}
		
		// open a new inverted image to display
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get and modify filename
		File f = new File(imgSrc);
		String fname = f.getName();
		String newName = "inverted-" + fname;
		
		// save inverted image with a new filename
		try
		{
			File ouptut = new File(newName);
	          ImageIO.write(img, "jpg", ouptut);
		}
		catch(IOException e)
		{
			System.out.println("Could not create.");
		}
	}
	
	// prompt user to choose a file, return absolute path
	public String choose()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			File selectedFile = fileChooser.getSelectedFile();
			String imgPath = selectedFile.getAbsolutePath();
			return imgPath;
		}
		return null;
	}
}
