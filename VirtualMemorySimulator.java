
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

import java.util.Scanner;
import java.io.*;

public class VirtualMemorySimulator {
	
	public static void main(String[] args) throws FileNotFoundException{
		VirtualMemorySimulator vm = new VirtualMemorySimulator();
		vm.simulate(args[0], "test.csv");
	}
	
	public void simulate(String inputFile, String outputFile) throws FileNotFoundException{
		String[][] memoryAccessList = createMemoryAccessList(inputFile);
		
		PageTable pt = new PageTable();
		PhysicalMemory pm = new PhysicalMemory();
		
		OS os = new OS(pt,pm);
		CPU cpu = new CPU(pm,pt,os);
		
		String[][] results = cpu.simulate(memoryAccessList);
		
		outputResults(results, outputFile);
	}
	
	private String[][] createMemoryAccessList(String fileName) throws FileNotFoundException{
		final int DEFAULT_MAX = 1000;
		String[][] accessList = new String[DEFAULT_MAX][];
		Scanner sc = new Scanner(new File(fileName));
		
		int i = 0;
		while (sc.hasNext()){
			String writeBit = sc.next();
			String[] memoryAccess;
			if (writeBit.equals("1")){
				memoryAccess = new String[3];
				memoryAccess[0] = writeBit;
				memoryAccess[1] = sc.next();
				memoryAccess[2] = sc.next();
			}
			else {
				memoryAccess = new String[2];
				memoryAccess[0] = writeBit;
				memoryAccess[1] = sc.next();
			}
			
			accessList[i] = memoryAccess;
			i++;
			
			if (i >= accessList.length){
				accessList = resize2DArray(accessList, accessList.length*2); 
			}
		}
		
		return resize2DArray(accessList, i);
	}
	
	private String[][] resize2DArray(String[][] arr, int newSize){
		String[][] newArr = new String[newSize][];
		
		for (int i = 0; i < newSize; i++){
			newArr[i] = arr[i];
		}
		
		return newArr;
	}
	
	private void outputResults(String[][] results, String fileName) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.write("Address,r/w,value,soft,hard,hit,evicted_pg#,dirty_evicted_page\n");
		
		for (int i = 0; i < results.length;i++){
			pw.write(String.join(",",results[i]));
		}
	}
}