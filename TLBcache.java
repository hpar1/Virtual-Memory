class TLBcache{
	class TlbEntry extends PageTableEntry{
		private int virtualpage;
		public void setVirtualPage(String HexVirtualPageNo){
			virtualpage = Integer.parseInt(HexVirtualPageNo,16);
		}
		public int getVirtualPage(){
			return virtualpage;
		}
	}
	public TlbEntry[] TLB;
	public TLBcache(int size){
		TLB = new TlbEntry[size];
	}
	public TLBcache(){
		TLB = new TlbEntry[8];
	}
	public void writeTLBEntry(int EntryNumber,String HexFrameNumber,String HexVirtualPageNo){
		TLB[EntryNumber].writeEntry(HexFrameNumber);
		TLB[EntryNumber].setVirtualPage(HexVirtualPageNo);
	}
	public TlbEntry getEntry(String HexVirtualPageNo){
		int vmPageNo;
		for(TlbEntry x:TLB){
			vmPageNo = Integer.parseInt(HexVirtualPageNo,16);
			if(vmPageNo == x.getVirtualPage()){
				return x;
			}
		}
		return null;
	}
}