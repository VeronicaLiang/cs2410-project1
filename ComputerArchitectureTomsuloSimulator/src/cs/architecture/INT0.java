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
		
		return false;
	}
	
	public boolean insertAllGPRInstruction(String opco, String rs, String rt, String rd){
		return false;
	}
	
	public void computeResult(int input){
		
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
