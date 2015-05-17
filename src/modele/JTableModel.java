
package modele;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sun.prism.paint.Color;

import connectors.Room;

public class JTableModel extends AbstractTableModel{

	private Object[][] data;
	private String[] title;
	
	public JTableModel( Object[][] data, String[]title)
	{
		this.data = data;
		this.title = title;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return data[row][column];
	}
	
	public String getColumnName(int col) {
		return this.title[col];
	}
	
	public boolean isCellEditable()
	{
		return false;
	}
	
	public Class getColumnClass(int col){
		  //On retourne le type de la cellule à la colonne demandée
		  //On se moque de la ligne puisque les types de données sont les mêmes quelle que soit la ligne
		  //On choisit donc la première ligne
		  return this.data[0][col].getClass();
		}
	
	public void setValueAt(Object value, int row, int col) {
		this.data[row][col] = value;
	}
	
	private void addRow(Object[] newTab, int row) {
		// TODO Auto-generated method stub
		for(int i = 0; i < newTab.length; i++)
		{
			this.setValueAt(newTab[i], row, i);
		}
	}
	
	public void remplir(ArrayList<Room> data)
	{
		this.data = new Object[data.size()][title.length];
		
		Object[] lign = new Object[title.length];
		
		for(int i = 0; i < data.size(); i++)
		{
			//System.out.println("\n"+data.get(i).getIdRoom()+ " : "+data.get(i).getName()+" - "+data.get(i).getCreator()+" : "+data.get(i).getNbPlayerMax());
			lign[0] = data.get(i).getIdRoom();
			lign[1] = data.get(i).getName();
			lign[2] = data.get(i).getCreator();
			lign[3] = data.get(i).getNbPlayerMax();
			
			addRow(lign, i);
			
			lign = new Object[title.length];
			
		}
		
		this.fireTableDataChanged();
	}


}
