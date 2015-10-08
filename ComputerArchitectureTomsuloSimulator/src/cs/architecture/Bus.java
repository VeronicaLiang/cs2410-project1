package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Bus, the communication channel among resources.
 * 
 */
public class Bus {
	private static Bus instance;
	private Bus(){}
	public static Bus getInstance(){
		if(instance==null)
			instance = new Bus();
		return instance;
	}
}
