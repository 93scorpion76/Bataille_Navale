package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SalonView extends JFrame implements ActionListener{
	
	private JPanel rightPan;
	private JPanel leftPan;
	private JPanel centerPan;
	
	private JButton[]chooseBoat;
	
	private JLabel[]namePlayer;
	private JButton[] isReady;
	
	private JButton ready = new JButton("Prêt");
	private JButton exit = new JButton("Quitter");
	
	public SalonView()
	{
		this.setTitle("Bataille Navale");
		this.setSize(1366, 725);
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
			chooseBoat[i] = new JButton("Position "+tmp);
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
			
		}
		else if(event.getSource() == ready)
		{
			new GameView();
			this.dispose();
		}
		else
		{
			
		}
	}
}
