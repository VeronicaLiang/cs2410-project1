package cs.architecture;

public class IssueQueue {
	private IssueQueue instance;
	private IssueQueue (){}
	public IssueQueue getInstance(){
		return instance;
	}
}
