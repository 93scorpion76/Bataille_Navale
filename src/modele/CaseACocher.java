package modele;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class CaseACocher  extends JButton{
	private String lienOn;
	private String lienOff;
	private Image image;
	
	
	public CaseACocher(String str){
		super(str);
		

		this.lienOn = "image/pret.png";
		this.lienOff = "image/no-pret.png"; 
		
		try {
			image = ImageIO.read(new File(lienOff));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){   
		  this.setContentAreaFilled(false);
		  this.setBorderPainted(false);
		  g.drawImage(image, 0, 0,this.getWidth(), this.getHeight(), this);
		  
	}
	
	public void changPret()
	{
		try {
			image = ImageIO.read(new File(lienOn));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	
	public void changNoPret()
	{
		try {
			image = ImageIO.read(new File(lienOff));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
}
