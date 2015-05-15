package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import observer.Observable;
import observer.Observateur;
import connectors.Player;
import controlers.ThreadClient;

public class SalonView extends JFrame implements ActionListener, Observable{
	
	private JPanel rightPan;
	private JPanel leftPan;
	private JPanel centerPan;
	
	private JButton[]chooseBoat;
	
	private JLabel[]namePlayer;
	private JButton[] isReady;
	
	private JButton ready = new JButton("Prêt");
	private JButton exit = new JButton("Quitter");
	
	private static Player player;
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	
	public SalonView(Player player)
	{
		this.player = player;
		
		this.setTitle("Bataille Navale "+player.getId());
		this.setSize(1366/2, 725);
		//this.setSize(1366, 725);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		 */
		//this.Game.setHorizontalAlignment();
		chooseBoat = new JButton[16];
		namePlayer = new JLabel[4];
		isReady = new JButton[4];
		
		rightPan = new JPanel();
		leftPan = new JPanel();
		centerPan = new JPanel();
		
		GridLayout gl = new GridLayout(4,2);
		JPanel panPlayer = new JPanel();
		panPlayer.setLayout(gl);
		int tmp;
		for(int i = 0; i < 4; i++)
		{
			tmp = i+1;
			namePlayer[i] = new JLabel("Joueur "+tmp);
			isReady[i] = new JButton("x");
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
			chooseBoat[i] = new JButton(tmp+"");
			chooseBoat[i].addActionListener(this);
			rightPan.add(chooseBoat[i]);
		}
		
		ready.addActionListener(this);
		exit.addActionListener(this);
		
		gl = new GridLayout(3,1);
		leftPan.setLayout(gl);
		
		leftPan.add(exit);
		leftPan.add(panPlayer);
		leftPan.add(ready);
		
		gl = new GridLayout(1,2);
		centerPan.setLayout(gl);
		
		centerPan.add(leftPan);
		centerPan.add(rightPan);
		
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
			player.setReady(true);
			this.updateObservateur("choosePosition");
			//new GameView();
			//this.dispose();
		}
		else
		{
			for(int i = 0; i < 16; i++){
				if(event.getSource() == chooseBoat[i])
				{
					int number =Integer.parseInt(chooseBoat[i].getText());
					player.setPosBateau(number);
				}
			}
		
		}
	}
	
	public void setPlayerName(int position, String name)
	{
		namePlayer[position].setText(name);
	}
	
	public void setStatut(int position, Boolean status)
	{
		isReady[position].setText(status.toString());
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
