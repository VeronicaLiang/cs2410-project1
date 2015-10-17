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
 * Load/Store 1 for address calculation     3 load buffer +3 store buffer   LD, L.D, SD, S.D
 *
 */
public class LoadStore {
	private static LoadStore instance;
	private LoadStore (){}
	public static LoadStore getInstance(){
		if(instance==null)
			instance = new LoadStore();
		return instance;
	}
	private static final int LATENCY = 1;

	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are MULT stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are BU  stations.
	 */
	public boolean insertInstruction(Instruction instruction){
		int start;
		int end;
		if(instruction.opco.contains("L")){
			start = 7;
			end = 9;
		}else{
			start = 10;
			end = 12;
		}
		for(int i =start;i<=end;i++){
			Station station = (Station) Const.reservationStations.get(i+"");

			if((!station.Busy)){
				int h;//TODO 这里的h是ROB的head entry？？？
				Register register;
				Register rd_register;
				String rs;
				String rd;
				int replacement = 0;
				int rd_replacement = 0;
				// Check whether there is a replacement
				if(instruction.rs.contains("(")){
					String [] tmp = instruction.rs.split("\\(");
					replacement = Integer.parseInt(tmp[0]);
					tmp = tmp[1].split("\\)");
					rs = tmp[0]+"";
				}else{
					rs = instruction.rs;
				}

				if(instruction.rd.contains("(")){
					String [] tmp = instruction.rd.split("\\(");
					rd_replacement = Integer.parseInt(tmp[0]);
					tmp = tmp[1].split("\\)");
					rd = tmp[0]+"";
				}else{
					rd = instruction.rd;
				}

				//all instructions need to check whether RegisterStat[rs] is busy
				if (rs.contains("R")){
					register = (Register) Const.integerRegistersStatus.get(rs);
				}else{
					register = (Register) Const.floatRegistersStatus.get(rs);
				}

				if(rd.contains("R")){
					rd_register = (Register) Const.integerRegistersStatus.get(rd);
				}else{
					rd_register = (Register) Const.floatRegistersStatus.get(rd);
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

				if(instruction.opco.equals("LD") || instruction.opco.equals("L.D")){
					ROBItem item = new ROBItem();
					item.instruction = instruction;
					item.destination = instruction.rd;
					Const.ROB.add(item);
					int b = Const.ROB.indexOf(item);
					Const.lastOfROB = b+1;
					rd_register.Reorder = b;
					rd_register.busy = true;
					// replacement + rs  is the address ;
					station.A = replacement ;
					station.loadFlag = 1;
					station.Dest = b;
				}else{
					station.A = rd_replacement ;
				}

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
		for(int i = 7;i<=12;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if(station.Busy){
				if(station.latency<LATENCY || !station.done){
					station.latency = station.latency +1;
					// a load instruction
					if(station.loadFlag == 0){
						if((station.Qj==0) && !station.done) {
							station.result = station.Vj + station.A;
							station.done = true;
						}
					}else{
						if((station.Qj == 0) && !station.done) {
							Memory test = Memory.getInstance();
							int address = (int) (station.Vj + station.A);
							float f = (float)test.getData().get(address);
							station.result = f;
							station.done = true;
						}
					}
				}else if(station.latency>=LATENCY && station.done){
						//Write result.
					station.Busy = false;
					if(station.loadFlag==0){
						if(station.Qk == 0){
							((ROBItem)Const.ROB.get(station.Dest)).value = station.result;
						}
					}else{
						int b = station.Dest;
						boolean isStoreAhead = false;
						for (int j = Const.firstOfROB; j < b; j++) {
							if (((ROBItem) Const.ROB.get(j)).instruction.opco.equals("SD") || ((ROBItem) Const.ROB.get(i)).instruction.opco.equals("S.D")) {
								isStoreAhead = true;
							}
						}
						if (!isStoreAhead) {
							station.A = (int) station.Vj + station.A;
							Memory test = Memory.getInstance();
							float f = (float)test.getData().get(b);
							((ROBItem) Const.ROB.get(station.Dest)).value = f;
						}
					}
				}
			}
		}
	}

}

