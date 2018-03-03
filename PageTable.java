class PageTable {
	public PageTableEntry[] PT;
	public int length = 256;
	public PageTable(int size) {
		PT = new PageTableEntry[size];
		length = size;
		create();
	}

	public PageTable() {
		PT = new PageTableEntry[256];
		length = 256;
		create();
	}
	private void create(){
		for(int i = 0;i < length;i++){
			PT[i] = new PageTableEntry();
		}
	}
	public PageTableEntry getEntry(int pagenumber) {
		return PT[pagenumber];
	}

	public boolean checkEntry(int pagenumber) {
		boolean check = PT[pagenumber].checkValid();
		return check;
	}

	public boolean checkValid(int pagenumber){
		return PT[pagenumber].checkValid() && PT[pagenumber].getDirty() == 0;
	}

	public void writeEntry(int pagenumber, String framenumber) {
		PT[pagenumber].writeEntry(framenumber);
	}
}
