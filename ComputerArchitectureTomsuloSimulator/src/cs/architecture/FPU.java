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
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are INT0&INT1 stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are FPU  stations.
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
						
					}
				}
					
				}
		}
		
//		for(int i=0;i<ReservationStationNumber;i++){
//			if((!ReservationStation[i].busy)&&(ROB.size < max)){
//				if(RegisterStatus[rs].busy){
//					//Update Status table
//					h = RegisterStatus[rs].Reorder;
//					if(ROB[h].Ready){
//						ReservationStation[i].Vj = ROB[h].Value;
//						ReservationStation[i].Qj = 0;
//					}else{
//						ReservationStation[i].Qj = h;
//					}
//					
//				}else{
//					ReservationStation[i].Vj = Regs[rs];
//					ReservationStation[i].Qj = 0;
//				}
//				
//				if(RegisterStatus[rt].busy){
//					//Update Status table
//					h = RegisterStatus[rt].Reorder;
//					if(ROB[h].Ready){
//						ReservationStation[i].Vk = ROB[h].Value;
//						ReservationStation[i].Qk = 0;
//					}else{
//						ReservationStation[i].Qk = h;
//					}
//					
//				}else{
//					ReservationStation[i].Vk = Regs[rt];
//					ReservationStation[i].Qk = 0;
//				}
//				
//				
//				ReservationStation[i].busy = true;
//				ReservationStation[i].dest = b;
//				ROB[b].Instruction = opco;
//				ROB[b].Dest = rd;
//				ROB[b].Ready = false;
//				return true;
//			}
//		}
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
