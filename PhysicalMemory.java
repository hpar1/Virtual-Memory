class PhysicalMemory {
	public int[][] RAM;
	private boolean full = false;
	private int counter = 0;
	private int pageamount = 16;
	private int PgSize = 256;
	public PhysicalMemory(int NoofPages, int PageSize) {
		RAM = new int[NoofPages][PageSize];
		pageamount = NoofPages;
		PgSize = PageSize;
	}

	public PhysicalMemory() {
		RAM = new int[16][256];
	}
	public boolean checkfull(){
		return full;
	}
	public int writeMemory(int[] data){	//used by OS
		writeMemoryFULL(data,counter);
		counter++;
		if(counter == pageamount){
			full = true;
		}
		return counter-1;
	}
	public void writeMemoryFULL(int[] data,int index){	//used by OS
		for(int i= 0;i < 256;i++){
			RAM[index][i]=data[i];
		}
	}
	public void writeMemory(int PageNo, int line, int data) {	//used by MMU
		RAM[PageNo][line] = data;
	}

	public int getMemory(int PageNo, int line) {	//used by MMU
		return RAM[PageNo][line];
	}
}