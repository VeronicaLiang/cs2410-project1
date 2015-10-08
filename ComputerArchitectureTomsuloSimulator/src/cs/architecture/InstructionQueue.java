package cs.architecture;

public class InstructionQueue {
	private InstructionQueue instance;
	private InstructionQueue(){}
	public InstructionQueue getInstance(){
		return instance;
	}
}
