

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
		String[] records = line.split("\\t+");
		String[] tmp = records[records.length-1].split("\\)\\s+\\=\\s+");
		String number = tmp[tmp.length-1];
		String[] tmp2 = tmp[0].split("\\(");
		int index = Integer.parseInt(tmp2[tmp2.length-1]);
		data.put(index, number);
	}
	
	public Hashtable getData(){
		return data;
	}

}
