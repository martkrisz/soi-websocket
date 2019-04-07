package socket.service;

public class Seat {
	public int Row = -1;
	public int Coloumn = -1;
	public SeatStatus Status =  SeatStatus.Free;
	public String LockId = "";
	
	
	public Seat(int row, int coloumn)
	{
		Row = row;
		Coloumn = coloumn;
		Status = SeatStatus.Free;
		LockId = "";
	}
}


