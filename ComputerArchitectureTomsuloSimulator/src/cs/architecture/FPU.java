package cs.architecture;
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
}
