package cs.architecture;

import cs.architecture.Const.Register;

/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation              Reservation stations      Instructions executing on the unit
 * FPU     4 (pipelined FP multiply/add)      5                         ADD.D, SUB.D, MUL.D,DIV.D
 *         4 (non-pipelined divide)
 * FPU:    Float Point Unit
 */
public class FPU {
	
	private static FPU instance;
	private FPU (){}
	public static FPU getInstance(){
		if(instance==null)
			instance = new FPU();
		return instance;
	}
	private static final int LATENCY = 4;
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are MULT stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are BU  stations.
	 */
	public boolean insertInstruction(String opco, String rs, String rt, String rd){
		for(int i = 13;i<=17;i++){
			Station station = Const.reservationStations.get(i+"");
			if((!station.Busy) && (Const.ROB.size()< Simulator.NR)){
				int h;
				Register register;
				if (rs.contains("R")){
					register = Const.integerRegistersStatus.get(rs);
				}else{
					register = Const.floatRegistersStatus.get(rs);
				}
				
				if(register.busy){
					h = register.Reorder;
					if(Const.ROB.get(h).ready){
						if (rs.contains("R")){
							Const.reservationStations.get(i+"").Vj = Const.ROB.get(h).intValue;
						}else{
							Const.reservationStations.get(i+"").Vj = Const.ROB.get(h).floatValue;
						}
						Const.reservationStations.get(i+"").Qj = 0;
					}else{
						Const.reservationStations.get(i+"").Qj = h;
					}
				}else{
					if (rs.contains("R")){
						Const.reservationStations.get(i+"").Vj = register.intValue ;
					}else{
						Const.reservationStations.get(i+"").Vj = register.floatValue ;
					}
					Const.reservationStations.get(i+"").Qj = 0;
				}
				
				// The same update for rt 
				if (rt.contains("R")){
					register = Const.integerRegistersStatus.get(rt);
				}else{
					register = Const.floatRegistersStatus.get(rt);
				}
				
				if(register.busy){
					h = register.Reorder;
					if(Const.ROB.get(h).ready){
						if (rt.contains("R")){
							Const.reservationStations.get(i+"").Vk = Const.ROB.get(h).intValue;
						}else{
							Const.reservationStations.get(i+"").Vk = Const.ROB.get(h).floatValue;
						}
						Const.reservationStations.get(i+"").Qk = 0;
					}else{
						Const.reservationStations.get(i+"").Qk = h;
					}
				}else{
					if (rt.contains("R")){
						Const.reservationStations.get(i+"").Vk = register.intValue ;
					}else{
						Const.reservationStations.get(i+"").Vk = register.floatValue ;
					}
					Const.reservationStations.get(i+"").Qk = 0;
				}
				Const.reservationStations.get(i+"").Busy = true;
				// The number of the ROB entry allocated for the result is also sent to the reservation station.
				int ROB_nbr = Const.ROB.size();
				Const.reservationStations.get(i+"").Dest = ROB_nbr;
				if (rd.contains("R")){
					Const.integerRegistersStatus.get(rd).Reorder = ROB_nbr;
				}else{
					Const.floatRegistersStatus.get(rd).Reorder = ROB_nbr;
				}
				Const.ROB.get(h).ready = false;
				return true;
			}
		}
		
		// the issue is not successful, needs to stall for one cycle.
		return false;
	}
	
	public void computeResult(int input){
		//change the long bits to double to calculate the result
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
