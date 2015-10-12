

/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation                Reservation stations  Instructions executing on the unit
 * BU      1 (condition and target evaluation)  2                     BEQZ, BNEZ, BEQ, BNE
 * BU: Branch Unit, used to 
 */
public class BU {
	private static BU instance;
	private BU (){}
	public static BU getInstance(){
		if(instance==null)
			instance = new BU();
		return instance;
	}
}
