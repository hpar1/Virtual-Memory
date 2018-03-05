
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

import java.io.IOException;

public class CPU {
	
	private PhysicalMemory pm;
	private PageTable pt;
	private OS os;
	private MMU mmu;
	private final int R_BIT_RESET_CYCLE = 5;
	
	public CPU(PhysicalMemory pm, PageTable pt, OS os){
		this.pm = pm;
		this.pt = pt;
		this.os = os;
		this.mmu = new MMU(pm, pt);
	}
	
	public String[][] simulate(String[][] memoryAccessList) throws IOException {
		
		String[][] results = new String[memoryAccessList.length][];
		// memoryAccessList: [access type, address, value]
		
		for (int i = 0; i < memoryAccessList.length; i++) {
			// reset page table ref bits every 5 accesses
			if (i % R_BIT_RESET_CYCLE == 0) {
				os.resetRef(mmu.tlb);
			}
			// 0 write, 1 read
			String writeBit = memoryAccessList[i][0];
			String address = memoryAccessList[i][1];
			String value = memoryAccessList[i][2];
			int intValue = Integer.parseInt(value);
			
			// [missStatus, response]
			int[] response = (writeBit.equals("1"))? 
					mmu.write(address, intValue) : mmu.read(address);
			
			// 0:hit, 1:soft, 2:hard
			int missStatus = response[0];
			int[] osResponse = {-1, 0};
			if (missStatus == 2) {
				osResponse = os.addPage(mmu.tlb, address);
			}
			
			String soft = (missStatus == 0)? "1":"0";
			String hard = (missStatus == 1)? "1":"0";
			String hit = (missStatus == 2)? "1":"0";
			String evicted_page = Integer.toString(osResponse[0]);
			String dirty = Integer.toString(osResponse[1]);
			
			results[i] = new String[] 
					{address, writeBit, value, soft, hard, hit, evicted_page, dirty};
		}
		
		return results;
	}
}