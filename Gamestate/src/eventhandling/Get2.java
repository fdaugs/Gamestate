package eventhandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;




import org.json.*;

public class Get2 {

	public static void main(String[] args) throws Exception {
		Sound2 player = new Sound2(); // Initialisierung des Players
		boolean fired_bomb = false; //Sound abgespielt?
		boolean fired_ace = false;
		int round = 0; //Aktuelle Runde
		int roundp =-1; //vorherige Runde
		
		final ServerSocket server = new ServerSocket(3001);
		System.out.println("Listening for connection on port 8080 ....");
		while (true) {
			try (Socket socket = server.accept()) {
				Date today = new Date();
				String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
				socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));

				
				String responseString = new String(getStringFromInputStream(socket.getInputStream()));
				
				JSONObject obj = new JSONObject(responseString.substring(responseString.indexOf("{")));
				
				
//				Reset der Umgebungsvariablen bei Rundenwechsel
				
				round= obj.getJSONObject("map").getInt("round");
				if(round>roundp){ 
					fired_bomb = false;
					fired_ace = false;
					roundp=round;
					System.out.println("reset");
				}
				
//				Wurde die Bombe geplanted?
				try {
					String bomb = obj.getJSONObject("round").getString("bomb");
					System.out.println(bomb);
					
					if(bomb.equals("planted") && fired_bomb == false){
						//player.playSound("C:\\Users\\fd\\Desktop\\ACE.wav"); //Abspielen des Sounds(16bit wav 44,1kHz)	
						fired_bomb = true;
					}
				} catch (Exception e) {
					System.out.println("keine Bombe");
				}
				
//				Hat der Spieler ein Ace gemacht?
				try {
					int kills= obj.getJSONObject("player").getJSONObject("state").getInt("round_kills");
					if(kills==5 && fired_ace == false){
						player.playSound("C:\\Users\\fd\\Desktop\\ACE.wav"); //Abspielen des Sounds(16bit wav 44,1kHz)
						fired_ace = true;
					}
				} catch (Exception e) {
					
					
				}
				
				
				
				

			}

		}
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	

}
