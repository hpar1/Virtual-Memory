class PageTableEntry {
	private int valid = 0;
	private int reference = 0;
	private int dirty = 0;
	private int pageframe;
	public PageTableEntry(){
		valid = 0;
		reference = 0;
		dirty = 0;
		pageframe = -1;
	}
	public void writeEntry(String Hexframenumber) {
		Hexframenumber = Hexframenumber.substring(0, 2); // to ignore offset
		pageframe = Integer.parseInt(Hexframenumber, 16);
		valid = 1;
		reference = 1;
	}
	public void writeEntry(int Intframenumber){
		pageframe= Intframenumber;
		valid = 1;
		reference = 1;
	}

	public int getReference() {
		return reference;
	}

	public void setReference() {
		reference = 1;
	}

	public void resetReference() {
		reference = 0;
	}

	public void setDirty() {
		dirty = 1;
	}

	public int getDirty() {
		return dirty;
	}

	public void resetDirty() {
		dirty = 0;
	}
	public void resetpageframe(){
		pageframe = -1;
	}
	public void resetvalid(){
		valid = 0;
	}
	public int getPageframe() {
		if (valid == 0) {
			return -1;
		}
		return pageframe;
	}

	public boolean checkValid() {
		boolean check = true;
		if (valid == 0) {
			check = false;
		}
		return check;
	}
}