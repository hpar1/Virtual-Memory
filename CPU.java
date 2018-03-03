
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

import java.util.List;
import java.util.ArrayList;

public class CPU {
	
	PhysicalMemory pm;
	PageTable pt;
	OS os;
	CPU(PhysicalMemory pm, PageTable pt, OS os){
		this.pm = pm;
		this.pt = pt;
		this.os = os;
	}
	
	public void simulate(List<String[]> memoryAccessList){
		
		MMU mmu = new MMU(pm, pt);
		List<String[]> results = new ArrayList();
		// memoryAccessList: [access type, address, value]
		
		for (int i = 0; i < memoryAccessList.size(); i++) {
			// reset page table ref bits every 20 accesses
			if (i % 20 == 0) {
				os.resetRef();
			}
			// 0 write, 1 read
			String writeBit = memoryAccessList.get(i)[0];
			String address = memoryAccessList.get(i)[1];
			String value = memoryAccessList.get(i)[2];
			int intValue = Integer.parseInt(value);
			
			// [miss_status, response]
			int[] response = (writeBit.equals("1"))? 
					mmu.write(address, intValue) : mmu.read(address);
			
			// 0:hit, 1:soft, 2:hard
			int miss_status = response[0];
			if (miss_status == 2) {
				// [FF]FF 
				String frameNo = address.substring(0, 2);
				int decFrameNo = Integer.parseInt(frameNo, 16);
				os.pageReplace(mmu.tlb, decFrameNo);
			}
			
			String soft = (miss_status == 0)? "1":"0";
			String hard = (miss_status == 1)? "1":"0";
			String hit = (miss_status == 2)? "1":"0";
			String evicted_page = "?";
			String dirty = "?";
			
			results.add(new String[] 
					{address, writeBit, value, soft, hard, hit, evicted_page, dirty});
		}
	}
}