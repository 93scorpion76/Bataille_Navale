package modele;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanneauColor extends JPanel{
	
	
	public PanneauColor(int id)
	{
		
		switch(id)
		{
		case 1:
			setBackground(new Color(198, 156, 108));   
			break;
			
		case 2:
			setBackground(new Color(120, 198, 221));   
			break;
		}
		
	}

}
