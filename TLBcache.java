class TLBcache{
	class TlbEntry extends PageTableEntry{
		private int virtualpage;
		
		public TlbEntry(){
			virtualpage = 0;
		}
		public void setVirtualPage(String HexVirtualPageNo){
			virtualpage = Integer.parseInt(HexVirtualPageNo,16);
		}
		public int getVirtualPage(){
			return virtualpage;
		}
	}
	private boolean full = false;
	private int EntryNumber = 0;
	public TlbEntry[] TLB;
	public int length = 8;
	public TLBcache(int size){
		TLB = new TlbEntry[size];
		length = size;
		create();
	}
	public TLBcache(){
		TLB = new TlbEntry[8];
		length = 8;
		create();
	}
	private void create(){
		for(int i = 0;i < length;i++){
			TLB[i] = new TlbEntry();
		}
	}
	public boolean checkfull(){
		return full;
	}
	private void makefull(){
		if(EntryNumber == length)
			full = true;
	}
	public void writeTLBEntry(String HexFrameNumber,String HexVirtualPageNo){
		TLB[EntryNumber].writeEntry(HexFrameNumber);
		TLB[EntryNumber].setVirtualPage(HexVirtualPageNo);
		EntryNumber++;
		makefull();
	}
	public void writeTLBENtry(PageTableEntry pagetableentry,String HexVirtualPageNo){
		TLB[EntryNumber] = (TlbEntry)pagetableentry;
		TLB[EntryNumber].setVirtualPage(HexVirtualPageNo);
		EntryNumber++;
		makefull();
	}
	public TlbEntry getEntry(String HexVirtualPageNo){
		int vmPageNo;
		for(TlbEntry x:TLB){
			HexVirtualPageNo = HexVirtualPageNo.substring(0, 2);
			vmPageNo = Integer.parseInt(HexVirtualPageNo,16);
			if(vmPageNo == x.getVirtualPage()){
				return x;
			}
		}
		return null;
	}
	public TlbEntry getEntryPageIndex(int IntVirtualPageNo){
		for(TlbEntry x:TLB){
			if(IntVirtualPageNo == x.getVirtualPage()){
				return x;
			}
		}
		return null;
	}
	public TlbEntry getEntry(int index){
		return TLB[index];
	}
	public void fifo(){
		TlbEntry[] temp = new TlbEntry[length];
		for(int i = 0;i < (length-1) ;i++){
			temp[i] = TLB[i+1];
		}
		TLB = temp;
	}
}