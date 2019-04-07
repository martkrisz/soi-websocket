package socket.service;

import java.util.ArrayList;

public class SeatManager {
	static private ArrayList<ArrayList<Seat>> Seats;
	static private int lockId = 1;
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
	
	public boolean lockSeat(int row, int coloumn) {
		if(Seats.size()<row)
			return false;
		ArrayList<Seat> col = Seats.get(row);
		if(col.size()<coloumn)
			return false;
		Seat seat = col.get(coloumn);
		if(seat.Status != SeatStatus.Free)
			return false;
		seat.Status = SeatStatus.Locked;
		seat.LockId = "Lock" + Integer.toString(lockId++);
		return true;
	}
	
	public String getLockId(int row, int coloumn)
	{
		return Seats.get(row).get(coloumn).LockId;
	}
	
	public boolean unlockSeat(String lockid) {
		for (ArrayList<Seat> rows : Seats) {
			for (Seat seat : rows) {
				if(seat.Status == SeatStatus.Locked && seat.LockId.equals(lockid))
				{
					seat.Status = SeatStatus.Free;
					seat.LockId = "";
					return true;
				}
			}
			
		}
		return false;
	}
	
	public boolean reserveSeat(String lockid) {
		for (ArrayList<Seat> rows : Seats) {
			for (Seat seat : rows) {
				if(seat.Status == SeatStatus.Locked && seat.LockId.equals(lockid))
				{
					seat.Status = SeatStatus.Reserved;
					return true;
				}
			}
			
		}
		return false;
	}
}
