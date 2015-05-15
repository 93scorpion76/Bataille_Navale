package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import modele.Panneau;
import modele.SmallButton;
import controlers.ConnexionControl;

public class ConnexionView extends JFrame implements ActionListener, KeyListener{
	
	private JLabel Game = new JLabel("Bienvenu matelos ! Prépare toi à prendre le large");
	
	private JTextField nameFT = new JTextField("");
	
	private SmallButton connexionGame = new SmallButton("Levez les voiles !!!");
	
	public ConnexionView()
	{
		this.setTitle("Bataille Navale");
		this.setSize(800, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		 */
		
		nameFT.setPreferredSize(new Dimension(200, 40));
		JPanel pan = new JPanel();
		pan.add(nameFT);
		
		connexionGame.addActionListener(this);
		
		Panneau fond = new Panneau();
		fond.add(this.Game);
		fond.add(pan);
		fond.add(this.connexionGame);
		
		this.Game.setBounds(0,0,793,40);
		this.Game.setHorizontalAlignment(JLabel.CENTER);
		this.Game.setFont(new Font("Serif", Font.BOLD, 30));
		
		//this.Game.setHorizontalTextPosition(JLabel.CENTER);
		this.connexionGame.setBounds(0,543,793,40);
		this.nameFT.setBounds(352,400,100,80);
		this.nameFT.setBackground(Color.getHSBColor(187, 93, 52));
		this.nameFT.setFont(new Font("Serif", Font.BOLD, 36));
		this.nameFT.addKeyListener(this);
		this.nameFT.requestFocus();
		
		//Compound borders
		Border compound;
		Border redline = BorderFactory.createLineBorder(Color.MAGENTA);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		//This creates a nice frame.
		compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		this.nameFT.setBorder(compound);
		
		pan.setBounds(0, 400, 800, 40);
		pan.setOpaque(false);
		
	
		
		this.setContentPane(fond);
		this.getContentPane().setLayout(null);
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getExtendedKeyCode() == 10)
		{
			if(nameFT.getText() != "" && nameFT.getText().length() <= 10 && nameFT.getText().length() >=2)
			{
				this.dispose();
				new ConnexionControl(nameFT.getText());
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
