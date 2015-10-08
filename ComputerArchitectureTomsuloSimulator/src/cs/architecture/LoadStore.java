package cs.architecture;

public class LoadStore {
	private LoadStore instance;
	private LoadStore (){}
	public LoadStore getInstance(){
		return instance;
	}
}
