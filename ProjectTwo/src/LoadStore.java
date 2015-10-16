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
	private static final int LATENCY = 5;

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
//					item.destination = instruction.rd;
					Const.ROB.add(item);
					int b = Const.ROB.indexOf(item);
					Const.lastOfROB = b+1;
					register.Reorder = b;
					register.busy = true;
					// replacement + rs  is the address ;
					station.A = replacement ;
					station.loadFlag = 1;
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
				if((station.latency>0) && (station.latency<LATENCY)){
					station.latency = station.latency +1;
				}else{
					if(station.latency>=LATENCY && station.done){
						//Write result.
						if(station.loadFlag==0){
							if(station.Qk == 0){
								((ROBItem)Const.ROB.get(0)).value = station.Vk;
							}
						}else{
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
						}
					}else{
						if((station.Qj==0)){
							if(station.loadFlag>0){//Load operation
								if(station.loadFlag ==1){
									boolean isStoreAhead = false;
									for(int n = (station.Dest-1);n>=0;n--){
										if(((ROBItem)Const.ROB.get(n)).instruction.opco.equals("LD") ||
												((ROBItem)Const.ROB.get(n)).instruction.opco.equals("L.D")){
											isStoreAhead = true;
										}
									}
									if(!isStoreAhead){
										station.loadFlag = 2;

//										station.A = station.Vj + station.A
									}

								}else if(station.loadFlag ==2){
									//TODO read from memory station.A
									station.latency = station.latency+1;
								}
							}else{//store operation
								if(Const.ROB.size()>0){
									ROBItem item = (ROBItem)Const.ROB.get(0);
									if(item.instruction.opco.equals("SD") || item.instruction.opco.equals("S.D")){
//									item.Address = station.Vj + station.A;
										item.destination = "load";
										item.address = (int)station.Vj + station.A;
										station.latency = station.latency+1;
									}
								}
							}

						}
					}
				}
			}
		}
	}
}

