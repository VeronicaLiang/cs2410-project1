

import java.util.*;

public class Memory {

	private static Memory instance;
	private Memory (){
	}
	public static Memory getInstance(){
		if(instance==null){
			instance = new Memory();
		}
		return instance;
	}
	private static List instrs = new ArrayList(); // the array's index is for pc
	private static Hashtable data = new Hashtable(); 
	
	public void loadInstruction(Instruction newInstr){
		instrs.add(newInstr);
	}
	
	public List getInstrs(){
		return instrs;
	}
	
	public void loadData(String line){
		// when parse the string, hard coded... 
		String[] records = line.split("=");
		String number = records[1].trim();
		//System.out.println(records[0].trim());
		String[] tmp = records[0].trim().split("\\(");
		String[] tmp2 = tmp[1].split("\\)");
		int index = Integer.parseInt(tmp2[0].trim());
		data.put(index, number);
	}
	
	public Hashtable getData(){
		return data;
	}

}
