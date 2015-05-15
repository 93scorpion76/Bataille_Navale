package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlers.ConnexionControl;

public class ConnexionView extends JFrame implements ActionListener{
	
	private JLabel nameLab = new JLabel("Votre pseudo : ");
	private JLabel Game = new JLabel("Bienvenu dans la bataille navale");
	
	private JTextField nameFT = new JTextField("");
	
	private JButton connexionGame = new JButton("Gooo !!!");
	
	public ConnexionView()
	{
		this.setTitle("Bataille Navale");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		 */
		this.Game.setHorizontalAlignment(JLabel.CENTER);
		nameFT.setPreferredSize(new Dimension(200, 20));
		JPanel pan = new JPanel();
		pan.add(nameLab);
		pan.add(nameFT);
		
		connexionGame.addActionListener(this);
		
		this.getContentPane().add(this.Game, BorderLayout.NORTH);
		this.getContentPane().add(pan, BorderLayout.CENTER);
		this.getContentPane().add(this.connexionGame, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == connexionGame)
		{
			if(nameFT.getText() != "" && nameFT.getText().length() <= 10 && nameFT.getText().length() >=2)
			{
				this.dispose();
				new ConnexionControl(nameFT.getText());
			}
		}
	}
	
	
}
