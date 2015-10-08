package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * ROB, Renaming Order Buffer.
 * 
 */
public class ROB {
	private static ROB instance;
	private ROB (){}
	public static ROB getInstance(){
		if(instance==null)
			instance = new ROB();
		return instance;
	}
}
