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


public class SmallButton extends JButton implements MouseListener{

	private String lienOn;
	private String lienOff;
	private Image imge;
	
	public SmallButton(String str){
		super(str);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Serif", Font.BOLD, 30));
		
		this.addMouseListener(this);

		this.lienOn = "image/smallButton_On.jpg";
		this.lienOff = "image/smallButton_Off.jpg"; 
		
		try {
			imge = ImageIO.read(new File(lienOff));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){   
		  this.setContentAreaFilled(false);
		  this.setBorderPainted(false);
		  g.drawImage(imge, 0, 0,this.getWidth(), this.getHeight(), this);
		  g.drawString(this.getText(), this.getWidth() / 2 - (this.getWidth() / 2 /4), (this.getHeight() / 2) + 5);
		  
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		try {
			imge = ImageIO.read(new File(lienOn));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		 try {
				imge = ImageIO.read(new File(lienOff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
