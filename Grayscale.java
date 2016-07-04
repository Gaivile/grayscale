import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;  
import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale {
	public static void main(String[] args)
	{
		// create a new object to get image source, make sure file is chosen
		Grayscale Gray = new Grayscale();
		String imgSrc = Gray.choose();
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
		
		// loop over each pixel, get it's RGB value, calculate average for
		// a grayscale and add set new value at the same coordinates
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				Color c = new Color(img.getRGB(j, i));
	            int red = (int)c.getRed();
	            int green = (int)c.getGreen();
	            int blue = (int)c.getBlue();
	            int average = (red + green + blue)/3;
	            Color gray = new Color(average, average, average);
	            img.setRGB(j, i, gray.getRGB());
			}
		}
		
		// open a new grayscale image to display
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get and modify filename
		File f = new File(imgSrc);
		String fname = f.getName();
		String newName = "gray-" + fname;
		
		// save grayscale image with a new filename
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
