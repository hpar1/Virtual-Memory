
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/

public class CPU {
	
	PhysicalMemory pm;
	PageTable pt;
	OS os;
	CPU(PhysicalMemory pm, PageTable pt, OS os){
		this.pm = pm;
		this.pt = pt;
		this.os = os;
	}
	
	public void simulate(int[][] memoryAccessList){
		MMU mmu = new MMU();
		// memoryAccessList: [access type, address, data]
		
		for (int i = 0; i < memoryAccessList.length; i++) {
			
			// 0 write, 1 read
			int readBit = memoryAccessList[i][0];
			int address = memoryAccessList[i][1];
			int data = memoryAccessList[i][2];
			
			// [miss_status, response]
			int[] response = (readBit)? 
					mmu.read(address) : mmu.write(address,data);
			
			// 0:hit, 1:soft, 2:hard
			int miss_status = response[0];
			if (miss_status == 2) {
				// [FF]FF 
				String frameNo = f.substring(0, 2);
				int decFrameNo = Integer.parseInt(frameNo, 16);
				os.pageReplace(mmu.tlb, decFrameNo);
			}
			
			// reset page table ref bits every 20 accesses
			if (i % 20) {
				os.resetRef();
			}
		}
	}
}