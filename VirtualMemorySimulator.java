
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
	
	public static void main(String[] args) throws IOException{
		VirtualMemorySimulator vm = new VirtualMemorySimulator();
		String inputFile = args[0];
		String outputFile = inputFile.substring(0,inputFile.indexOf(".")) + ".csv";
		vm.simulate(inputFile, outputFile);
	}
	
	public void simulate(String inputFile, String outputFile) throws IOException{
		createPgFileCopies();
		
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
				memoryAccess = new String[3];
				memoryAccess[0] = writeBit;
				memoryAccess[1] = sc.next();
			}
			
			accessList[i] = memoryAccess;
			i++;
			
			if (i >= accessList.length){
				accessList = resize2DArray(accessList, accessList.length*2); 
			}
		}
		
		sc.close();
		
		return resize2DArray(accessList, i);
	}
	
	private String[][] resize2DArray(String[][] arr, int newSize){
		String[][] newArr = new String[newSize][];
		
		for (int i = 0; i < newSize; i++){
			newArr[i] = arr[i];
		}
		
		return newArr;
	}
	
	private void outputResults(String[][] results, String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		StringBuilder sb = new StringBuilder();
		sb.append("Address,r/w,value,soft,hard,hit,evicted_pg#,dirty_evicted_page\n");
		
		for (int i = 0; i < results.length;i++){
			sb.append(String.join(",",results[i]));
			sb.append("\n");
		}
		pw.write(sb.toString());
		pw.close();
	}
	
	private void createPgFileCopies() throws IOException{
        String fileSource = "OriginalPageFiles/";
        String destination = "EditedPageFiles/";
        File srcDir = new File(fileSource);
        File destDir = new File(destination);
        copyAll(srcDir, destDir);
	}
	
    // Function to copy original files
    private void copyAll(File sourceDir, File destDir) throws IOException {
        if (sourceDir.isDirectory()) {
            copyDir(sourceDir, destDir);
        } else {
            copyFile(sourceDir, destDir);
        }
    }
    // copy directories
    private void copyDir(File source, File target) throws IOException {
        if (target.exists() != true) {
            target.mkdir();
        }
        for (String s : source.list()) { // .list returns all of the file names in the directory
            copyAll(new File(source, s), new File(target, s));
        }
    }
    // copy files
    private void copyFile(File source, File target) throws IOException {
        // try catch finally so even if there is an exception buffer still closes
        try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target);) {
            byte[] b = new byte[1024]; // buffer to hold file contents for copying
            int length;
            while ((length = in.read(b)) > 0) { // keeps copying until end of file
                out.write(b, 0, length);
            }
        }
    }
}