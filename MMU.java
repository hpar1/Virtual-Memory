public class MMU{

  private PhysicalMemory mem;
  public TLBcache tlb;
  private PageTable pt;

  public MMU(PhysicalMemory mem, PageTable pt){
    this.mem = mem;
    tlb = new TLBcache();
    this.pt = pt;
  }


  public int[] read(String address){

    String virtualPageNumber = address.substring(0, 2);
    int index = Integer.parseInt(virtualPageNumber, 16);

    String hex = address.substring(2);
    int offset = Integer.parseInt(hex, 16);
    int data = 0;
    int type;

    //hit
    if(tlb.getEntry(virtualPageNumber) != null){
      type = 0;
      int pgFrameNum = tlb.getEntry(virtualPageNumber).getPageframe();
      data = mem.getMemory(pgFrameNum, offset);
    }
    //soft
    else if (pt.checkValid(index)){
      type = 1;
      data = mem.getMemory(pt.getEntry(index).getPageframe(), offset);
      //update tlb
    }
    //hard
    else{
      type = 2;
    }
    int[] a = {type, data};
    return a;

  }

  public int[] write(String address, int value){
    String virtualPageNumber = address.substring(0, 2);
    int index = Integer.parseInt(virtualPageNumber, 16);
	int type;

    String hex = address.substring(2);
    int offset = Integer.parseInt(hex, 16);
    //hit
    if(tlb.getEntry(virtualPageNumber) != null){
      type = 0;
      int pgFrameNum = tlb.getEntry(virtualPageNumber).getPageframe();
      mem.writeMemory(pgFrameNum, offset, value);

    }
    //soft
    else if (pt.checkValid(index)){
      type = 1;
      mem.writeMemory(pt.getEntry(index).getPageframe(), offset, value);
      //update tlb
    }
    //hard
    else{
      type = 2;
    }
    int[] a = {type};
    return a;

  }

}
