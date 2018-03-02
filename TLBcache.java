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
	public static TlbEntry[] TLB;
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
	public void writeTLBEntry(int EntryNumber,String HexFrameNumber,String HexVirtualPageNo){
		TLB[EntryNumber].writeEntry(HexFrameNumber);
		TLB[EntryNumber].setVirtualPage(HexVirtualPageNo);
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
	public TlbEntry getEntry(int index){
		return TLB[index];
	}
}