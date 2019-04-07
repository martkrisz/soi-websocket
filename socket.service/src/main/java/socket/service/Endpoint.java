package socket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/cinema")
public class Endpoint {
	private static SeatManager seatManager = new SeatManager();
	private Parser parser = new Parser();
	private static ArrayList<Session> sessions = new ArrayList<Session>() ;
	
	@OnOpen
	public void open(Session session) {
	System.out.println("WebSocket opened: " + session.getId());
		sessions.add(session);
	}
	@OnClose
	public void close(Session session) {
	System.out.println("WebSocket closed: " + session.getId());
		sessions.remove(session);
	}
	
	
	@OnMessage
	public void message(Session session, String message) throws Exception {
		System.out.println("WebSocket message: " + message);
		JsonObject msg = parser.getJsonObject(message);
		String type = msg.getString("type");
		JsonObject reply;
		switch(type)
		{
		case "initRoom":
			if(msg.containsKey("rows") && msg.containsKey("columns"))
			{
				seatManager.initRoom(msg.getInt("rows"), msg.getInt("columns"));
			}
			break;

		case "getRoomSize":
			RoomSize room = seatManager.getRoomSize();
			reply = Json.createObjectBuilder()
			.add("type", "roomSize")
			.add("rows", room.Rows)
			.add("columns", room.Columns)
			.build();
			session.getBasicRemote().sendObject(reply.toString());
			break;

		case "updateSeats":
			ArrayList<ArrayList<Seat>> seats = seatManager.getSeats();
			for (ArrayList<Seat> rows : seats) {
				for (Seat seat : rows) {
					reply = Json.createObjectBuilder()
							.add("type", "seatStatus")
							.add("row", seat.Row)
							.add("column", seat.Column)
							.add("status", seat.Status.toString())
							.build();
							session.getBasicRemote().sendObject(reply.toString());
				}
			}
			break;

		case "lockSeat":
			if(msg.containsKey("row") && msg.containsKey("column"))
			{
				String id = seatManager.lockSeat(msg.getInt("row"), msg.getInt("column"));
				reply = Json.createObjectBuilder()
						.add("type", "lockResult")
						.add("lockId", id)
						.build();
				session.getBasicRemote().sendObject(reply.toString());
						
				Seat seat = seatManager.getSeat(msg.getInt("row"), msg.getInt("column"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("column", seat.Column)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;

		case "unlockSeat":
			if(msg.containsKey("lockId"))
			{
				seatManager.unlockSeat(msg.getString("lockId"));
						
				Seat seat = seatManager.getSeat(msg.getString("lockId"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("column", seat.Column)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;

		case "reserveSeat":
			if(msg.containsKey("lockId"))
			{
				seatManager.reserveSeat(msg.getString("lockId"));
						
				Seat seat = seatManager.getSeat(msg.getString("lockId"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("column", seat.Column)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;
		
		}
		
	}
	
	@OnError
	public void error(Session session,Throwable t) throws IOException, EncodeException {
		System.out.println("WebSocket error: " + t.getMessage());
		JsonObject reply = Json.createObjectBuilder()
				.add("type", "error")
				.add("message", t.getMessage())
				.build();
		session.getBasicRemote().sendObject(reply.toString());
	}
	
	
	private static void broadcast(String message) throws IOException, EncodeException{
		for (Session session : sessions) {
			session.getBasicRemote().sendObject(message);
		};   

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
