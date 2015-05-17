package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import modele.ActionButton;
import modele.PanneauColor;
import modele.SmallButton;
import connectors.Player;
import observer.Observable;
import observer.Observateur;

public class GameView extends JFrame implements ActionListener, Observable{

	private ActionButton[]chooseCible;
	
	private PanneauColor leftPan;
	private PanneauColor rightPan;
	private JPanel centerPan;
	
	private JLabel tourLab;
	
	private SmallButton exit;
	private JTextArea text;
	//private JScrollPane scroll = new JScrollPane(tableau);
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	private Player player;
	
	private int sizeWidth = 1366/2;
	private int sizeHeight = 725/2;
	
	public GameView(Player player)
	{
		this.setTitle("Bataille Navale : "+player.getNom()+" - "+player.getId());
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		this.player = player;
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		*/
		
		leftPan = new PanneauColor(1);
		leftPan.setLayout(new BorderLayout());
		rightPan = new PanneauColor(2);
		centerPan = new JPanel();
		text= new JTextArea(5,30);
		chooseCible=new ActionButton[16];
	
		text.setFont(new Font("Serif", Font.BOLD, 15));
		tourLab = new JLabel("");
		tourLab.setFont(new Font("Serif", Font.BOLD, 20));
		//left 
		
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setPreferredSize(new Dimension(380, 100));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		

		text.setOpaque(false);
		scrollPane.setOpaque(false);
		
		exit = new SmallButton("Quitter");
		exit.setPreferredSize(new Dimension(sizeWidth/2,sizeHeight/15));
		JPanel panTmp =  new JPanel(new BorderLayout());
		panTmp.add(exit, BorderLayout.SOUTH);
		exit.addActionListener(this);
		
		leftPan.add(tourLab, BorderLayout.PAGE_START);
		leftPan.add(scrollPane, BorderLayout.CENTER);
		leftPan.add(exit, BorderLayout.PAGE_END);
		
		//right
		GridLayout gl = new GridLayout(4,4);
		gl.setHgap(20);
		gl.setVgap(20);
		rightPan.setLayout(gl);

		ActionButton.setChoose(false);
		int tmp;
		for(int i = 0; i < 16; i++)
		{
			tmp = i+1;
			chooseCible[i] = new ActionButton(""+tmp);
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
	
	public JLabel getTourLab() {
		return this.tourLab;
	}

	public Player getPlayer() {
		return player;
	}

	public void enabledAllButton()
	{
		for(int i = 0; i < 16; i++)
		{
			chooseCible[i].setEnabled(false);
		}
	}
	
	public void enabledAllButtonIDPlayer(int id)
	{
		if(id == player.getId()){
			for(int i = 0; i < 16; i++)
			{
				chooseCible[i].setEnabled(true);
			}
		}
	}
	
	public boolean getButtonEnabled()
	{
		return chooseCible[0].isEnabled();
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
					updateObservateur(""+chooseCible[i].getPosition());
					i = 16;
				}
			}
		}
	}

	public void addMessage(String message)
	{
		text.append(message);
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

	public ActionButton getActionButton(int id)
	{
		int tmp = id-1;
		return chooseCible[tmp];
	}
	
	public void Observateur(int[] boat, int[]debris)
	{
		for(int i = 0; i < 16; i++)
		{
			if(i+1 == boat[i])
			{
				chooseCible[i].changeBoat();
			}
			else if(i+1 == debris[i])
			{
				chooseCible[i].changeDebris();
			}
			else
			{
				chooseCible[i].changeInvisible();
			}
		}
	}
}
