pubic class MMU{

  private PhysicalMemory mem;
  private TLBcache tlb;
  private PageTable pt;

  public MMU(PhysicalMemory mem, ){

  }

  public int[] read(String address){

    String virtualPageNumber = address.substring(0, 2);
    int index = Integer.parseInt(virtualPageNumber, 16);

    String hex = address.substring(2);
    int offset = Integer.parseInt(hex, 16);
    int data = 0;
    int type;

    //hit
    if(tlb.getEntry(virtualPageNumber)){
      type = 0;
      int pgnum = tlb.getEntry(virtualPageNumber).getPageframe();
      data = Integer.parseInt(mem.getMemory(pgFrameNum, offset));
      //UPDATE THE number of hit values by one
    }
    //soft
    else if (pt.checkValid(index)){
      type = 1;
      //UPDATE SOFT MISS counter
      data = Integer.parseInt(mem.getMemory(vpt.getEntry(index).getPageframe());
    }
    //hard
    else{
      type = 2;
    }
    int[] a = {type, data};
    return a;

  }

  public int write(String address, int value){
    String virtualPageNumber = address.substring(0, 2);
    int index = Integer.parseInt(virtualPageNumber, 16);

    String hex = address.substring(2);
    int offset = Integer.parseInt(hex, 16);
     
  }

}
