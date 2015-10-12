

public class BranchTargetBuffer {
	// a two dimensional array
	// the row number is the pc index (pc mod 32)
	// the first column is the predicted pc
	// the second column is the 1 bit prediction 
	private static int [] [] buffer = Initialize();
	
	public static int [] [] Initialize(){
		int [] [] b = new int [32][2];
		for(int row = 0; row < 32; row++){
			b[row][0] = -1;
			b[row][1] = 0;
		}
		return b;
	}
	
	public int[] [] Getbuffer(){
		return buffer;
	}
	
}
