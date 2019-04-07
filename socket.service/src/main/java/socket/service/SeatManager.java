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
	
	public String lockSeat(int row, int coloumn) throws Exception {
		if(Seats.size()<row)
			throw new Exception("Invalid row!");
		ArrayList<Seat> col = Seats.get(row);
		if(col.size()<coloumn)
			throw new Exception("Invalid coloumn!");
		Seat seat = col.get(coloumn);
		if(seat.Status != SeatStatus.free)
			throw new Exception("Seat is not free!");
		seat.Status = SeatStatus.locked;
		seat.LockId = "Lock" + Integer.toString(lockId++);
		return seat.LockId;
	}
	
	public String getLockId(int row, int coloumn)
	{
		return Seats.get(row).get(coloumn).LockId;
	}
	
	public void  unlockSeat(String lockid) throws Exception {
		for (ArrayList<Seat> rows : Seats) {
			for (Seat seat : rows) {
				if(seat.Status == SeatStatus.locked && seat.LockId.equals(lockid))
				{
					seat.Status = SeatStatus.free;
					seat.LockId = "";
					return;
				}
			}
			
		}

		throw new Exception("LockId not exits!");
	}
	
	public void reserveSeat(String lockid) throws Exception {
		for (ArrayList<Seat> rows : Seats) {
			for (Seat seat : rows) {
				if(seat.Status == SeatStatus.locked && seat.LockId.equals(lockid))
				{
					seat.Status = SeatStatus.reserved;
					return;
				}
			}
			
		}

		throw new Exception("LockId not exits!");
	}
}
