package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import observer.Observable;
import observer.Observateur;
import modele.PanneauColor;
import modele.SmallButton;
import connectors.Player;
import connectors.Room;

public class ListRoomView extends JFrame implements ActionListener, Observable{

	private int sizeWidth = 1366/2;
	private int sizeHeight = 725;
	
	private JTable monJTable;
	private SmallButton createRoom;
	private SmallButton joinRoom;
	
	private PanneauColor pan;
	
	private String title[] = {"Numéro", "Nom de la room", "Nom du créateur", "Nombre de joueur"};
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	
	public ListRoomView()
	{
		this.setTitle("Bataille Navale : ");
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* image
			ImageIcon ImageIcon = new ImageIcon("icone.jpg");
			Image Image = ImageIcon.getImage();
			this.setIconImage(Image);
		*/
		
		Object data [][] = new Object[1][title.length];
		monJTable = new JTable(data, title);
		JScrollPane tableau = new JScrollPane(monJTable);
		
		monJTable.setOpaque(false);
		tableau.setOpaque(false);
		
		createRoom = new SmallButton("Créer un salon");
		createRoom.addActionListener(this);
		
		joinRoom = new SmallButton("Rejoindre la salle");
		joinRoom.addActionListener(this);
		
		pan = new PanneauColor(1);
		pan.setLayout(new BorderLayout());
		
		pan.add(joinRoom, BorderLayout.PAGE_START);
		pan.add(tableau, BorderLayout.CENTER);
		pan.add(createRoom, BorderLayout.PAGE_END);
		
		this.setContentPane(pan);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void refreshTable(ArrayList<Room> data)
	{
		monJTable.removeAll();
		
		Object[][] dataRoom = new Object[data.size()][title.length];
		System.out.println("ici :"+data.size());
		for(int i = 0; i < data.size(); i++)
		{
			dataRoom[i][0] = data.get(i).getIdRoom();
			dataRoom[i][1] = data.get(i).getName();
			dataRoom[i][2] = data.get(i).getCreator();
			dataRoom[i][3] = data.get(i).getNbPlayerMax();
		}
		
		pan = new PanneauColor(1);
		pan.setLayout(new BorderLayout());
		
		monJTable = new JTable(dataRoom, title);
		JScrollPane tableau = new JScrollPane(monJTable);

		pan.add(joinRoom, BorderLayout.PAGE_START);
		pan.add(tableau, BorderLayout.CENTER);
		pan.add(createRoom, BorderLayout.PAGE_END);
		
		this.setContentPane(pan);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == createRoom)
		{
			updateObservateur("create");
		}
		else if(event.getSource() == joinRoom)
		{
			updateObservateur("join");
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
			switch(appel)
			{
				case "create" :
					obs.update(appel, "");
					break;
			
				case "join" :
					obs.update(appel, "1");
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
