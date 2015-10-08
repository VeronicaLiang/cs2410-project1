package cs.architecture;

/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Register File container, used to hold all the registers
 */
public class RegisterFile{
	private static RegisterFile instance;
	private RegisterFile(){}
	public static RegisterFile getInstance(){
		if(instance==null)
			instance = new RegisterFile();
		return instance;
	}
}
