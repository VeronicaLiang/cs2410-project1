package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * IssueQueue, the queue used to contain instructions are about to get issued.
 * 
 */
public class IssueQueue {
	private static IssueQueue instance;
	private IssueQueue (){}
	public static IssueQueue getInstance(){
		if(instance==null){
			instance = new IssueQueue();
		}
		return instance;
	}
}
