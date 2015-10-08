package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * InstructionQueue, the queue hold all the instructions.
 */
public class InstructionQueue {
	private InstructionQueue instance;
	private InstructionQueue(){}
	public InstructionQueue getInstance(){
		return instance;
	}
}
