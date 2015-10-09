package cs.architecture;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	FPU fpuUnit;// FPU unit instance;
	InstructionQueue instructionQueue;// Instruction queue unit instance;
	INT0 int0Unit;//INT0 unit instance
	INT1 int1Unit;//INT1 unit instance
	IssueQueue issueQueue;//Issuing queue instance
	LoadStore loadStoreUnit;// Load and store unit instance
	MULT multUnit;// MULT unit instance
	PC pc;//PC instance
	RegisterFile registerFile;//Register file instance
	ROB renamingBuffer;//ROB instance
	Scoreboard scoreboard;//scoreboard instance
	
	ArrayList<String> instructionList = new ArrayList<String>();
	
	boolean finishedFlag = true;//flag for whether this simulation comes to its end.
	
	public Simulator(String instructionFile, Memory main){
		//Initiate all the units
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
		
//		fetch all the instruction from instruction file.
		String line = null;
		try {
			FileReader filereader = new FileReader (instructionFile);
			BufferedReader bufferedreader = new BufferedReader (filereader);
			boolean flag = false; // indicate when the data is start loading
			while ((line = bufferedreader.readLine()) != null){
				if(flag){
					main.loadData(line);
				}else{
					if(line.contains("DATA")){
						flag = true;
					}else{
						Instructions instr = new Instructions();
						instr = instr.loadInstrs(line);
						main.loadInstruction(instr);
					}
				}
			}
		
//			System.out.println(main.getData().size());
			bufferedreader.close();
		}
		catch (FileNotFoundException ex){
			System.out.println("Unable to open file '" + instructionFile + "'");
		}
		catch (IOException ex){
			System.out.println("Error reading file '" + instructionFile + "'");
		}
		
	}
	/*
	 * Start this simulation with a loop representing clock cycles.
	 */
	public void startSimulation(Memory main, int NF, int NQ){
		
		finishedFlag = false;
		int clock_cycle = 0;
		int pc = 0; //initialize the program counter 
		BranchTargetBuffer BTBuffer = new BranchTargetBuffer();
		LinkedList<Instructions> FQueue = new LinkedList<Instructions>(); // Fetched Instructions Queue

		while(!finishedFlag){//Clock cycles loop
			
			/**
			 * If the instruction queue is not full, and there are instructions not finished,
			 * Fetch instructions. 
			 * In one clock cycle, the maximum number of fetching is NF. 
			 */
			int fetched = 0;
			while((FQueue.size() <= NQ) && (fetched < 4) &&(pc < main.getInstrs().size())){
				main.getInstrs().get(pc).UpdatePC(pc); // Instruction needs to have a feature called pc, so that can check whether the BTBuffer prediction is wrong.
				FQueue.add(main.getInstrs().get(pc));
				if(BTBuffer.Getbuffer()[pc][0] != -1){
					pc = BTBuffer.Getbuffer()[pc][0]; // if there is an entry in BTBuffer, use the predicted pc, otherwise, pc ++
				}else{
					pc++;
				}
				fetched++;
			}

			
			/**
			 * Decode the instruction
			 * If the instruction is not a branch, but find in BTBuffer, need to deleted the following instructions in the iqueue, and refetch. 
			 */
//			while(the DQueue size not exceed )
//			if(the instruction is not a branch, but have entry in BTBuffer){
//				
//			}
			
			
			issue();
			readOperands();
			execute();
			writeResult();
			
			clock_cycle ++;
			if(pc >= main.getInstrs().size()){
				finishedFlag = true;
			}
		}
		//Destroying all the resources. TODO
	}
	/*
	 * If a functional unit for the instruction is free and no other active
       instruction has the same destination register, the scoreboard issues the
       instruction to the functional unit and updates its internal data structure. This
       step replaces a portion of the ID step in the MIPS pipeline. By ensuring that
       no other active functional unit wants to write its result into the destination
       register, we guarantee that WAW hazards cannot be present. If a structural or
       WAW hazard exists, then the instruction issue stalls, and no further instructions
       will issue until these hazards are cleared. When the issue stage stalls, it
       causes the buffer between instruction fetch and issue to fill; if the buffer is a
       single entry, instruction fetch stalls immediately. If the buffer is a queue with
       multiple instructions, it stalls when the queue fills.

	 */
	public void issue(){
		//check the functional unit for the instruction is free or not.
		//check not other active instruction has the same destination register.
		//the scoreboard issues the instruction to the functional unit and updates its internal data structural.
		
		
	}
	/*
	 * The scoreboard monitors the availability of the source operands.
       A source operand is available if no earlier issued active instruction is
       going to write it. When the source operands are available, the scoreboard tells
       the functional unit to proceed to read the operands from the registers and
       begin execution. The scoreboard resolves RAW hazards dynamically in this
       step, and instructions may be sent into execution out of order. This step,
       together with issue, completes the function of the ID step in the simple MIPS
       pipeline.
	 */
	public void readOperands(){
		
	}
	/*
	 * The functional unit begins execution upon receiving operands.
       When the result is ready, it notifies the scoreboard that it has completed
       execution. This step replaces the EX step in the MIPS pipeline and takes multiple
       cycles in the MIPS FP pipeline.
	 */
    public void execute(){
		
	}
    /*
     * Once the scoreboard is aware that the functional unit has completed
       execution, the scoreboard checks for WAR hazards and stalls the completing
       instruction, if necessary.
     */
    public void writeResult(){
    	
    }
    
	public static void main(String args[]) throws IOException{
		String inputFile = args[0];
		int NF = Integer.parseInt(args[1]); // The maximum number of instructions can be fetched in one cycle
		int NQ = Integer.parseInt(args[2]); // The length of the instruction queue
		Memory main = new Memory(); // store memory data 
		
		Simulator simulator = new Simulator(inputFile, main);
		
		simulator.startSimulation(main, NF, NQ);
	}
	
	
}
