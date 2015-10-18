import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Unit    Latency for operation                Reservation stations  Instructions executing on the unit
 * BU      1 (condition and target evaluation)  2                     BEQZ, BNEZ, BEQ, BNE
 * BU: Branch Unit, used to 
 */
public class BU {
	private static BU instance;
	private BU (){}
	public static BU getInstance(){
		if(instance==null)
			instance = new BU();
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
	   beq	000100	rs	rt	immediate	 beq $1,$2,10	 if($1==$2)  goto PC+4+40	 if (rs == rt) PC <- PC+4 + (sign-extend)immediate<<2 
      bne	000101	rs	rt	immediate	 bne $1,$2,10	 if($1!=$2)  goto PC+4+40	 if (rs != rt) PC <- PC+4 + (sign-extend)immediate<<2 
      BEQZ  条件转移指令，当寄存器中内容为0时转移发生  BEQZ R1,0
      BENZ  条件转移指令，当寄存器中内容不为0时转移发生 BNEZ R1,0
      BEQ   条件转移指令，当两个寄存器内容相等时转移发生 BEQ R1,R2
      BNE 条件转移指令，当两个寄存器中内容不等时转移发生 BNE R1,R2
	 */
	public boolean insertInstruction(Instruction instruction){
		for(int i = 18;i<=19;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if((!station.Busy)){
				int h;
//				检查rd register
				Register register;
				register = (Register) Const.integerRegistersStatus.get(instruction.rd);
				
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
				
				//, , BEQ, BNE
				//检查 rs register if the opco is beqz or benz, rs holds the loops index, vk could be nothing.
				if(instruction.opco.equals("BEQZ") || instruction.opco.equals("BNEZ")){
					station.A = Integer.parseInt(instruction.rt) ;
				}else{
					station.A = Integer.parseInt(instruction.rt) ;//offset
					
					register = (Register) Const.integerRegistersStatus.get(instruction.rs);
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
				}
				
				
//				检查rd register
				register = (Register) Const.integerRegistersStatus.get(instruction.rd);
				
				ROBItem item = new ROBItem();
				item.destination = instruction.rd;
				item.instruction = instruction;
				Const.ROB.add(item);
				int b = Const.ROB.indexOf(item);
				Const.lastOfROB = b + 1;
				//register.Reorder = b; 
				//register.busy = true;
				station.Op = instruction.opco;
				station.Dest = b;
				station.Busy = true;
				station.latency = 0;
				station.done = false;
				station.wbDone = false;
				return true;
			}
		}
		
		// the issue is not successful, needs to stall for one cycle.
		return false;
	}
	/*
	 *beq	000100	rs	rt	immediate	 beq $1,$2,10	 if($1==$2)  goto PC+4+40	 if (rs == rt) PC <- PC+4 + (sign-extend)immediate<<2 
      bne	000101	rs	rt	immediate	 bne $1,$2,10	 if($1!=$2)  goto PC+4+40	 if (rs != rt) PC <- PC+4 + (sign-extend)immediate<<2 
      BEQZ  条件转移指令，当寄存器中内容为0时转移发生  BEQZ R1,0
      BENZ  条件转移指令，当寄存器中内容不为0时转移发生 BNEZ R1,0
      BEQ   条件转移指令，当两个寄存器内容相等时转移发生 BEQ R1,R2
      BNE 条件转移指令，当两个寄存器中内容不等时转移发生 BNE R1,R2
	 */
	public void execute(){
		boolean isExecute = false;
		boolean isWB = false;
		for(int i = 18;i<=19;i++){
			Station station = (Station) Const.reservationStations.get(i+"");
			if(station.Busy){
				if(station.latency<LATENCY || !station.done){
					station.latency = station.latency +1;
					//System.out.println("station.Qj--->"+station.Qj+"   station.Qk--->"+station.Qk);
					/*
					 *beq	000100	rs	rt	immediate	 beq $1,$2,10	 if($1==$2)  goto PC+4+40	 if (rs == rt) PC <- PC+4 + (sign-extend)immediate<<2 
				      bne	000101	rs	rt	immediate	 bne $1,$2,10	 if($1!=$2)  goto PC+4+40	 if (rs != rt) PC <- PC+4 + (sign-extend)immediate<<2 
				      BEQZ  条件转移指令，当寄存器中内容为0时转移发生  BEQZ R1,0
				      BENZ  条件转移指令，当寄存器中内容不为0时转移发生 BNEZ R1,0
				      BEQ   条件转移指令，当两个寄存器内容相等时转移发生 BEQ R1,R2
				      BNE 条件转移指令，当两个寄存器中内容不等时转移发生 BNE R1,R2
					 */
					if((station.Qj==0) && (station.Qk==0) && !station.done && !isExecute){
						float vk = station.Vk;
						float vj = station.Vj;
						
						if(station.Op.equals("BEQZ")){
							if(vj==0){
								station.result = 1; 
							}else{
								station.result = 0; 
							}
						}else if(station.Op.equals("BNEZ")){
							if(vj!=0){
								station.result = 1; 
							}else{
								station.result = 0; 
							}
						}else if(station.Op.equals("BEQ")){
							if(vk==vj){
								station.result = 1; 
							}else{
								station.result = 0; 
							}
						}else if(station.Op.equals("BNE")){
							
							if(vk!=vj){
								station.result = 1;
							}else{
								station.result = 0; 
							}
						}
						station.latency = station.latency+1;
						station.done = true;
					}
				}else if(station.latency>=LATENCY && !station.wbDone && station.done && !isWB && Const.NB > 0){
					//Write result. 
					int b = station.Dest;
					
					((ROBItem)Const.ROB.get(b)).value = station.result;
					((ROBItem)Const.ROB.get(b)).offset = station.A;
					((ROBItem)Const.ROB.get(b)).ready = true;
					station.Busy = false;
					isWB = true;
					station.wbDone = true;
					Const.NB--;
				}
			}
			
			
		}
	}
}
