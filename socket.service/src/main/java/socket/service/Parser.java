package socket.service;

import java.io.StringReader;
import javax.json.*;

public class Parser {
	
	public JsonObject getJsonObject(String message) {
		message.replaceAll("\r?\n", "");
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject result = reader.readValue().asJsonObject();
		reader.close();
		return result;
	}
	
	
}