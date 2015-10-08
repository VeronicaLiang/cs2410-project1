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
	private MULT instance;
	private MULT (){}
	public MULT getInstance(){
		return instance;
	}
}
