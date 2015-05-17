package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

import observer.Observable;
import observer.Observateur;
import modele.JTableModel;
import modele.PanneauColor;
import modele.SmallButton;
import connectors.Player;
import connectors.Room;

public class ListRoomView extends JFrame implements ActionListener, Observable, MouseInputListener{

	private int sizeWidth = 1366/2;
	private int sizeHeight = 725/2;
	
	private JTableModel tabModel;
	private JTable myJTable;
	
	private SmallButton createRoom;
	private JLabel welcomeLab;
	
	private PanneauColor pan;
	
	private String title[] = {"Numéro", "Nom de la room", "Nom du créateur", "Nombre de joueur"};
	
	//Création collection d'observateur
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	
	public ListRoomView(String namePlayer)
	{
		this.setTitle("Bataille Navale");
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon ImageIcon = new ImageIcon("image/icone.png");
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);
		
		
		Object data [][] = new Object[1][title.length];
		
		for(int i = 0; i < title.length; i++)
		{
			data[0][i] = "";
		}
		
		tabModel = new JTableModel(data, title);
		myJTable = new JTable(tabModel);
		myJTable.addMouseListener(this);
		JScrollPane tableau = new JScrollPane(myJTable);
		
		myJTable.setOpaque(false);
		tableau.setOpaque(false);
		
		createRoom = new SmallButton("Créer une bataille navale");
		createRoom.addActionListener(this);
		
		welcomeLab = new JLabel("Bienvenu Pirate : "+namePlayer);
		welcomeLab.setHorizontalAlignment(JLabel.CENTER);
		welcomeLab.setFont(new Font("Serif", Font.BOLD, 30));
		
		
		
		pan = new PanneauColor(1);
		pan.setLayout(new BorderLayout());
		
		pan.add(welcomeLab, BorderLayout.PAGE_START);
		pan.add(tableau, BorderLayout.CENTER);
		pan.add(createRoom, BorderLayout.PAGE_END);
		
		
		this.setContentPane(pan);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void refreshTable(ArrayList<Room> data)
	{
		tabModel.remplir(data);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == createRoom)
		{
			updateObservateur("create");
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
			if(appel.equals("create"))
					obs.update(appel, "");
			else
					obs.update("join", appel);
					
			}
	}
	

	@Override
	public void delObservateur() {
		// TODO Auto-generated method stub
		listObservateur = new ArrayList<Observateur>();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		if(myJTable.getSelectedRow() > -1)
		{
			String id = tabModel.getValueAt(myJTable.getSelectedRow(), 0).toString();
			updateObservateur(id);
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
