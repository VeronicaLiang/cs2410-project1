package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit       Latency for operation         Reservation stations            Instructions executing on the unit
 * 
 * Load/Store 1 for address calculation     3 load buffer +3 store buffer   LD, L.D, SD, S.D
 * 
 */
public class LoadStore {
	private static LoadStore instance;
	private LoadStore (){}
	public static LoadStore getInstance(){
		if(instance==null)
			instance = new LoadStore();
		return instance;
	}
}
