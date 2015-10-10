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
	public static enum Unit{
		INT0, INT1, MULT, LoadStore, FPU, BU;
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
	public static HashMap<String,Const.Unit> unitsForInstruction = new HashMap<String,Const.Unit>();
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
	 */
	public static HashMap<Const.Unit,Station> reservationStations = new HashMap<Const.Unit,Station>();
	static {
		reservationStations.put(Const.Unit.INT0,new Station());
		reservationStations.put(Const.Unit.INT1,new Station());
		reservationStations.put(Const.Unit.MULT,new Station());
		reservationStations.put(Const.Unit.LoadStore,new Station());
		reservationStations.put(Const.Unit.FPU,new Station());
		reservationStations.put(Const.Unit.BU,new Station());
	}
	
	public static enum Register{
		F0,F1, F2, F3, F4, F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,F15
		,F16,F17, F18, F19, F20, F21,F22,F23,F24,F25,F26,F27,F28,F29,F30,F31,F32;
	}
	/*
	 * Integer Register Status Table
	 */
	public static HashMap<Const.Register,Integer> integerRegistersStatus = new HashMap<Const.Register,Integer>();
	static{
		integerRegistersStatus.put(Register.F0,0);
		integerRegistersStatus.put(Register.F1,0);
		integerRegistersStatus.put(Register.F2,0);
		integerRegistersStatus.put(Register.F3,0);
		integerRegistersStatus.put(Register.F4,0);
		integerRegistersStatus.put(Register.F5,0);
		integerRegistersStatus.put(Register.F6,0);
		integerRegistersStatus.put(Register.F7,0);
		integerRegistersStatus.put(Register.F8,0);
		integerRegistersStatus.put(Register.F9,0);
		integerRegistersStatus.put(Register.F10,0);
		integerRegistersStatus.put(Register.F11,0);
		integerRegistersStatus.put(Register.F12,0);
		integerRegistersStatus.put(Register.F13,0);
		integerRegistersStatus.put(Register.F14,0);
		integerRegistersStatus.put(Register.F15,0);
		
		integerRegistersStatus.put(Register.F16,0);
		integerRegistersStatus.put(Register.F17,0);
		integerRegistersStatus.put(Register.F18,0);
		integerRegistersStatus.put(Register.F19,0);
		integerRegistersStatus.put(Register.F20,0);
		integerRegistersStatus.put(Register.F21,0);
		integerRegistersStatus.put(Register.F22,0);
		integerRegistersStatus.put(Register.F23,0);
		integerRegistersStatus.put(Register.F24,0);
		integerRegistersStatus.put(Register.F25,0);
		integerRegistersStatus.put(Register.F26,0);
		integerRegistersStatus.put(Register.F27,0);
		integerRegistersStatus.put(Register.F28,0);
		integerRegistersStatus.put(Register.F29,0);
		integerRegistersStatus.put(Register.F30,0);
		integerRegistersStatus.put(Register.F31,0);
		integerRegistersStatus.put(Register.F32,0);
	}
	
	/*
	 * Float Register Status Table
	 */
	public static HashMap<Const.Register,Float> floatRegistersStatus = new HashMap<Const.Register,Float>();
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
}
