package cs.architecture;

import java.io.*;

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
	
	boolean finishedFlag = true;//flag for whether this simulation comes to its end.
	
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
	/*
	 * Start this simulation with a loop representing clock cycles.
	 */
	public void startSimulation(){
		finishedFlag = false;
		while(finishedFlag){//Clock cycles loop
			
		}
	}
	
	public static void main(String args[]) throws IOException{
		String inputFile = args[0];
		// Reading the benchmark files
		String line = null;
		try {
			FileReader filereader = new FileReader (inputFile);
			BufferedReader bufferedreader = new BufferedReader (filereader);
			boolean flag = false; // indicate when the data is start loading
			Memory main = new Memory();
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
		
			System.out.println(main.getData().size());
			bufferedreader.close();
		}
		catch (FileNotFoundException ex){
			System.out.println("Unable to open file '" + inputFile + "'");
		}
		catch (IOException ex){
			System.out.println("Error reading file '" + inputFile + "'");
		}
	}
	
	
}
