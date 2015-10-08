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
	private Bus instance;
	private Bus(){}
	public Bus getInstance(){
		return instance;
	}
}
