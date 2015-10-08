package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * InstructionQueue, the queue hold all the instructions.
 */
public class InstructionQueue {
	private static InstructionQueue instance;
	private InstructionQueue(){}
	public static InstructionQueue getInstance(){
		if(instance==null)
			instance = new InstructionQueue();
		return instance;
	}
}
