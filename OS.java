
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
    public OS(PageTable pt, TLBcache tlb, PhysicalMemory m) throws IOException {
        // PageTable and memory are passed in
        p = pt;
        mem = m;
    }
    // Default constructor for testing DELETE IT LATER!!!!!!!!!!!!!
    public OS() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        // to test that Copying is happening correctly
        //OS op = new OS();
        //op.addPage("AA00");

        // PageTable p = new PageTable();
        // TLBcache tl = new TLBcache();
        // System.out.println(tl.getEntry(0).getReference());
        // tl.getEntry(0).setReference();
        // System.out.println(tl.getEntry(0).getReference());
        // op.resetRef(p, tl);
        // System.out.println(tl.getEntry(0).getReference());
        // System.out.println(op);

        // if(pfolder.isDirectory())
        //     System.out.println("Got IT!!!");
        // else
        //     System.out.println("Don't Got it (-_-)");

        // for(File pfile: pfolder.listFiles()){
        //     System.out.println(pfile.getName());
        // }
    }

    // reset reference bits must pass in TLB
    public void resetRef(TLBcache t) {
        // reset PageTable Reference bits
        for (int j = 0; j < p.length; j++) {
            p.getEntry(j).resetReference();
        }
        // reset TLB R bits
        for (int i = 0; i < t.length; i++) {
            t.getEntry(i).resetReference();
        }
    }

    // Reads page file and puts it in Physical Memory
    public int[] addPage(TLBcache t, String pageNum) throws IOException{
        int[] pFile = new int[256];
        String frameNo = pageNum.substring(0, 2);
        String pageFile = "EditedPageFiles/" + frameNo + ".pg";
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
            replace = pageReplace(t, frameNo);
            mem.writeMemoryFULL(pFile, replace[0]);
            p.getEntry(frameNo).writeEntry(replace[0]); // index written
            evictedPage = replace[1];
            dirtySet = replace[2];
        }
        else{
            p.getEntry(frameNo).writeEntry(writeMemory(pFile)); // empty index written
        }
        
        int[] ret = {evictedPage, dirtySet};
        return ret;
    }

    // clock replacement for page table
    private int[] pageReplace(TLBcache t, String frameNo) throws IOException{
        int writeIndex = -1;
        int evictedPage = -1;
        int dirtySet = -1;
        StringBuilder sb = new StringBuilder(); // new stringbuilder to store output
        // reset R bit until an entry is found with R bit already == 0
        while(p.getEntry(pointer).getReference() == 1){
            p.getEntry(pointer).resetReference();
            // If page table entry is also in TLB reset its R bit
            if(t.getEntry(pointer) != null){
                t.getEntry(pointer).resetReference();
            }
            // go to next page table entry
            if(pointer == p.PT.length-1){
                pointer = 0;
            }
            else{
                pointer++;
            }
        }
        // if the page table at pointer already has (R bit == 0) and (V bit == 1)
        if(p.getEntry(pointer).getDirty() == 1){
            pw = new PrintWriter(new File("EditedPageFiles/" + Integer.toHexString(pointer) + ".pg")); // to overwrite the page file
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
        
        // SHOULD THERE BE A POINTER++ HERE SO IT WILL START ON THE NEXT ONE NEXT TIME?????????????????????????????????

        int[] ret = {writeIndex, evictedPage, dirtySet};
        return ret;
    }

}