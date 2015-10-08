package cs.architecture;

public class Bus {
	private Bus instance;
	private Bus(){}
	public Bus getInstance(){
		return instance;
	}
}
