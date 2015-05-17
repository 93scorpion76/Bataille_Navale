package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.Client;
import connectors.Player;
import controlers.ConnexionControl;
import controlers.ListControl;
import controlers.ListRoomControl;
import modele.PanneauColor;
import modele.SmallButton;

public class CreateView extends JFrame implements ActionListener{

	private int sizeWidth = 1366/2;
	private int sizeHeight = 725/2;
	
	private SmallButton back;
	private SmallButton create;
	
	private JLabel welcomeLab;
	private JLabel nameRoomLab;
	private JLabel personneMaxLab;
	
	private JTextField nameRoomTF;
	
	private JRadioButton nbr2;
	private JRadioButton nbr3;
	private JRadioButton nbr4;
	private int nbrChoose;
	
	private String namePlayer;
	private Client cli;
	
	public CreateView(String namePlayer, Client cli)
	{
		this.setTitle("Bataille Navale");
		this.setSize(sizeWidth, sizeHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		ImageIcon ImageIcon = new ImageIcon("image/icone.png");
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);
		 
		
		this.namePlayer = namePlayer;
		this.cli = cli;
		
		//Initialisation button
		back = new SmallButton("Retour");
		back.setPreferredSize(new Dimension(200, 40));
		create = new SmallButton("Créer la bataille");
		back.addActionListener(this);
		create.addActionListener(this);
		
		//Initialisation label
		welcomeLab = new JLabel("Prêt à se battre capitaine "+namePlayer+" ?");
		nameRoomLab = new JLabel("Nom de la bataille");
		personneMaxLab = new JLabel("Nombre de pirates max : ");
		
		welcomeLab.setHorizontalAlignment(JLabel.CENTER);
		welcomeLab.setFont(new Font("Serif", Font.BOLD, 20));
		nameRoomLab.setHorizontalAlignment(JLabel.CENTER);
		nameRoomLab.setFont(new Font("Serif", Font.BOLD, 30));
		personneMaxLab.setHorizontalAlignment(JLabel.CENTER);
		personneMaxLab.setFont(new Font("Serif", Font.BOLD, 30));
		
		//Initialisation JTextField
		nameRoomTF = new JTextField("");
		nameRoomTF.setPreferredSize(new Dimension(sizeWidth/2, 40));
		nameRoomTF.setBackground(Color.getHSBColor(187, 93, 52));
		nameRoomTF.setFont(new Font("Serif", Font.BOLD, 36));
		
		//Initialisation JRadioButton
		nbr2 = new JRadioButton("2 pirates");
		nbr2.setSelected(true);
		nbrChoose = 2;
		nbr3 = new JRadioButton("3 pirates");
		nbr4 = new JRadioButton("4 pirates");
		
		nbr2.setFont(new Font("Serif", Font.BOLD, 20));
		nbr3.setFont(new Font("Serif", Font.BOLD, 20));
		nbr4.setFont(new Font("Serif", Font.BOLD, 20));
		
		nbr2.addActionListener(this);
		nbr3.addActionListener(this);
		nbr4.addActionListener(this);
		
		PanneauColor panCenter = new PanneauColor(1);
		panCenter.setLayout(new BorderLayout());
		JPanel pan = new JPanel();
		JPanel panRadioButton = new JPanel();
		
		GridLayout gl = new GridLayout(3 , 2);
		pan.setLayout(gl);
		
		JPanel panBack = new JPanel();
		JPanel panName = new JPanel();
		
		new GridLayout(3 , 1);
		panRadioButton.setLayout(gl);
		panRadioButton.add(nbr2);
		panRadioButton.add(nbr3);
		panRadioButton.add(nbr4);
		
		nbr2.setOpaque(false);
		nbr3.setOpaque(false);
		nbr4.setOpaque(false);
		
		panBack.add(back);
		panName.add(nameRoomTF);
		
		pan.add(panBack);
		pan.add(welcomeLab);
		pan.add(nameRoomLab);
		pan.add(nameRoomTF);
		pan.add(personneMaxLab);
		pan.add(panRadioButton);
		
		panCenter.add(pan, BorderLayout.CENTER);
		panCenter.add(create, BorderLayout.PAGE_END);
		
		pan.setOpaque(false);
		panBack.setOpaque(false);
		panRadioButton.setOpaque(false);
		
		this.setContentPane(panCenter);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == create)
		{
			if(nameRoomTF.getText().length() >= 2 && nameRoomTF.getText().length() <= 15){
				Player player = cli.CreateRoom(namePlayer, nameRoomTF.getText(), nbrChoose);
				if(player != null){
					this.dispose();
					new ConnexionControl(player, cli);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Le nom de la bataille doit être compris entre 2 et 15 caractères.", "Erreur : Nom de la bataille", JOptionPane.ERROR_MESSAGE);
			}
		}
			
		else if(event.getSource() == back)
		{
			this.dispose();
			new ListControl(namePlayer);
		}
		else
		{
			String tmp = ((JRadioButton)event.getSource()).getText();
			
			nbr2.setSelected(false);
			nbr3.setSelected(false);
			nbr4.setSelected(false);
			((JRadioButton)event.getSource()).setSelected(true);
			
			nbrChoose = Integer.parseInt(tmp.substring(0, tmp.indexOf(" ")));
		}
	}
}

