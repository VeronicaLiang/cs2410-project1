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
public class INT1 {
	private static INT1 instance;
	private INT1 (){}
	public static INT1 getInstance(){
		if(instance==null)
			instance = new INT1();
		return instance;
	}
}
