package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit       Latency for operation         Reservation stations            Instructions executing on the unit
 * 
 * 
 */
public class INT0 {
	private static INT0 instance;
	private INT0 (){}
	public static INT0 getInstance(){
		if(instance==null)
			instance = new INT0();
		return instance;
	}
	
	public boolean insertInstruction(String opco, int rs, int rt, int rd){
		for(int i = 13;i<=17;i++){
			Station station = Const.reservationStations.get(i+"");
			if((!station.busy) && (Const.ROB.size()<Simulator.NR)){
				
			}
		}
	}
	
	public void computeResult(int input){
		double rs = Double.longBitsToDouble(ReservationStation[input].Vj);
		double rt = Double.longBitsToDouble(ReservationStation[input].Vk);
		double result = 0;
		
		//calculate the result
		if(ReservationStation[input].operation.equals("mul.d"))
			result = rs * rt;
		
		//change the result to long bits
		ReservationStation[input].result = Double.doubleToLongBits(result);
	}
	
}
