package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * PCInstance, PC.
 * 
 */
public class PC {
	static PC instance;
	private PC(){
		
	}
	public static PC getInstance(){
		if(instance==null)
			instance = new PC();
		return instance;
	}
	
	void set(int newValue){
		// set up the Program counter value
	}
}
