package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import connectors.Player;
import observer.Observable;
import observer.Observateur;

public class GameView extends JFrame implements ActionListener, Observable{

	private JButton[]chooseCible;
	
	private JPanel leftPan;
	private JPanel rightPan;
	private JPanel centerPan;
	
	private JButton exit;
	private JTextArea text;
	//private JScrollPane scroll = new JScrollPane(tableau);
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	private Player player;
	
	public GameView(Player player)
	{
		this.setTitle("Bataille Navale : "+player.getNom());
		this.setSize(1366/2, 725);
		//this.setSize(1366, 725);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.player = player;
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		*/
		
		leftPan = new JPanel(new BorderLayout());
		rightPan = new JPanel();
		centerPan = new JPanel();
		text= new JTextArea(5,30);
		chooseCible=new JButton[16];
		
		//left 
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(380, 100));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		exit= new JButton("Quitter");
		exit.setPreferredSize(new Dimension(20, 200));
		exit.addActionListener(this);
		
		leftPan.add(exit, BorderLayout.PAGE_START);
		leftPan.add(scrollPane, BorderLayout.CENTER);
		
		//right
		GridLayout gl = new GridLayout(4,4);
		gl.setHgap(20);
		gl.setVgap(20);
		rightPan.setLayout(gl);
		
		int tmp;
		for(int i = 0; i < 16; i++)
		{
			tmp = i+1;
			chooseCible[i] = new JButton("Cible "+tmp);
			chooseCible[i].addActionListener(this);
			rightPan.add(chooseCible[i]);
		}
		
		
		gl = new GridLayout(1,2);
		centerPan.setLayout(gl);
		
		centerPan.add(leftPan);
		centerPan.add(rightPan);
		
		this.setContentPane(centerPan);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public Player getPlayer() {
		return player;
	}

	public void enabledAllButton()
	{
		for(int i = 0; i < 16; i++)
		{
			chooseCible[i].setEnabled(!chooseCible[i].isEnabled());
		}
	}
	
	public void enabledAllButtonIDPlayer(int id)
	{
		if(id == player.getId()){
			for(int i = 0; i < 16; i++)
			{
				chooseCible[i].setEnabled(!chooseCible[i].isEnabled());
			}
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == exit)
		{
			updateObservateur("exit");
		}
		else
		{
			for(int i =0; i < this.chooseCible.length; i++)
			{
				if(event.getSource() == this.chooseCible[i])
				{
					i++;
					updateObservateur(""+i);
					i = 16;
				}
			}
		}
	}

	@Override
	public void addObservateur(Observateur obs) {
		// TODO Auto-generated method stub
		listObservateur.add(obs);
	}

	@Override
	public void updateObservateur(String appel) {
		// TODO Auto-generated method stub
		for(Observateur obs : this.listObservateur ){
			if(appel.equals("exit"))
					obs.update(appel, "");
			else	
					obs.update("shoot", appel);
			}
	}

	@Override
	public void delObservateur() {
		// TODO Auto-generated method stub
		listObservateur = new ArrayList<Observateur>();
	}

}
