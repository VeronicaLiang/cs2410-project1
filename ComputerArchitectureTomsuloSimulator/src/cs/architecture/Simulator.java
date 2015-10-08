package cs.architecture;
/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Simulator, the simulating procedure runner.
 * 
 */
public class Simulator {
	BU buUnit;// BU unit instance;
	Bus bus;//Bus unit instance;
	FPU fpuUnit;
	InstructionQueue instructionQueue;
	INT0 int0Unit;
	INT1 int1Unit;
	IssueQueue issueQueue;
	LoadStore loadStoreUnit;
	MULT multUnit;
	PC pc;
	RegisterFile registerFile;
	ROB renamingBuffer;
	Scoreboard scoreboard;
	public Simulator(){
		buUnit = BU.getInstance();
		bus = Bus.getInstance();
		fpuUnit = FPU.getInstance();
		instructionQueue = InstructionQueue.getInstance();
		int0Unit = INT0.getInstance();
		int1Unit = INT1.getInstance();
		issueQueue = IssueQueue.getInstance();
		loadStoreUnit = LoadStore.getInstance();
		multUnit = MULT.getInstance();
		pc = PC.getInstance();
		registerFile = RegisterFile.getInstance();
		renamingBuffer = ROB.getInstance();
		scoreboard = Scoreboard.getInstance();
	}
}
