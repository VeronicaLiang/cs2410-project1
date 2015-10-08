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
	private IssueQueue instance;
	private IssueQueue (){}
	public IssueQueue getInstance(){
		return instance;
	}
}
