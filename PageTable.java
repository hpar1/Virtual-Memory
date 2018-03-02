class PageTable {
	public PageTableEntry[] PT;

	public PageTable(int size) {
		PT = new PageTableEntry[size];
	}

	public PageTable() {
		PT = new PageTableEntry[256];
	}

	public PageTableEntry getEntry(int pagenumber) {
		return PT[pagenumber];
	}

	public boolean checkEntry(int pagenumber) {
		boolean check = PT[pagenumber].checkValid();
		return check;
	}

	public void writeEntry(int pagenumber, String framenumber) {
		PT[pagenumber].writeEntry(framenumber);
	}

}