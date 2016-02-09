package csgoEventSoundPlayer;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;



public class GamestateListener {

	private static int PORT = 3001; //Port specified in your cfg
	public static  ServerSocket listenServer; 
	private static JSONObject MYJSONOBJ;

	
	public  GamestateListener() {
		try {
			listenServer = new ServerSocket(PORT); //open new Server Socket
			System.out.println("Started Socket..."); //printing out started listening
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	public JSONObject listenAndParseJSON() throws IOException{
		
		
		System.out.println("Listening for connection on port "+PORT+" ...."); //printing out started listening
		
			try (Socket socket = listenServer.accept()) { //wait for connection
				
				System.out.println("Start get From Socket           " + System.currentTimeMillis());
				InputStream mis = socket.getInputStream();
				System.out.println("Stop get From Socket           " + System.currentTimeMillis());
				String responseString = IOUtils.toString(mis, "UTF-8"); 
				System.out.println("Stop to String           " + System.currentTimeMillis());
				MYJSONOBJ = new JSONObject(responseString.substring(responseString.indexOf("{")));//split the response string
				
			
				
				return MYJSONOBJ;//return the json obj

		} catch (Exception e) {
			MYJSONOBJ = new JSONObject("{ERROR:True}");//create error obj
			return MYJSONOBJ;//return it
		}
					
		
	}
	
	
	
	
	

}
