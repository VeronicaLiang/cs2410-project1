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
}
