package cs.architecture;

public class Instruction {
	String note;
	String rs;
	String rt;
	String rd;
	String opco;
	int pc;
	
	public Instruction loadInstrs(String line){
		String[] records = line.split("\\t+");
		Instruction instr = new Instruction();
		instr.note = records[0];
		instr.opco = records[1];
		String[] tmp = records[2].split(",\\s+");

		if(instr.opco != "S.D"){
			instr.rt = tmp[0];
		}else{
			instr.rs = tmp[0];
		}
			
		if(instr.opco != "S.D"){
			instr.rs = tmp[1];
		}else{
			instr.rt = tmp[1];
		}
		
		if(tmp.length > 2){
			if(tmp[2] != null && !tmp[2].isEmpty()){
				instr.rd = tmp[2];
			}
		}
		
		return instr;
	}
	
	public void UpdatePC (int pc_value){
		pc = pc_value;
	}
}
