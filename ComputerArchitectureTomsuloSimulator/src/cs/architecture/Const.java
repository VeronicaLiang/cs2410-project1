package cs.architecture;

import java.util.HashMap;

/**
 * @author Computer Architecture Simulator Project Group
 *
 */

/*
 * Const, stores constant variables
 */
public class Const {
	public class Unit{
		public static String INT0 = "INT0", INT1 = "INT1", MULT = "MULT", LoadStore = "LoadStore", FPU = "FPU", BU = "BU";
	}
//	public static enum OPCODE{
//		AND, ANDI, OR, ORI, SLT, SLTI,DADD,DADDI,DSUB//INT0,INT1
//		,DMUL//MULT
//		,LD, L.D,SD, S.D//LoadStore
//		,ADD.D, SUB.D, MUL.D,DIV.D//FPU
//		,BEQZ, BNEZ, BEQ, BNE//BU;
//	}
	/*
	 * Table of instructions executing on what units.
	 */
	public static HashMap<String,String> unitsForInstruction = new HashMap<String,String>();
	static{
		unitsForInstruction.put("AND",Unit.INT0);
		unitsForInstruction.put("ANDI",Unit.INT0);
		unitsForInstruction.put("OR",Unit.INT0);
		unitsForInstruction.put("SLT",Unit.INT0);
		unitsForInstruction.put("DADD",Unit.INT0);
		unitsForInstruction.put("DADDI",Unit.INT0);
		unitsForInstruction.put("DSUB",Unit.INT0);
		
		unitsForInstruction.put("DMUL",Unit.MULT);
		
		unitsForInstruction.put("LD",Unit.LoadStore);
		unitsForInstruction.put("L.D",Unit.LoadStore);
		unitsForInstruction.put("SD",Unit.LoadStore);
		unitsForInstruction.put("S.D",Unit.LoadStore);
		
		unitsForInstruction.put("ADD.D",Unit.FPU);
		unitsForInstruction.put("SUB.D",Unit.FPU);
		unitsForInstruction.put("ADD.D",Unit.FPU);
		unitsForInstruction.put("DIV.D",Unit.FPU);
		
		unitsForInstruction.put("BEQZ",Unit.BU);
		unitsForInstruction.put("BNEZ",Unit.BU);
		unitsForInstruction.put("BEQ",Unit.BU);
		unitsForInstruction.put("BNE",Unit.BU);
	}
	/*
	 * Reservation Stations Table.
	 * Station 1 to 4 are INT0&INT1 stations.
	   Station 5 and 6 are INT0&INT1 stations.
	   Station 7 to 12 are Load/Store  stations.
	   Station 13 to 17 are FPU  stations.
	   Station 18 and 19 are FPU  stations.
	 */
	public static HashMap<String,Station> reservationStations = new HashMap<String,Station>();
	static {
		Station station;
		for(int i = 1;i<=19;i++){
			station = new Station();
			station.name = i+"";
			reservationStations.put(station.name ,station);
		}
	}
	
	public class Register{
		String name;
		float floatValue;
		int intValue;
		boolean busy;
		int Reorder;
	}
	public class IntegerRegister{
		public static String R0 = "R0",R1 = "R1", R2 = "R2", R3 = "R3", R4 = "R4", R5 = "R5",R6 = "R6",R7 = "R7",R8 = "R8"
				,R9 = "R9",R10 = "R10",R11 = "R11",R12 = "R12",R13 = "R13",R14 = "R14",R15 = "R15",R16 = "R16",R17 = "R17"
				, R18 = "R18", R19 = "R19", R20 = "R20", R21 = "R21",R22 = "R22",R23 = "R23",R24 = "R24",R25 = "R25"
				,R26 = "R26",R27 = "R27",R28 = "R28",R29 = "R29",R30 = "R30",R31 = "R31";
	}
	
	/*
	 * Integer Register Status Table
	 */
	public static HashMap<String,Register> integerRegistersStatus = new HashMap<String,Register>();
	static{
		Register register = new Register();
		integerRegistersStatus.put(IntegerRegister.R0,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R1,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R2,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R3,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R4,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R5,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R6,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R7,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R8,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R9,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R10,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R11,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R12,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R13,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R14,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R15,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R16,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R17,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R18,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R19,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R20,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R21,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R22,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R23,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R24,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R25,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R26,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R27,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R28,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R29,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R30,register);
		register = new Register();
		integerRegistersStatus.put(IntegerRegister.R31,register);
		
	}
	
	public class FloatRegister{
		public static String F0 = "F0",F1 = "F1", F2 = "F2", F3 = "F3", F4 = "F4", F5 = "F5",F6 = "F6",F7 = "F7",F8 = "F8"
				,F9 = "F9",F10 = "F10",F11 = "F11",F12 = "F12",F13 = "F13",F14 = "F14",F15 = "F15",F16 = "F16",F17 = "F17"
				, F18 = "F18", F19 = "F19", F20 = "F20", F21 = "F21",F22 = "F22",F23 = "F23",F24 = "F24",F25 = "F25"
				,F26 = "F26",F27 = "F27",F28 = "F28",F29 = "F29",F30 = "F30",F31 = "F31";
	}
	/*
	 * Float Register Status Table
	 */
	public static HashMap<String,Register> floatRegistersStatus = new HashMap<String,Register>();
	static{
		Register register = new Register();
		floatRegistersStatus.put(FloatRegister.F0,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F1,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F2,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F3,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F4,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F5,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F6,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F7,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F8,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F9,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F10,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F11,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F12,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F13,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F14,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F15,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F16,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F17,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F18,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F19,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F20,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F21,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F22,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F23,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F24,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F25,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F26,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F27,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F28,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F29,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F30,register);
		register = new Register();
		floatRegistersStatus.put(FloatRegister.F31,register);
		
	}
	static int NR = 16;
	public static java.util.ArrayList<ROBItem> ROB = new java.util.ArrayList<ROBItem>();
	public class ROBItem{
		boolean ready = false;
		float floatValue;
		int intValue;
	}
}
