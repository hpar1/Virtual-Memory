
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

import java.util.Scanner;
import java.io.PrintWriter;

public class VirtualMemorySimulator(){
	
	public static void main(String[] args) {
		VirtualMemorySimulator vm = new VirtualMemorySimulator();
		vm.simulate(args[0]);
	}
	
	public void simulate(string inputFile, string outputFile){
		memoryAccessList = createMemoryAccessList(inputFile);
		
		PageTable pt = new PageTable()
		PhysicalMemory pm = new PhysicalMemory();
		
		OS os = new OS(pt);
		CPU cpu = new CPU(pm,pt,os);
		
		int[][] result = cpu.simulate(memoryAccessList);
		
		outputResults(stringfileName);
	}
	
	private int[][] createMemoryAccessList(string fileName){
		List<int[]> memoryAccessList = new ArrayList
	
		Scanner sc = new Scanner(new File(fileName));
		while (sc.hasNext){
			sc.nextInt();
		}
	}
	
	private void outputResults(string fileName){
		PrintWriter pw = new PrintWriter(new File(fileName));
	}
}