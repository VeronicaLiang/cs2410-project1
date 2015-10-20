import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation              Reservation stations      Instructions executing on the unit
 * FPU     4 (pipelined FP multiply/add)      5                         ADD.D, SUB.D, MUL.D,DIV.D
 *         4 (non-pipelined divide)
 * FPU:    Float Point Unit
 */
public class FPU {
	
	private static FPU instance;
	private FPU (){}
	public static FPU getInstance(){
		if(instance==null)
			instance = new FPU();
		return instance;
	}
	private static final int LATENCY = 4;
	
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are MULT stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are BU  stations.
	 */
	public boolean insertInstruction(Instruction instruction){
		// we can issue a divide, but we cannot execute when this unit has divide.
		//if (hasDivide()) {
		//	return false;
		//}
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
				item.instruction = instruction;
				Const.ROB.add(item);
				int b = Const.ROB.indexOf(item);
				register.Reorder = b; 
				register.busy = true;
				Const.lastOfROB = b + 1;
				station.Dest = b;
				station.Busy = true;
				station.latency = 0;
				station.done = false;
				station.wbDone = false;
				station.Op = instruction.opco;
				station.status = "issued";
				station.text = instruction.text;
				station.newIssued = true;
				return true;
			}
		}
		
		// the issue is not successful, needs to stall for one cycle.
		return false;
	}
	
	public void execute(){
		boolean isExecute = false;
		boolean isWB = false;
		for(int i = 13;i<=17;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if(station.Busy && !station.newIssued){
				// if latency == 0 and not div.d, this instruction issued after div.d.
				// so if there is a divide in unit, just waiting (skip to next station)
				if (station.latency == 0 && !station.Op.equals("DIV.D") && this.hasDivide()) {
					continue;
				} else if ((station.latency < LATENCY || !station.done) && !station.Op.equals("DIV.D")) {
					station.latency = station.latency + 1;
					if ((station.Qj == 0) && (station.Qk == 0) && !station.done && !isExecute) {
						float vk = station.Vk;
						float vj = station.Vj;
						if (station.Op.equals("ADD.D")) {
							station.result = vk + vj;
						} else if (station.Op.equals("SUB.D")) {
							station.result = vj - vk;
						} else if (station.Op.equals("MUL.D")) {
							station.result = vj * vk;
						}
						station.done = true;
						isExecute = true;
						station.status = "executed";
					}
				} else if ((station.latency < LATENCY || !station.done) && station.Op.equals("DIV.D")) {
					if (!this.unitBusy(i)) {
						station.latency = station.latency + 1;
					}
					if ((station.Qj == 0) && (station.Qk == 0) && !station.done && !isExecute) {
						float vk = station.Vk;
						float vj = station.Vj;
						station.result = vj / vk;
						station.done = true;
						isExecute = true;
					}
				} else if (station.latency >= LATENCY && !station.wbDone && station.done && !isWB && Const.NB > 0 && Const.NC > 0) {
					// Write result.
					int b = station.Dest;

					Iterator iterator = Const.reservationStations.entrySet()
							.iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Entry) iterator.next();
						Station s = (Station) entry.getValue();
						if (s.Qj == b) {
							s.Vj = station.result;
							s.Qj = 0;
						}
						if (s.Qk == b) {
							s.Vk = station.result;
							s.Qk = 0;
						}
					}
					((ROBItem) Const.ROB.get(b)).value = station.result;
					((ROBItem) Const.ROB.get(b)).ready = true;
					((ROBItem)Const.ROB.get(b)).newReady = true;
					station.Busy = false;
					isWB = true;
					station.wbDone = true;
					Const.NB--;
					Const.NC--;
				}
			}
		}
	}
	
	public boolean hasDivide () {
		for(int i = 13;i<=17;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if (station.Op.equals("DIV.D")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean unitBusy(int index) {
		for(int i = 13;i<=17;i++){
			if(i != index){
				Station station = (Station) Const.reservationStations.get(i+"");
				if (station.Busy && station.latency > 0) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	
}
