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
//				Date today = new Date(); //create date for timestamp
//				String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today; //create timestamp var
//				socket.getOutputStream().write(httpResponse.getBytes("UTF-8")); //send timestamp

//				String responseString = new String(getStringFromInputStream(socket.getInputStream()));//parse and save the response
//				
				String responseString = IOUtils.toString(socket.getInputStream(), "UTF-8");
				MYJSONOBJ = new JSONObject(responseString.substring(responseString.indexOf("{")));//split the response string
//				
				return MYJSONOBJ;//return the json obj
				
//				return getJsonObject(socket.getInputStream());

		} catch (Exception e) {
			MYJSONOBJ = new JSONObject("{ERROR:True}");//create error obj
			return MYJSONOBJ;//return it
		}
					
		
	}
	
	
	
	private static String getStringFromInputStream(InputStream is) {
		System.out.println("InputString Start" + System.currentTimeMillis());
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
		System.out.println("InputString Stop" + System.currentTimeMillis());
		return sb.toString();
		
	}
	
	
	private JSONObject getJsonObject(InputStream inputStreamObject){

	    //Create input stream
//	    InputStream inputStreamObject = getRequestclass().getResourceAsStream(jsonFileName);
		System.out.println("InputString Start" + System.currentTimeMillis());
	   try {
	       BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject, "UTF-8"));
	       StringBuilder responseStrBuilder = new StringBuilder();

	       String inputStr;
	       while ((inputStr = streamReader.readLine()) != null)
	           responseStrBuilder.append(inputStr);

	       JSONObject jsonObject = new JSONObject(responseStrBuilder.toString().substring(responseStrBuilder.indexOf("{")));
	       System.out.println("InputString Stop" + System.currentTimeMillis());
	       //returns the json object
	       return jsonObject;

	   } catch (IOException e) {
	       e.printStackTrace();
	   } catch (JSONException e) {
	       e.printStackTrace();
	   }

	    //if something went wrong, return null
	    return null;
	}
	
	

	

}
