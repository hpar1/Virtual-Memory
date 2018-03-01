class PhysicalMemory {
	int[][] RAM;

	public PhysicalMemory(int NoofPages, int PageSize) {
		RAM = new int[NoofPages][PageSize];
	}

	public PhysicalMemory() {
		RAM = new int[16][256];
	}

	public void writeMemory(int PageNo, int line, int data) {
		RAM[PageNo][line] = data;
	}

	public int getMemory(int PageNo, int line) {
		return RAM[PageNo][line];
	}
}