package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.ActionButton;
import modele.CaseACocher;
import modele.PanneauColor;
import modele.SmallButton;
import observer.Observable;
import observer.Observateur;
import connectors.Player;
import controlers.ThreadClient;

public class RoomView extends JFrame implements ActionListener, Observable{
	
	private PanneauColor rightPan;
	private PanneauColor leftPan;
	private JPanel centerPan;
	
	private ActionButton[]chooseBoat;
	
	private JLabel nbPlayerMax;
	private JLabel[]namePlayer;
	private CaseACocher[] isReady;
	
	private SmallButton ready = new SmallButton("Prêt");
	private SmallButton exit = new SmallButton("Quitter");
	
	private static Player player;
	
	private int sizeWidth = 1366/2;
	private int sizeHeight = 725/2;
	
	private int positionPlayer;
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	private int nbrPlayer;
	
	public RoomView(Player player)
	{
		this.player = player;

		this.setTitle("Bataille Navale ");
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		ImageIcon ImageIcon = new ImageIcon("image/icone.png");
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);
		 
		
		chooseBoat = new ActionButton[16];
		namePlayer = new JLabel[4];
		isReady = new CaseACocher[4];
		
		rightPan = new PanneauColor(2);
		leftPan = new PanneauColor(1);
		
		centerPan = new JPanel();
		
		
		GridLayout gl = new GridLayout(4,2);
		JPanel panPlayer = new JPanel();
		panPlayer.setLayout(gl);
		
		
		for(int i = 0; i < 4; i++)
		{
			namePlayer[i] = new JLabel("");
			
			namePlayer[i].setFont(new Font("Serif", Font.BOLD, 30));
			
			isReady[i] = new CaseACocher("");
			namePlayer[i].setHorizontalAlignment(JLabel.CENTER);
			isReady[i].setVisible(false);
			panPlayer.add(namePlayer[i]);
			panPlayer.add(isReady[i]);
		}
		
		
		gl = new GridLayout(4,4);
		gl.setHgap(20);
		gl.setVgap(20);
		rightPan.setLayout(gl);
		
		int tmp;
		for(int i = 0; i < 16; i++)
		{
			tmp = i+1;
			chooseBoat[i] = new ActionButton(""+tmp);
			chooseBoat[i].addActionListener(this);
			rightPan.add(chooseBoat[i]);
		}
		
		ActionButton.setChoose(false);
		ready.addActionListener(this);
		exit.addActionListener(this);
		
		gl = new GridLayout(3,1);
		leftPan.setLayout(gl);
		leftPan.add(ready);
		leftPan.add(panPlayer);
		exit.setPreferredSize(new Dimension(sizeWidth/2,sizeHeight/10));
		JPanel panTmp =  new JPanel(new BorderLayout());
		
		nbPlayerMax = new JLabel();
		nbPlayerMax.setFont(new Font("Serif", Font.BOLD, 15));
		nbPlayerMax.setHorizontalAlignment(JLabel.CENTER);
		
		panTmp.add(nbPlayerMax, BorderLayout.CENTER);
		panTmp.add(exit, BorderLayout.SOUTH);
		leftPan.add(panTmp);
		
		gl = new GridLayout(1,2);
		centerPan.setLayout(gl);
		
		centerPan.add(leftPan);
		centerPan.add(rightPan);
		panPlayer.setOpaque(false);
		centerPan.setOpaque(false);
		panTmp.setOpaque(false);
		
		this.setContentPane(centerPan);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == exit)
		{
			this.updateObservateur("exit");
		}
		else if(event.getSource() == ready)
		{
			if(player.getPosBateau() != 0){
				ActionButton.setChoose(true);
				player.setReady(true);
				ready.setEnabled(false);
				System.out.println("Position choisi : "+player.getPosBateau());
				this.updateObservateur("choosePosition");
			}
		}
		else
		{
			for(int i = 0; i < 16; i++){
				if(event.getSource() == chooseBoat[i])
				{
					for(int j = 0; j < 16 ; j++)
					{
						if(!chooseBoat[j].isCloudB())
							chooseBoat[j].changeCloud();
					}
					
					chooseBoat[i].changeBoat();
					player.setPosBateau(chooseBoat[i].getPosition());
				}
			}
		
		}
	}
	
	public void setNbPlayer(int nb)
	{
		if(nb == 0)
			nbPlayerMax.setText("Selection position bateau en cours");
		else
			nbPlayerMax.setText("Nombre de pirates attendus "+nb);
	}
	
	public void setPlayerName(int position, Player playerName)
	{
		if(player.getId() == playerName.getId()){
			positionPlayer = position;
			namePlayer[positionPlayer].setForeground(new Color(145, 10, 10));
		}
		
		namePlayer[position].setText(playerName.getNom());
		isReady[position].setVisible(true);
	}
	
	public void setStatut(int position, Boolean status)
	{
		isReady[position].changPret();
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
			switch(appel)
			{
				case "exit" :
					obs.update(appel, "");
					break;
			
				case "choosePosition" :
					obs.update(appel, player.getPosBateau()+"");
					break;
			}
		}
	}

	@Override
	public void delObservateur() {
		// TODO Auto-generated method stub
		listObservateur = new ArrayList<Observateur>();
	}



	

}
