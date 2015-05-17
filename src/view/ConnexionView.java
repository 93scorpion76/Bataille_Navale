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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import modele.Panneau;
import modele.SmallButton;
import controlers.ConnexionControl;
import controlers.ListControl;
import controlers.ListRoomControl;

public class ConnexionView extends JFrame implements ActionListener, KeyListener, MouseListener{
	
	private JLabel Game = new JLabel("Bienvenu matelos ! Pr�pare toi � prendre le large");
	
	private JTextField nameFT = new JTextField("Nom du navire");
	
	private SmallButton connexionGame = new SmallButton("Levez les voiles !!!");
	
	public ConnexionView()
	{
		this.setTitle("Bataille Navale");
		this.setSize(800, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
			ImageIcon ImageIcon = new ImageIcon("image/icone.png");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		 
		
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
		this.nameFT.setFont(new Font("Serif", Font.BOLD, 20));
		this.nameFT.addKeyListener(this);
		this.nameFT.addMouseListener(this);
		
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
				new ListControl(nameFT.getText());
			}
			else
				JOptionPane.showMessageDialog(null, "Le nom de votre navire doit �tre compris entre 2 et 10 caract�res.", "Erreur : Nom de navire", JOptionPane.ERROR_MESSAGE);
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
				//new ListRoomControl(nameFT.getText());
				new ListControl(nameFT.getText());
			}
			else
				JOptionPane.showMessageDialog(null, "Le nom de votre navire doit �tre compris entre 2 et 10 caract�res.", "Erreur : Nom de navire", JOptionPane.ERROR_MESSAGE);
		}
		
		if(nameFT.getText().equals("Nom du navire"))
		{
			nameFT.setText("");
			this.nameFT.setFont(new Font("Serif", Font.BOLD, 36));
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

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == nameFT)
		{
			if(nameFT.getText().equals("Nom du navire")){
				nameFT.setText("");
				this.nameFT.setFont(new Font("Serif", Font.BOLD, 36));
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
