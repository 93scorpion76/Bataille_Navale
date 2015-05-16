package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	
	private JLabel[]namePlayer;
	private CaseACocher[] isReady;
	
	private SmallButton ready = new SmallButton("Prêt");
	private SmallButton exit = new SmallButton("Quitter");
	
	private static Player player;
	
	private int sizeWidth = 1366/2;
	private int sizeHeight = 725;
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	
	public RoomView(Player player)
	{
		this.player = player;
		
		this.setTitle("Bataille Navale "+player.getId());
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		 */
		//this.Game.setHorizontalAlignment();
		chooseBoat = new ActionButton[16];
		namePlayer = new JLabel[4];
		isReady = new CaseACocher[4];
		
		rightPan = new PanneauColor(2);
		leftPan = new PanneauColor(1);
		
		centerPan = new JPanel();
		
		
		GridLayout gl = new GridLayout(4,2);
		JPanel panPlayer = new JPanel();
		panPlayer.setLayout(gl);
		
		int tmp;
		for(int i = 0; i < 4; i++)
		{
			tmp = i+1;
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
		exit.setPreferredSize(new Dimension(sizeWidth/2,sizeHeight/15));
		JPanel panTmp =  new JPanel(new BorderLayout());
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
			ActionButton.setChoose(true);
			player.setReady(true);
			System.out.print("Position choisi : "+player.getPosBateau());
			this.updateObservateur("choosePosition");
			//new GameView();
			//this.dispose();
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
	
	public void setPlayerName(int position, String name)
	{
		namePlayer[position].setText(name);
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
