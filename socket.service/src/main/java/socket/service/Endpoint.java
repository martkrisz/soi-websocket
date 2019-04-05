package socket.service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/cinema")
public class Endpoint {
	
	@OnMessage
	public String message(String message) {
		return null;
	}
	
	public void initRoom(Integer row, Integer col) {
		
	}
	
	public void getRoomSize() {
		
	}
	
	public void updateSeats() {
		
	}
	
	public void lockSeat(Integer row, Integer col) {
		
	}
	
	public void unlockSeat() {
		
	}
	
	public void reserveSeat() {
		
	}

}
