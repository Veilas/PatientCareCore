package src;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utilities {
	
	public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return df.format(new Date()).toString();
	}
	
	public static byte[] getByteArray(ArrayList<ServiceData> data) {
		byte[] beds = new byte[data.size()];
		
		//ne kodirati id kreveta sa 0
		for(int i = 0; i < data.size(); i++) {
			beds[i] = (byte) data.get(i).getBed_id(); 
		}
		return beds;
	}

}
