package connectors;

import java.util.ArrayList;

public class Room {
		int idRoom;

		private ArrayList<Player> lPlayer;
		private boolean RoomFull;
		
		public Room(int idRoom)
		{
			lPlayer = new ArrayList<Player>();
			RoomFull = false;
			this.idRoom = idRoom;
		}
		
		public boolean AddPlayer(Player p1)
		{
			if(!RoomFull)
			{
				lPlayer.add(p1);
				if(lPlayer.size()==4)
					RoomFull = true;
				return true;
			}
			else
				return false;
		}
		
		public void setRoomFull(boolean roomFull) {
			RoomFull = roomFull;
		}
		
		public int getIdRoom() {
			return idRoom;
		}
		
		public Player getPlayer(int idPlayer)
		{
			for(int i=0; i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).getId() == idPlayer)
					return lPlayer.get(i);
			}
			return null;
		}
		
}