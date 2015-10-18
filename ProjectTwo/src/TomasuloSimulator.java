
import java.io.*;
import java.util.*;

public class TomasuloSimulator {
	BU buUnit;// BU unit instance;
	FPU fpuUnit;// FPU unit instance;
	INT0 int0Unit;//INT0 unit instance
	INT1 int1Unit;//INT1 unit instance
	LoadStore loadStoreUnit;// Load and store unit instance
	MULT multUnit;// MULT unit instance
	Memory memory;
	
	
	
	boolean finishedFlag = true;//flag for whether this simulation comes to its end.
	int NF ; // The maximum number of instructions can be fetched in one cycle
	int NQ ; // The length of the instruction queue
	int NI ; // The maximum number of instructions can be decoded in one cycle
	int ND ; // The length of the Decoded instruction queue
	int NW = 4;//The maximum number of instructions can be issued every clock cycle to reservation stations. 
	
	
	int pc = 0; //initialize the program counter 
	
	public TomasuloSimulator(String instructionFile){
		//Initiate all the units
		buUnit = BU.getInstance();
		fpuUnit = FPU.getInstance();
		int0Unit = INT0.getInstance();
		int1Unit = INT1.getInstance();
		loadStoreUnit = LoadStore.getInstance();
		multUnit = MULT.getInstance();
		memory = Memory.getInstance();

//		fetch all the instruction from instruction file.
		String line = null;
		try {
			FileReader filereader = new FileReader (instructionFile);
			BufferedReader bufferedreader = new BufferedReader (filereader);
			boolean flag = false; // indicate when the data is start loading
			while ((line = bufferedreader.readLine()) != null){
				if(flag){
					memory.loadData(line);
				}else{
					if(line.contains("DATA")){
						flag = true;
					}else{
						if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")){
							// skip the empty lines.
						}else{
							Instruction instr = new Instruction();
							instr = instr.loadInstrs(line);
							memory.loadInstruction(instr);
						}
					}
				}
			}
			List instrs = memory.getInstrs();
			Instruction ins,ins2;
			for (int i = 0; i < instrs.size(); i++) {
				ins = (Instruction) instrs.get(i);
				if (ins.opco.equals("BEQZ") || ins.opco.equals("BNEZ") || ins.opco.equals("BEQ") || ins.opco.equals("BNE")) {
					for (int j= 0; j < instrs.size(); j++) {
						ins2 = (Instruction) instrs.get(j);
						if (ins.rt.equals(ins2.note)) {
							ins.rt = j+"";
						}
					}
				}
			}
		
//			System.out.println(main.getInstrs().size());
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
	public void startSimulation(int NF, int NQ, int NI, int ND){
		this.NF = NF;
		this.NQ = NQ;
		this.NI = NI;
		this.ND = ND;
		
		finishedFlag = false;
		int clock_cycle = 2;
		
		BranchTargetBuffer BTBuffer = new BranchTargetBuffer();
		LinkedList FQueue = new LinkedList(); // Fetched Instructions Queue
		LinkedList DQueue = new LinkedList(); // Decoded Instructions Queue (actually, the decode is not needed, only check for branch)
		Const.ROB.add(new ROBItem()); // add an item to ROB, so that we can use 1 as the first index
		
		while(!finishedFlag){//Clock cycles loop
			
			/**
			 * If the instruction queue is not full, and there are instructions not finished,
			 * Fetch instructions. 
			 * In one clock cycle, the maximum number of fetching is NF. 
			 */
			int fetched = 0;
			while((FQueue.size() < NQ) && (fetched < NF) &&(pc < memory.getInstrs().size())){
				((Instruction)memory.getInstrs().get(pc)).UpdatePC(pc); // Instruction needs to have a feature called pc, so that can check whether the BTBuffer prediction is wrong.
				FQueue.add(memory.getInstrs().get(pc));
				if(BTBuffer.Getbuffer()[pc%32][0] != -1){
					pc = BTBuffer.Getbuffer()[pc%32][0]; // if there is an entry in BTBuffer, use the predicted pc, otherwise, pc ++
				}else{
					pc++;
				}
				fetched++;
			}

			
			/**
			 * Decode the instruction
			 * If the instruction is not a branch, but find in BTBuffer, need to deleted the following instructions in the iqueue, and refetch. 
			 */
			int decoded = 0;
			while((DQueue.size() <= NI) && (decoded < ND)&&(!FQueue.isEmpty()) ){
				Instruction next = (Instruction) FQueue.poll();
				DQueue.add(next);
				if (!next.opco.equals("BEQZ") && !next.opco.equals("BNEZ") && !next.opco.equals("BEQ") && !next.opco.equals("BNE")&&(BTBuffer.Getbuffer()[next.pc%32][0] != -1)){
					// If the instruction is not a branch, but has entry in BTBuffer
					FQueue.clear();
					pc = next.pc++;
					if(BTBuffer.Getbuffer()[next.pc%32][1] == 0) {
						BTBuffer.Getbuffer()[next.pc%32][1] = 1; // allow the first time is wrong
					}else{
						// reset the entry
						BTBuffer.Getbuffer()[next.pc%32][0] = -1;
						BTBuffer.Getbuffer()[next.pc%32][1] = 0;
					}
				}
			}
			
			if(commit(BTBuffer)){
				DQueue.clear();
				FQueue.clear();
			}

			execute();
			issue(DQueue);
			
			clock_cycle ++;
//			System.out.println(clock_cycle);
//			System.out.println("pc->"+pc+"  getInstrs->"+memory.getInstrs().size()+"  Const.lastOfROB->"+Const.lastOfROB+"  firstOfROB->"+Const.firstOfROB);
			
			
			System.out.println("After Clock Cycle " + clock_cycle +":");
			System.out.println("------------------------------");
			System.out.println("Registers Status:");
			for (int i = 0; i < 32; i++){
				String str = "R"+i+": "+(int)((Register)Const.integerRegistersStatus.get("R"+i)).value;
				int length = str.length();
				for (int m = 0; m < 15-length; m++) {
					str = str + " ";
				}
				//str = str + str.length();
				str = str + "F"+i+": "+((Register)Const.floatRegistersStatus.get("F"+i)).value;
				System.out.println(str);
			}
			System.out.println("------------------------------");
			System.out.println("Reservation Stations (Busy) Status:");
			boolean printFlag = true;
			for (int i = 1; i <= 19; i++) {
				Station station = (Station) Const.reservationStations.get(i+"");
				if (station.Busy == true) {
					String result = "name-->"+station.name+" Op-->"+station.Op+" Qj-->"+station.Qj+" Vj-->"+station.Vj+" Qk-->"+station.Qk+" Vk-->"+station.Vk+" result-->"+station.result;
					System.out.println(result);
					printFlag = false;
				}
				
			}
			if (printFlag) {
				System.out.println("All Reservation Stations are idle.");
			}
			printFlag = true;
			System.out.println("------------------------------");
			System.out.println("ROB Status:");
			for (int i = Const.firstOfROB; i < Const.lastOfROB; i++) {
	    		System.out.println("Opco-->"+((ROBItem)Const.ROB.get(i)).instruction.opco + " pc-->"+((ROBItem)Const.ROB.get(i)).instruction.pc+" Reorder-->"+i);
	    		printFlag = false;
			}
			if (printFlag) {
				System.out.println("No item in ROB.");
			}
			System.out.println("------------------------------");
			System.out.println("Memory Status:");
			//System.out.println("Mem[R2]: "+ memory.getData().get((int)((Register)Const.integerRegistersStatus.get("R2")).value));
			printFlag = true;
			TreeMap teMap=new TreeMap(); 
			Iterator it = memory.getData().entrySet().iterator();  
			while (it.hasNext()) {  
				Map.Entry e = (Map.Entry) it.next();  
				teMap.put(e.getKey(), e.getValue());
			}
			it = teMap.entrySet().iterator();  
			while (it.hasNext()) {  
				Map.Entry e = (Map.Entry) it.next();
				System.out.println("Mem[" + e.getKey() + "]: "  + e.getValue());  
				printFlag = false;
			}
			
			if (printFlag) {
				System.out.println("Memory is empty.");
			}
			System.out.println("------------------------------");
			System.out.println();
			if(pc >= memory.getInstrs().size() && Const.lastOfROB - Const.firstOfROB == 0){
				finishedFlag = true;
				System.out.println("Total Clock Cycle are "+clock_cycle);
			}
		}
		
		//Destroying all the resources. TODO
	}
	/*
	 * Get an instruction from the instruction queue. Issue the instruction if
       there is an empty reservation station and an empty slot in the ROB; send the
       operands to the reservation station if they are available in either the registers
       or the ROB. Update the control entries to indicate the buffers are in use. The
       number of the ROB entry allocated for the result is also sent to the reservation
       station, so that the number can be used to tag the result when it is placed
       on the CDB. If either all reservations are full or the ROB is full, then instruction
       issue is stalled until both have available entries.

	 */
	public void issue(LinkedList DQueue){
		int issue_count = 0;
		boolean halt = false;
		boolean issueFPU = false;
		boolean issueINT0 = false;
		boolean issueINT1 = false;
		boolean issueLS = false;
		boolean issueBU = false;
		boolean issueMULT = false;
		while((issue_count < this.NW) &&(!halt)){
			if((Const.lastOfROB - Const.firstOfROB)>=Const.NR){//If ROB' size equals or is greater than NR , stop issuing instructions.
				halt = true;
				return;
			}
		//Check no more than NW instructions in the instructions waiting queue
			if(DQueue.size()!=0){
				Instruction instruction = (Instruction) DQueue.getFirst();
				String unit = (String)Const.unitsForInstruction.get(instruction.opco);
				boolean isSuccessful = false;
				if(unit.equals("FPU") && !issueFPU){
					isSuccessful = fpuUnit.insertInstruction(instruction);
					issueFPU = true;
				}else if(unit.equals("INT0") && (!issueINT0 || !issueINT1)){
					if (!issueINT0){
						isSuccessful = int0Unit.insertInstruction(instruction);
						issueINT0 = true;
						if(!isSuccessful && !issueINT1){
							isSuccessful = int1Unit.insertInstruction(instruction);
							issueINT1 = true;
						}
					} else if (!issueINT1) {
						isSuccessful = int1Unit.insertInstruction(instruction);
						issueINT1 = true;
					}
					
					
				}else if(unit.equals("LoadStore") && !issueLS){
					isSuccessful = loadStoreUnit.insertInstruction(instruction);
					issueLS = true;
				}else if(unit.equals("BU") && !issueBU){
					isSuccessful = buUnit.insertInstruction(instruction);
					issueBU = true;
				}else if(unit.equals("MULT") && !issueMULT){
					isSuccessful = multUnit.insertInstruction(instruction);
					issueMULT = true;
				}
				if(!isSuccessful){
					halt = true;
					return;
				}
				DQueue.poll(); // remove it if issued
				issue_count++;
			} else {
				return;
			}
			
			
		}
      }

	
	
	
	/*
	 *If one or more of the operands is not yet available, monitor the
      CDB while waiting for the register to be computed. This step checks for
      RAW hazards. When both operands are available at a reservation station, execute
      the operation. Instructions may take multiple clock cycles in this stage,
      and loads still require two steps in this stage. Stores need only have the base
      register available at this step, since execution for a store at this point is only
      effective address calculation.
	 */
    public void execute(){
		//Iterate resvervation stations table, and execute every station.
    	Const.NB = 4;
    	fpuUnit.execute();
    	int0Unit.execute();
    	int1Unit.execute();
    	loadStoreUnit.execute();
    	multUnit.execute();
    	buUnit.execute();
	}
    
    /*
     * This is the final stage of completing an instruction, after which only
       its result remains. (Some processors call this commit phase ��completion�� or
       ��graduation.��) There are three different sequences of actions at commit depending
       on whether the committing instruction is a branch with an incorrect prediction,
       a store, or any other instruction (normal commit). The normal commit case
       occurs when an instruction reaches the head of the ROB and its result is present
       in the buffer; at this point, the processor updates the register with the result and
       removes the instruction from the ROB. Committing a store is similar except
       that memory is updated rather than a result register. When a branch with incorrect
       prediction reaches the head of the ROB, it indicates that the speculation
       3.6 Hardware-Based Speculation �� 187
       was wrong. The ROB is flushed and execution is restarted at the correct successor
       of the branch. If the branch was correctly predicted, the branch is finished.
       Once an instruction commits, its entry in the ROB is reclaimed and the register
       or memory destination is updated, eliminating the need for the ROB entry.
     */
    public boolean commit(BranchTargetBuffer btb){
    	
		int NC = 4;
		int bus_count = 0;
		boolean flushflag = false;
    	while(Const.lastOfROB - Const.firstOfROB > 0 && bus_count < NC ){
    		int h = Const.firstOfROB;  // always commit the first item in ROB
			ROBItem item = (ROBItem)Const.ROB.get(h);
			//System.out.println("item.ready->"+item.ready+"   item.opco->"+item.instruction.opco+"   rob.size->"+Const.ROB.size());
    		if(item.ready){
    			String d = item.destination;
    			if(item.instruction.opco.equals("BEQZ") || item.instruction.opco.equals("BNEZ")
						||item.instruction.opco.equals("BNE")||item.instruction.opco.equals("BEQ")){
    				//System.out.println("item.value-->"+item.value);
					if(item.value == 1){ // change pc
						if(btb.Getbuffer()[item.instruction.pc % 32][0] == -1){
							// If there is no entry in the Branch Target Buffer
							flushflag = true;
							Const.ROB.clear();
							Const.ROB.add(new ROBItem());
							// clear reservation station
							Const.reservationStations = new HashMap();
							Station station;
							for(int i = 1;i<=19;i++){
								station = new Station();
								station.name = i+"";
								Const.reservationStations.put(station.name ,station);
							}

							for (int i = 0; i < 32; i++){
								((Register)Const.integerRegistersStatus.get("R"+i)).busy = false ;
								((Register)Const.floatRegistersStatus.get("F"+i)).busy = false;
							}

							Const.firstOfROB = 1;
							Const.lastOfROB = 1;
							//update the buffer
							btb.Getbuffer()[item.instruction.pc % 32][0] = item.offset;
							btb.Getbuffer()[item.instruction.pc % 32][1] = 0;
							pc = item.offset;
						}else{
    						int predicted = btb.Getbuffer ()[item.instruction.pc % 32][0]; // the predicted pc
							if (item.offset != predicted) {// If branch is mispredicted.
								flushflag = true;
								Const.ROB.clear();
								Const.reservationStations = new HashMap();
								Station station;
								for(int i = 1;i<=19;i++){
									station = new Station();
									station.name = i+"";
									Const.reservationStations.put(station.name ,station);
								}

								for (int i = 0; i < 32; i++){
									((Register)Const.integerRegistersStatus.get("R"+i)).busy = false ;
									((Register)Const.floatRegistersStatus.get("F"+i)).busy = false;
								}


								Const.ROB.add(new ROBItem());
								Const.firstOfROB = 1;
								Const.lastOfROB = 1;

								if (btb.Getbuffer()[item.instruction.pc % 32][1] == 1) {
									// update the branch-target-buffer
									btb.Getbuffer()[item.instruction.pc % 32][0] = (int) item.offset;
									btb.Getbuffer()[item.instruction.pc % 32][1] = 0;
								} else {
									// allow make mistakes twice.
									btb.Getbuffer()[item.instruction.pc % 32][1] = 0;
								}
								pc = item.offset;
							}
							Const.firstOfROB++;
						}
    				}else{
						pc = item.instruction.pc + 1;
						if(btb.Getbuffer ()[item.instruction.pc % 32][0] != -1){
							flushflag = true;
							Const.ROB.clear();
							Const.reservationStations = new HashMap();
							Station station;
							for(int i = 1;i<=19;i++){
								station = new Station();
								station.name = i+"";
								Const.reservationStations.put(station.name ,station);
							}

							Const.ROB.add(new ROBItem());
							Const.firstOfROB = 1;
							Const.lastOfROB = 1;
							for (int i = 0; i < 32; i++){
								((Register)Const.integerRegistersStatus.get("R"+i)).busy = false ;
								((Register)Const.floatRegistersStatus.get("F"+i)).busy = false;
							}

							if(btb.Getbuffer()[item.instruction.pc % 32][1] == 1){
								btb.Getbuffer()[item.instruction.pc % 32][0] = pc;
								btb.Getbuffer()[item.instruction.pc % 32][1] = 0;
							}else{
								btb.Getbuffer()[item.instruction.pc % 32][1] = 1;
							}
						}
					}
    				bus_count++;
    			}else if(item.instruction.opco.equals("S.D") || item.instruction.opco.equals("SD")){
    				if (item.instruction.opco.equals("SD")) {
    					memory.getData().put(item.address, ((int) item.value)+"");
    				} else {
    					memory.getData().put(item.address, item.value+"");
    				}
    				
					bus_count++;
					Const.firstOfROB++;
    			}else{
					//TODO update the registers
					if(d.contains("R")){
						((Register) Const.integerRegistersStatus.get(d)).value = item.value;
					}else{
						((Register) Const.floatRegistersStatus.get(d)).value = item.value;
					}
					bus_count++;
					Const.firstOfROB++;
    			}
    			item.busy = false;

    			if(!item.instruction.opco.equals("S.D") && !item.instruction.opco.equals("SD")
						&& !item.instruction.opco.equals("BEQZ") && !item.instruction.opco.equals("BNEZ")
						&& !item.instruction.opco.equals("BNE") && !item.instruction.opco.equals("BEQ")){
        			if(d.contains("R")){
        				if(((Register)Const.integerRegistersStatus.get(d)).Reorder==h){
                        	((Register)Const.integerRegistersStatus.get(d)).busy = false;
        				}
        			} else {
        				if(((Register)Const.floatRegistersStatus.get(d)).Reorder==h){
            				((Register)Const.floatRegistersStatus.get(d)).busy = false;
            			}
        			}
    			}

    		} else {
    			break;
    		}
    		
    	}
		return flushflag;
    }

    public static void main(String args[]) throws IOException{
		String inputFile = args[0];
		int NF = Integer.parseInt(args[1]); // The maximum number of instructions can be fetched in one cycle
		int NQ = Integer.parseInt(args[2]); // The length of the instruction queue
		int NI =  Integer.parseInt(args[3]); // The maximum number of instructions can be decoded in one cycle
		int ND = Integer.parseInt(args[4]); // The length of the Decoded instruction queue
		
		TomasuloSimulator simulator = new TomasuloSimulator(inputFile);
		
		simulator.startSimulation( NF, NQ, NI, ND);
    	
	}
	
	
}
