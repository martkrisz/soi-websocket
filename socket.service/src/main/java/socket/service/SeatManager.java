package socket.service;

import java.util.ArrayList;

public class SeatManager {
	private ArrayList<ArrayList<Seat>> Seats;
	private int lockId = 1;
	int Rows = -1;
	int Coloumns = -1;

	public void initRoom(Integer row, Integer col) {
		Rows = row;
		Coloumns = col;
		Seats = new ArrayList<ArrayList<Seat>>();
		for(int i = 0;i <row;i++)
		{
			ArrayList<Seat> seatrow = new ArrayList<Seat>();
			for(int l =0;l<col;l++)
				seatrow.add(new Seat(i+1,l+1));
			Seats.add(seatrow);
		}
	}
	
	public RoomSize getRoomSize() {
		return new RoomSize(Rows, Coloumns);
	}
	
	public ArrayList<ArrayList<Seat>> getSeats() {
		return Seats;
	}
	
	public Seat getSeat(int row, int coloumn) throws Exception {
		if(Seats.size()<row-1)
			throw new Exception("Invalid row!");
		ArrayList<Seat> col = Seats.get(row-1);
		if(col.size()<coloumn-1)
			throw new Exception("Invalid coloumn!");
		return col.get(coloumn-1);
	}
	
	public Seat getSeat(String lockId) throws Exception {
		for (ArrayList<Seat> rows : Seats) {
			for (Seat seat : rows) {
				if(seat.Status.equals(SeatStatus.locked) && seat.LockId.equals(lockId))
					return seat;
			}
			
		}

		throw new Exception("LockId not exits!");
	}
	
	public String lockSeat(int row, int coloumn) throws Exception {
		Seat seat = getSeat(row, coloumn);
		if(seat.Status != SeatStatus.free)
			throw new Exception("Seat is not free!");
		seat.Status = SeatStatus.locked;
		seat.LockId = "Lock" + Integer.toString(lockId++);
		return seat.LockId;
	}
	
	public String getLockId(int row, int coloumn)
	{
		return Seats.get(row-1).get(coloumn-1).LockId;
	}
	
	public Seat  unlockSeat(String lockId) throws Exception {
		Seat seat = getSeat(lockId);
		seat.Status = SeatStatus.free;
		seat.LockId = "";
		return seat;
	}
	
	public Seat reserveSeat(String lockId) throws Exception {

		Seat seat = getSeat(lockId);
		seat.Status = SeatStatus.reserved;
		return seat;
	}
}
