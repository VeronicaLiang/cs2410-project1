package cs.architecture;

public class PCInstance {
	PCInstance instance;
	private PCInstance(){
		
	}
	public PCInstance getInstance(){
		return instance;
	}
}
