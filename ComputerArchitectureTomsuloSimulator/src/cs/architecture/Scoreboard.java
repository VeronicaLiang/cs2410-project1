package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Scoreboard, Used to manage the execution instructions and states of of all the units.
 * 
 */
public class Scoreboard {
	private static Scoreboard instance;
	private Scoreboard(){}
	public static Scoreboard getInstance(){
		if(instance==null)
			instance = new Scoreboard();
		return instance;
	}
}
