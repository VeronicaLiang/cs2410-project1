package cs.architecture;

import cs.architecture.Const.Register;

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
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are INT0&INT1 stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are FPU  stations.
	 */
	
	public boolean insertInstruction(String opco, String rs, String rt, String rd){
		if(isImmediate(opco))
			return insertImmInstruction(opco,rs,rt,rd);
		else
			return insertAllGPRInstruction(opco, rs, rt, rd);
	}
	
	public boolean isImmediate(String opco){
		//check if the instruction is immediate
		return (opco.equals("ANDI") || opco.equals("DADDI"));
	}
	
	public boolean insertImmInstruction(String opco, String rs, String rt, String rd){
//		for(int i = 0;i<=2;i++){
//			Station station = Const.reservationStations.get(i+"");
//			if((!station.Busy) && (Const.ROB.size()< Simulator.NR)){
//				int h;
//				Register register = Const.integerRegistersStatus.get(rs);
//			
//				if(register.busy){
//					h = register.Reorder;
//					if(Const.ROB.get(h).ready){
//						Const.reservationStations.get(i+"").Vj = Const.ROB.get(h).intValue;
//						Const.reservationStations.get(i+"").Qj = 0;
//					}else{
//						Const.reservationStations.get(i+"").Qj = h;
//					}
//				}else{
//					Const.reservationStations.get(i+"").Vj = register.intValue ;
//					Const.reservationStations.get(i+"").Qj = 0;
//				}
//			
//				// The same update for rt 
//				register = Const.integerRegistersStatus.get(rt);
//
//				if(register.busy){
//					h = register.Reorder;
//					if(Const.ROB.get(h).ready){
//						Const.reservationStations.get(i+"").Vk = Const.ROB.get(h).intValue;
//						Const.reservationStations.get(i+"").Qk = 0;
//					}else{
//						Const.reservationStations.get(i+"").Qk = h;
//					}
//				}else{
//					Const.reservationStations.get(i+"").Vk = register.intValue ;
//					Const.reservationStations.get(i+"").Qk = 0;
//				}
//				Const.reservationStations.get(i+"").Busy = true;
//				// The number of the ROB entry allocated for the result is also sent to the reservation station.
//				int ROB_nbr = Const.ROB.size();
//				Const.reservationStations.get(i+"").Dest = ROB_nbr;
//				Const.integerRegistersStatus.get(rd).Reorder = ROB_nbr;
//				Const.ROB.get(h).ready = false;
//				return true;
//			}
//		}
		return false;
	}
	
	public boolean insertAllGPRInstruction(String opco, String rs, String rt, String rd){
		return false;
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
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are MULT stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are BU  stations.
	 */
	public void execute(){
		for(int i = 5;i<=6;i++){
			Station station = Const.reservationStations.get(i+"");
			//TODO Check whether operands are available.
		}
	}
	
}
