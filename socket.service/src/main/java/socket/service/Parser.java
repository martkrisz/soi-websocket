package socket.service;

import java.io.StringReader;
import javax.json.*;

public class Parser {
	
	public JsonObject getJsonObject(String message) {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject result = reader.readObject();
		reader.close();
		return result;
	}
	
	
}