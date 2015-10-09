package cs.architecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Used to fetch instructions from a local file.
 */
public class InstructionFetcher {
	String[] instruction; 
	
	public static void fetchInstructions(String instructionFilePath){
		BufferedReader br = null;
		try {
			File dataFile = new File(instructionFilePath);
			if (dataFile.exists()) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "utf-8"));
				String str = null;
				while ((str = br.readLine()) != null) {
					try {
						System.err.println("Instruction->"+str);
//						String[] strs = str.split(",");
//						if(strs.length==8){
//							
//							
//						}else{
//							System.err.println("长度不对->"+str);
//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InstructionFetcher.fetchInstructions("/Users/fanlingling/Documents/workspace/SRPC_StubGenerator/src/test.xml");
	}

}
