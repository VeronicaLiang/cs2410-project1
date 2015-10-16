import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit       Latency for operation         Reservation stations            Instructions executing on the unit
 * 
 * 
 */
public class INT0 {
	private static INT0 instance;
	private INT0 (){}
	public static INT0 getInstance(){
		if(instance==null)
			instance = new INT0();
		return instance;
	}
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are INT0&INT1 stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are FPU  stations.
	 */
	
private static final int LATENCY = 2;
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are MULT stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are BU  stations.
	 */
	public boolean insertInstruction(Instruction instruction){
		for(int i = 1;i<=2;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if((!station.Busy)){
				int h;
				Register register;
				if (instruction.rs.contains("R")){
					register = (Register) Const.integerRegistersStatus.get(instruction.rs);
				}else{
					register = (Register) Const.floatRegistersStatus.get(instruction.rs);
				}
				
				if(register.busy){
					h = register.Reorder;
					if(((ROBItem)Const.ROB.get(h)).ready){
						station.Vj = ((ROBItem)Const.ROB.get(h)).value;
						station.Qj = 0;
					}else{
						station.Qj = h;
					}
				}else{
					station.Vj = register.value;
					station.Qj = 0;
				}
				
				// The same update for rt 
				if(instruction.immediate){//If the rt is an immediate.
					station.Vk = Float.parseFloat(instruction.rt);
					station.Qk = 0;
				}else{//If rt is a register.
					if (instruction.rt.contains("R")){
						register = (Register) Const.integerRegistersStatus.get(instruction.rt);
					}else{
						register = (Register) Const.floatRegistersStatus.get(instruction.rt);
					}
					System.out.println("instruction.rt->"+instruction.rt);
					if(register.busy){
						h = register.Reorder;
						if(((ROBItem)Const.ROB.get(h)).ready){
							station.Vk = ((ROBItem)Const.ROB.get(h)).value;
							station.Qk = 0;
						}else{
							station.Qk = h;//If the value of the register in the ROB not ready yet. Use the Qk to record the index, then get the value.
						}
					}else{
						station.Vk = register.value ;
						station.Qk = 0;
					}
				}
				
				
				if (instruction.rd.contains("R")){
					register = (Register) Const.integerRegistersStatus.get(instruction.rd);
				}else{
					register = (Register) Const.floatRegistersStatus.get(instruction.rd);
				}
				ROBItem item = new ROBItem();
				item.destination = instruction.rd;
				Const.ROB.add(item);
				int b = Const.ROB.indexOf(item);
				Const.lastOfROB = b + 1;
				register.Reorder = b; 
				register.busy = true;
				station.Dest = b;
				station.Busy = true;
				station.latency = 0;
				station.done = false;
				return true;
			}
		}
		
		// the issue is not successful, needs to stall for one cycle.
		return false;
	}
	
	public void execute(){
		for(int i = 1;i<=2;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if((station.latency>0) && (station.latency<LATENCY)){
				station.latency = station.latency +1;
			}else{
				if(station.latency>=LATENCY && station.done){
					//Write result. 
					int b = station.Dest;
					station.Busy = false;
					
					Iterator iterator = Const.reservationStations.entrySet().iterator();
					while(iterator.hasNext()){
					   Map.Entry entry = (Entry) iterator.next();
					   Station s = (Station) entry.getValue();
					   if(s.Qj==b){
						   s.Vj = station.result;s.Qj = 0;
					   }
					   if(s.Qk==b){
						   s.Vk = station.result;s.Qk = 0;
					   }
					   ((ROBItem)Const.ROB.get(b)).value = station.result;
					   ((ROBItem)Const.ROB.get(b)).ready = true;
					}
				}else{
					if((station.Qj==0) && (station.Qk==0)){
						float vk = station.Vk;
						float vj = station.Vj;
						if(station.Op.equals("DADD")){
							station.result = vk +vj;
						}else if(station.Op.equals("DSUB")){
							station.result = vj - vk; 
						}else if(station.Op.equals("DADDI")){
							station.result = vj - vk;  
						}else if(station.Op.equals("AND")){
							station.result = vj + vk;
						}else if(station.Op.equals("ANDI")){
							station.result = vj + vk;
						}else if(station.Op.equals("OR")){
							station.result = ((int)vj) | ((int)vk);
						}else if(station.Op.equals("ORI")){
							station.result = ((int)vj) | ((int)vk);
						}else if(station.Op.equals("SLT")){//if $s < $t $d = 1; advance_pc (4); else $d = 0; advance_pc (4);
							boolean result ;
							result = vj < vk ;
							if(result){
								station.result = 1;
							}else{
								station.result = 0;
							}
						}else if(station.Op.equals("SLTI")){
							boolean result ;
							result = vj < vk ;
							if(result){
								station.result = 1;
							}else{
								station.result = 0;
							}
						}
						station.latency = station.latency+1;
						station.done = true;
					}
				}
				
			}
		}
	}
	
}
