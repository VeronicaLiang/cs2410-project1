package cs.architecture;

public class FPU {
	private FPU instance;
	private FPU (){}
	public FPU getInstance(){
		return instance;
	}
}
