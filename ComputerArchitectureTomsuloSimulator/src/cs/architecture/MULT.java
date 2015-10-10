package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation              Reservation stations      Instructions executing on the unit
 * 
 * MULT    4 (integer multiply)               2                         DMUL
 * 
 */
public class MULT {
	private static MULT instance;
	private MULT (){}
	public static MULT getInstance(){
		if(instance==null){
			instance = new MULT();
		}
		return instance;
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
