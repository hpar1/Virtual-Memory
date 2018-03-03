pubic class MMU{

  private PhysicalMemory mem;
  private TLBcache tlb;
  private PageTable pt;

  public int fetch(String address){

    String virtualPageNumber = address.substring(0, 2);
    int index = Integer.parseInt(virtualPageNumber, 16);

    String hex = address.substring(2);
    int offset = Integer.parseInt(hex, 16);
    int data = 0;

    if(tlb.getEntry(virtualPageNumber)){
      int pgnum = tlb.getEntry(virtualPageNumber).getPageframe();
      data = Integer.parseInt(mem.getMemory(pgFrameNum, offset));
      //UPDATE THE number of hit values by one
    }
    else if (pt.checkValid(index)){
      //UPDATE SOFT MISS counter
      data = Integer.parseInt(mem.getMemory(vpt.getEntry(index).getPageframe());
    }



  }


}
