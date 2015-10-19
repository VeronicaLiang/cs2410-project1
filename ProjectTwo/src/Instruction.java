

public class Instruction {
	String note;
	String rs;
	String rt;
	String rd;
	String opco;
	String text; // output original instruction
	/*
	 * Qj, Qk—The reservation stations that will produce the corresponding source
       operand; a value of zero indicates that the source operand is already available
       in Vj or Vk, or is unnecessary.
	 */
//	String immediate;
	boolean immediate = false;
	
	int pc;
	
	public Instruction loadInstrs(String line){
		String[] records = line.split("\\t+");
		Instruction instr = new Instruction();
		instr.text = line.trim().replace("\t", " ");
		if (records[0].equals("")){
			instr.note = records[0];
		} else {
			instr.note = records[0].substring(0, records[0].length()-1);
		}
		
		instr.opco = records[1];
		String[] tmp = records[2].split(",\\s+");

		// The instruction S.D has different style
		if(instr.opco.equals("S.D")||instr.opco.equals("SD")){
			instr.rd = tmp[1];
			instr.rs = tmp[0];
		}else{
			instr.rd = tmp[0];
			instr.rs = tmp[1];
		}

		//
		if(tmp.length > 2){
			if(tmp[2] != null && !tmp[2].isEmpty()){
				instr.rt = tmp[2];
			}
			if(instr.opco.contains("I")){
				instr.immediate = true;
			}
		}

		if (instr.opco.equals("BNEZ") || instr.opco.equals("BEQZ")){
			instr.rt = tmp[1];
		}
		
		return instr;
	}
	
	public void UpdatePC (int pc_value){
		pc = pc_value;
	}
}
