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
	public void writeMemory(int[] data){
		writeMemoryFULL(data,counter);
		counter++;
		if(counter == pageamount)
			full = true;
	}
	public void writeMemoryFULL(int[] data,int index){
		for(int i= 0;i < 256;i++){
			RAM[index][i]=data[i];
		}
	}
	public void writeMemory(int PageNo, int line, int data) {
		RAM[PageNo][line] = data;
	}

	public int getMemory(int PageNo, int line) {
		return RAM[PageNo][line];
	}
}