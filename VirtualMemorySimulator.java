
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class VirtualMemorySimulator {
	
	public static void main(String[] args) throws FileNotFoundException{
		VirtualMemorySimulator vm = new VirtualMemorySimulator();
		vm.simulate(args[0], "test.csv");
	}
	
	public void simulate(String inputFile, String outputFile) throws FileNotFoundException{
		List<String[]> memoryAccessList = createMemoryAccessList(inputFile);
		
		PageTable pt = new PageTable();
		PhysicalMemory pm = new PhysicalMemory();
		
		OS os = new OS(pt,pm);
		CPU cpu = new CPU(pm,pt,os);
		
		List<String[]> results = cpu.simulate(memoryAccessList);
		
		outputResults(results, outputFile);
	}
	
	private List<String[]> createMemoryAccessList(String fileName) throws FileNotFoundException{
		List<String[]> accessList = new ArrayList<String[]>();
	
		Scanner sc = new Scanner(new File(fileName));
		while (sc.hasNext()){
			String writeBit = sc.next();
			String[] memoryAccess;
			System.out.println(writeBit);
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
			
			accessList.add(memoryAccess);
		}
		
		return accessList;
	}
	
	private void outputResults(List<String[]> results, String fileName) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.write("Address,r/w,value,soft,hard,hit,evicted_pg#,dirty_evicted_page\n");
		
		for (int i = 0; i < results.size();i++){
			pw.write(String.join(",",results.get(i)));
		}
	}
}