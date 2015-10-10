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
		public static String F0,F1, F2, F3, F4, F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,F15
		,F16,F17, F18, F19, F20, F21,F22,F23,F24,F25,F26,F27,F28,F29,F30,F31,F32;
	}
	/*
	 * Integer Register Status Table
	 */
	public static HashMap<String,String> integerRegistersStatus = new HashMap<String,String>();
	static{
		integerRegistersStatus.put(Register.F0,null);
		integerRegistersStatus.put(Register.F1,null);
		integerRegistersStatus.put(Register.F2,null);
		integerRegistersStatus.put(Register.F3,null);
		integerRegistersStatus.put(Register.F4,null);
		integerRegistersStatus.put(Register.F5,null);
		integerRegistersStatus.put(Register.F6,null);
		integerRegistersStatus.put(Register.F7,null);
		integerRegistersStatus.put(Register.F8,null);
		integerRegistersStatus.put(Register.F9,null);
		integerRegistersStatus.put(Register.F10,null);
		integerRegistersStatus.put(Register.F11,null);
		integerRegistersStatus.put(Register.F12,null);
		integerRegistersStatus.put(Register.F13,null);
		integerRegistersStatus.put(Register.F14,null);
		integerRegistersStatus.put(Register.F15,null);
		
		integerRegistersStatus.put(Register.F16,null);
		integerRegistersStatus.put(Register.F17,null);
		integerRegistersStatus.put(Register.F18,null);
		integerRegistersStatus.put(Register.F19,null);
		integerRegistersStatus.put(Register.F20,null);
		integerRegistersStatus.put(Register.F21,null);
		integerRegistersStatus.put(Register.F22,null);
		integerRegistersStatus.put(Register.F23,null);
		integerRegistersStatus.put(Register.F24,null);
		integerRegistersStatus.put(Register.F25,null);
		integerRegistersStatus.put(Register.F26,null);
		integerRegistersStatus.put(Register.F27,null);
		integerRegistersStatus.put(Register.F28,null);
		integerRegistersStatus.put(Register.F29,null);
		integerRegistersStatus.put(Register.F30,null);
		integerRegistersStatus.put(Register.F31,null);
		integerRegistersStatus.put(Register.F32,null);
	}
	
	/*
	 * Float Register Status Table
	 */
	public static HashMap<String,String> floatRegistersStatus = new HashMap<String,String>();
	static{
		integerRegistersStatus.put(Register.F0,null);
		integerRegistersStatus.put(Register.F1,null);
		integerRegistersStatus.put(Register.F2,null);
		integerRegistersStatus.put(Register.F3,null);
		integerRegistersStatus.put(Register.F4,null);
		integerRegistersStatus.put(Register.F5,null);
		integerRegistersStatus.put(Register.F6,null);
		integerRegistersStatus.put(Register.F7,null);
		integerRegistersStatus.put(Register.F8,null);
		integerRegistersStatus.put(Register.F9,null);
		integerRegistersStatus.put(Register.F10,null);
		integerRegistersStatus.put(Register.F11,null);
		integerRegistersStatus.put(Register.F12,null);
		integerRegistersStatus.put(Register.F13,null);
		integerRegistersStatus.put(Register.F14,null);
		integerRegistersStatus.put(Register.F15,null);
		
		integerRegistersStatus.put(Register.F16,null);
		integerRegistersStatus.put(Register.F17,null);
		integerRegistersStatus.put(Register.F18,null);
		integerRegistersStatus.put(Register.F19,null);
		integerRegistersStatus.put(Register.F20,null);
		integerRegistersStatus.put(Register.F21,null);
		integerRegistersStatus.put(Register.F22,null);
		integerRegistersStatus.put(Register.F23,null);
		integerRegistersStatus.put(Register.F24,null);
		integerRegistersStatus.put(Register.F25,null);
		integerRegistersStatus.put(Register.F26,null);
		integerRegistersStatus.put(Register.F27,null);
		integerRegistersStatus.put(Register.F28,null);
		integerRegistersStatus.put(Register.F29,null);
		integerRegistersStatus.put(Register.F30,null);
		integerRegistersStatus.put(Register.F31,null);
		integerRegistersStatus.put(Register.F32,null);
	}
	public static java.util.ArrayList<ROBItem> ROB = new java.util.ArrayList<ROBItem>();
	public class ROBItem{
		boolean ready = false;
	}
}
