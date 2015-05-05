package launches;

import org.json.JSONException;
import org.json.JSONObject;

public class MainClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject dataset = new JSONObject();
		try {
			dataset.put("Methode", "ListeActeur");
		} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
	}

}
