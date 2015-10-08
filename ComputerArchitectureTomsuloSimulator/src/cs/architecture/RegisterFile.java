package cs.architecture;

/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Register File container, used to hold all the registers
 */
public class RegisterFile{
	private RegisterFile instance;
	private RegisterFile(){}
	public RegisterFile getInstance(){
		return instance;
	}
}
