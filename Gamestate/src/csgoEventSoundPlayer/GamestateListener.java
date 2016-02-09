package csgoEventSoundPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.*;

public class GamestateListener {

	private static int PORT = 3001; // Port specified in your cfg
	public static ServerSocket listenServer;
	private static JSONObject MYJSONOBJ;

	public GamestateListener() {
		try {
			listenServer = new ServerSocket(PORT); // open new Server Socket
			System.out.println("Started Socket..."); // printing out started
														// listening
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public JSONObject listenAndParseJSON() throws IOException {

		System.out
				.println("Listening for connection on port " + PORT + " ...."); // printing
																				// out
																				// started
																				// listening

		try (Socket socket = listenServer.accept()) { // wait for connection

			//
			String responseString = IOUtils.toString(socket.getInputStream(),
					"UTF-8");
			MYJSONOBJ = new JSONObject(responseString.substring(responseString
					.indexOf("{")));// split the response string
			//
			return MYJSONOBJ;// return the json obj

		} catch (Exception e) {
			MYJSONOBJ = new JSONObject("{ERROR:True}");// create error obj
			return MYJSONOBJ;// return it
		}

	}

}
