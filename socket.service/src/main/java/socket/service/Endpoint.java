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
			if(!msg.isNull("rows") && !msg.isNull("coloums"))
			{
				seatManager.initRoom(msg.getInt("rows"), msg.getInt("coloums"));
			}
			break;

		case "getRoomSize":
			RoomSize room = seatManager.getRoomSize();
			reply = Json.createObjectBuilder()
			.add("type", "roomSize")
			.add("rows", room.Rows)
			.add("coloums", room.Coloumns)
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
							.add("coloum", seat.Coloumn)
							.add("status", seat.Status.toString())
							.build();
							session.getBasicRemote().sendObject(reply.toString());
				}
			}
			break;

		case "lockSeat":
			if(!msg.isNull("row") && !msg.isNull("coloum"))
			{
				String id = seatManager.lockSeat(msg.getInt("row"), msg.getInt("coloum"));
				reply = Json.createObjectBuilder()
						.add("type", "lockResult")
						.add("lockId", id)
						.build();
				session.getBasicRemote().sendObject(reply.toString());
						
				Seat seat = seatManager.getSeat(msg.getInt("row"), msg.getInt("coloum"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("coloum", seat.Coloumn)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;

		case "unlockSeat":
			if(!msg.isNull("lockId"))
			{
				seatManager.unlockSeat(msg.getString("lockId"));
						
				Seat seat = seatManager.getSeat(msg.getString("lockId"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("coloum", seat.Coloumn)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;

		case "reserveSeat":
			if(!msg.isNull("lockId"))
			{
				seatManager.reserveSeat(msg.getString("lockId"));
						
				Seat seat = seatManager.getSeat(msg.getString("lockId"));
				reply = Json.createObjectBuilder()
						.add("type", "seatStatus")
						.add("row", seat.Row)
						.add("coloum", seat.Coloumn)
						.add("status", seat.Status.toString())
						.build();
				broadcast(reply.toString());
			}
			break;
		
		}
		
	}
	
	@OnError
	public void error(Throwable t) {
		System.out.println("WebSocket error: " + t.getMessage());
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
