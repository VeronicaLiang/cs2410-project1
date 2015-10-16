
public class ROBItem {
	int entry;
	boolean busy;
//	String instruction;
	Instruction instruction; // change the type of instruction, make the comparing of instruction type easier. --xl
	String state;
	String destination;
	int address;
	float value;
	boolean ready = false;
}
