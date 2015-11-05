import java.util.LinkedList;


public class Simulator {
	public Simulator(int p,int n1,int n2,int b,int a1,int a2,int C,int d,int d1){
		this.p= p;
		this.n1 = n1;
		this.n2 = n2;
		this.b = b;
		this.a1 = a1;
		this.a2 = a2;
		this.C = C;
		this.d = d;
		this.d1 = d1;
	}
	
	int p = 0; // The number of processors
	int n1 = 0; // The size of L1
	int n2 =  0; // The size of l2
	int b = 0; // The block size
	int a1 = 0; // The associativity of l1
	int a2 = 0; // The associativity of l2
	int C = 0; // The number of cycles spent between two cores
	int d = 0; // The number of cycles spent on a l2 cache hit(The l1 hit is satisfied in the same cycle in which it is issued)
	int d1 = 0; // The number of cycles spent on a memory access
	
	LinkedList writeBuffer = new LinkedList();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int p = Integer.parseInt(args[0]); // The number of processors
		int n1 = Integer.parseInt(args[1]); // The size of L1
		int n2 =  Integer.parseInt(args[2]); // The size of l2
		int b = Integer.parseInt(args[3]); // The block size
		int a1 = Integer.parseInt(args[4]); // The associativity of l1
		int a2 = Integer.parseInt(args[5]); // The associativity of l2
		int C = Integer.parseInt(args[6]); // The number of cycles spent between two cores
		int d = Integer.parseInt(args[7]); // The number of cycles spent on a l2 cache hit(The l1 hit is satisfied in the same cycle in which it is issued)
		int d1 = Integer.parseInt(args[7]); // The number of cycles spent on a memory access
		
		
	}

}
