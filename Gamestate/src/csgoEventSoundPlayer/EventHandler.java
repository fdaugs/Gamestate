package csgoEventSoundPlayer;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public final class EventHandler {
	private static SoundHandler mySoundHandler = new SoundHandler(); // Initialisierung des Players
	private static GamestateListener myGameStateListener = new GamestateListener();
	
	private static JSONObject myJSONObject;
	
	private static String PFAD = "C:\\Users\\fd\\Desktop\\ACE.wav";
	
	static boolean fired_bomb = false; //Sound abgespielt?
	static boolean fired_ace = false;
	static String phase = "";
	
	
	public static void main(String[] args) throws IOException {

		while (true){
			myJSONObject = myGameStateListener.listenAndParseJSON();
			System.out.println(myJSONObject.toString());
			EventHandler.reactToEvents(myJSONObject);
		}
		
		
		
		
	}
	
	
	
	public static void reactToEvents(JSONObject o) {
		
	
		
		// Reset der Umgebungsvariablen bei Rundenwechsel
		try {
			phase= o.getJSONObject("round").getString("phase");
			if (phase.equals("over")) {
				fired_bomb = false;
				fired_ace = false;
				System.out.println("reset");
			}
		} catch (JSONException e1) {
			
		}	
		

		// Wurde die Bombe geplanted?
		try {
			String bomb = o.getJSONObject("round").getString("bomb");
			System.out.println(bomb);

			if (bomb.equals("planted") && fired_bomb == false) {
				fired_bomb = true;
				mySoundHandler.playSound(PFAD);
			}
		} catch (Exception e) {
			System.out.println("keine Bombe");
		}

		// Hat der Spieler ein Ace gemacht?
		try {
			int kills = o.getJSONObject("player").getJSONObject("state")
					.getInt("round_kills");
			if (kills == 5 && fired_ace == false) {
				mySoundHandler.playSound(PFAD); // Abspielen
																				// des
																				// Sounds(16bit
																				// wav
																				// 44,1kHz)
				fired_ace = true;
			}
		} catch (Exception e) {

		}
		
		// Hat der Spieler die Bombe?
		try {
			String weapon2 = o.getJSONObject("player").getJSONObject("weapons").getJSONObject("weapon_02")
					.getString("name");
			if (weapon2.equals("c4")) {
				mySoundHandler.playSound(PFAD); // Abspielen
																				// des
																				// Sounds(16bit
																				// wav
																				// 44,1kHz)
				fired_ace = true;
			}
		} catch (Exception e) {

		}

	}
	
		
	}

