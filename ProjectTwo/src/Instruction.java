

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
		String[] records = line.split(",");
		String[] pre = records[0].split("\\s+");
		String tmp0 = pre[2].trim();
		String tmp1 = records[1].trim();
		String tmp2 = "";
		if (records.length>2){
			tmp2 = records[2].trim();
		}
		
		
		Instruction instr = new Instruction();
		String text = pre[0]+"\t"+pre[1]+"\t"+tmp0+", "+tmp1+", "+tmp2;
		text = text.trim();
		if (text.endsWith(",")){
			text = text.substring(0, text.length()-1);
		}
		instr.text = text;
		System.out.println(text);
		
		if (pre[0].equals("")){
			instr.note = pre[0];
		} else {
			instr.note = pre[0].substring(0, pre[0].length()-1);
		}
		
		instr.opco = pre[1];
		//String tmp = line.substring(beginIndex);
		
		

		// The instruction S.D has different style
		if(instr.opco.equals("S.D")||instr.opco.equals("SD")){
			instr.rd = tmp1.trim();
			instr.rs = tmp0.trim();
		}else{
			instr.rd = tmp0.trim();
			instr.rs = tmp1.trim();
		}

		//
		if(!tmp2.equals("")){
			instr.rt = tmp2;
			if(instr.opco.contains("I")){
				instr.immediate = true;
			}
		}

		if (instr.opco.equals("BNEZ") || instr.opco.equals("BEQZ")){
			instr.rt = tmp1.trim();
		}
		
		return instr;
	}
	
	public void UpdatePC (int pc_value){
		pc = pc_value;
	}
}
