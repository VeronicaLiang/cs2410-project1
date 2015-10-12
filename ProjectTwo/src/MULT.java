import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation              Reservation stations      Instructions executing on the unit
 * 
 * MULT    4 (integer multiply)               2                         DMUL
 * 
 */
public class MULT {
	private static MULT instance;
	private MULT (){}
	public static MULT getInstance(){
		if(instance==null){
			instance = new MULT();
		}
		return instance;
	}
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
		for(int i = 13;i<=17;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if((!station.Busy)){
				int h;//TODO 这里的h是ROB的head entry？？？
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
				if (instruction.rt.contains("R")){
					register = (Register) Const.integerRegistersStatus.get(instruction.rt);
				}else{
					register = (Register) Const.floatRegistersStatus.get(instruction.rt);
				}
				
				if(register.busy){
					h = register.Reorder;
					if(((ROBItem)Const.ROB.get(h)).ready){
						station.Vk = ((ROBItem)Const.ROB.get(h)).value;
						station.Qk = 0;
					}else{
						station.Qk = h;
					}
				}else{
					station.Vk = register.value ;
					station.Qk = 0;
				}
				
				if (instruction.rt.contains("R")){
					register = (Register) Const.integerRegistersStatus.get(instruction.rd);
				}else{
					register = (Register) Const.floatRegistersStatus.get(instruction.rd);
				}
				ROBItem item = new ROBItem();
				item.destination = instruction.rd;
				Const.ROB.add(item);
				int b = Const.ROB.indexOf(item);
				register.Reorder = b; 
				register.busy = true;
				
				return true;
			}
		}
		
		// the issue is not successful, needs to stall for one cycle.
		return false;
	}
	
	public void execute(){
		for(int i = 13;i<=17;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if((station.latency>0) && (station.latency<LATENCY)){
				station.latency = station.latency +1;
			}else{
				if(station.latency==LATENCY){
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
					//TODO Check whether all the operands are available.
					if((station.Qj==0) && (station.Qk==0)){
						float vk = station.Vk;
						float vj = station.Vj;
						if(station.Op.equals("ADD.D")){
							station.result = vk +vj;
						}else if(station.Op.equals("SUB.D")){
							station.result = vj - vk; 
						}else if(station.Op.equals("MUL.D")){
							station.result = vj * vk; 
						}else if(station.Op.equals("DIV.D")){
							station.result = vj / vk;
						}
						station.latency = station.latency+1;
					}
				}
				
			}
		}
	}
}
