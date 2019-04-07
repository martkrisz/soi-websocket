package socket.service;

public class Seat {
	public int Row = -1;
	public int Column = -1;
	public SeatStatus Status =  SeatStatus.free;
	public String LockId = "";
	
	
	public Seat(int row, int column)
	{
		Row = row;
		Column = column;
		Status = SeatStatus.free;
		LockId = "";
	}
}


