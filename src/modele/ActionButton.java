package modele;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class ActionButton  extends JButton implements MouseListener{

	private boolean chooseB;
	private String choose;
	
	private boolean cloudB;
	private String cloudOn;
	private String cloudOff;
	
	private boolean touchB;
	private String touch;

	private Image imge;
	
	private int position;
	
	private static boolean isChoose = false;
	
	public ActionButton(String str){
		super(str);
		

		this.position = Integer.parseInt(str);
		chooseB= false;
		cloudB = true;
		touchB = false;
		
		this.addMouseListener(this);

		this.choose = "image/boat.png";
		this.cloudOff = "image/nuage_Off.png";
		this.cloudOn = "image/nuage_On.png";
		this.touch = "image/debris.png"; 
		
		try {
			imge = ImageIO.read(new File(cloudOff));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isChoose() {
		return isChoose;
	}

	public static void setChoose(boolean isChoose) {
		ActionButton.isChoose = isChoose;
	}
	
	public void changeCloud()
	{
		if(!isChoose) {
			chooseB= false;
			cloudB = true;
			touchB = false;
		
			try {
				imge = ImageIO.read(new File(cloudOff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void changeBoat()
	{
		if(!isChoose) {
			chooseB= true;
			cloudB = false;
			touchB = false;
		
			try {
				imge = ImageIO.read(new File(choose));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void changeInvisible()
	{
		setVisible(false);
	}
	
	public boolean isCloudB() {
		return cloudB;
	}

	public void changeDebris()
	{
		chooseB= false;
		cloudB = false;
		touchB = true;
		
		try {
			imge = ImageIO.read(new File(touch));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){   
		  this.setContentAreaFilled(false);
		  this.setBorderPainted(false);
		  g.drawImage(imge, 0, 0,this.getWidth(), this.getHeight(), this); 
	}
	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(cloudB && !isChoose)
		{
			try {
				imge = ImageIO.read(new File(cloudOn));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(cloudB && !isChoose)
		{
			try {
				imge = ImageIO.read(new File(cloudOff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
