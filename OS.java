
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/
import java.io.*;
import java.util.*;

public class OS {
    public int pointer = 0; // ptr for page replacement
    private PageTable p;
    private PhysicalMemory mem;
    private PrintWriter pw; // to write back to page file if there is a change

    // constructor
    public OS(PageTable pt, PhysicalMemory m) throws IOException {
        // PageTable and memory are passed in
        p = pt;
        mem = m;
    }

    // reset reference bits must pass in TLB
    public void resetRef(TLBcache t) {
        // reset PageTable Reference bits
        for (int j = 0; j < p.length; j++) {
            p.getEntry(j).resetReference();
        }
        // reset TLB R bits
        for (int i = 0; i < t.length; i++) {
            t.getTlbEntry(i).resetReference();
        }
    }

    // Reads page file and puts it in Physical Memory
    public int[] addPage(TLBcache t, String pageNum) throws IOException{
        int[] pFile = new int[256];
        String vmPageNum = pageNum.substring(0, 2);
        String pageFile = "EditedPageFiles/" + vmPageNum + ".pg"; // to write back
        Scanner sc = new Scanner(new File(pageFile));
        int evictedPage = -1;
        int dirtySet = -1;
        int[] replace;

        // read from .pg file and store in array
        for(int i=0; i<pFile.length; i++){
            if(sc.hasNextInt()){
                pFile[i] = sc.nextInt();
               // System.out.println(i + " "+ pFile[i]); // FOR TESTING
            }
        }
        sc.close();

        if(mem.checkfull() == true){
            replace = pageReplace(t, vmPageNum);
            mem.writeMemoryFULL(pFile, replace[0]);
            p.getEntry(vmPageNum).writeEntry(replace[0]); // index written
            evictedPage = replace[1];
            dirtySet = replace[2];
            if (t.getEntry(vmPageNum) != null) {
                t.getEntry(vmPageNum).writeEntry(replace[0]); // update TLB entry
            }
        }
        else{
            int temp = mem.writeMemory(pFile);
            p.getEntry(vmPageNum).writeEntry(temp); // empty index written
            if (t.getEntry(vmPageNum) != null) {
                t.getEntry(vmPageNum).writeEntry(temp); // update TLB entry
            }
        }
        
        int[] ret = {evictedPage, dirtySet};
        return ret;
    }

    // clock replacement for page table
    private int[] pageReplace(TLBcache t, String vmPageNum) throws IOException{
        int writeIndex = -1;
        int evictedPage = -1;
        int dirtySet = -1;
        StringBuilder sb = new StringBuilder(); // new stringbuilder to store output
        
        boolean cycle = true;
        while(cycle == true){
            // reset R bit until an entry is found with R bit already == 0
            if(p.getEntry(pointer).getReference() == 1){
                p.getEntry(pointer).resetReference();
                // If page table entry is also in TLB reset its R bit
                if (t.getEntry(pointer) != null) {
                    t.getEntry(pointer).resetReference();
                }
            }
            if((p.getEntry(pointer).getReference() == 0)&&(p.getEntry(pointer).checkValid() == true)){
                cycle = false;
                break;
            }
            // go to next page table entry
            if (pointer == p.getLength() - 1) {
                pointer = 0;
            } else {
                pointer++;
            }
        }

        // if the page table at pointer already has (R bit == 0) and (V bit == 1)
        if(p.getEntry(pointer).getDirty() == 1){
            String tmp = Integer.toHexString(pointer);
            if(tmp.length() == 1){
                tmp = "0" + tmp;
            }
            pw = new PrintWriter(new File("EditedPageFiles/" + tmp + ".pg")); // to overwrite the page file
            int RAMindex = p.getEntry(pointer).getPageframe();
            for(int i=0; i<mem.RAM[RAMindex].length;i++){
                sb.append(mem.RAM[RAMindex][i] + "\n");
            }
            pw.write(sb.toString()); // write string builder to file
            pw.close();
            dirtySet = 1; // dirty was set    
        }
        else{
            dirtySet = 0; // dirty was not set
        }

        evictedPage = pointer;
        writeIndex = p.getEntry(pointer).getPageframe(); // the open index in RAM
        p.PT[pointer] = new PageTableEntry(); // resets bits in Entry
        
        if (t.getEntry(pointer) != null) {
            // t.getEntry(pointer).resetReference();
            // t.getEntry(pointer).resetDirty();
            // t.getEntry(pointer).resetvalid();
            // t.getEntry(pointer).resetpageframe();
            String tmp = Integer.toHexString(pointer);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            t.evictTlb(tmp);
        }
        
        // go to next page table entry
        if (pointer == p.getLength() - 1) {
            pointer = 0;
        } else {
            pointer++;
        }

        int[] ret = {writeIndex, evictedPage, dirtySet};
        return ret;
    }

}