
/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/
import java.io.*;

public class OS {
    public int pointer = 0; // ptr for page replacement
    private PageTable p;
    private TLB t;

    // constructor
    public OS(PageTable pt, TLB tlb) throws IOException {
        String fileSource = "OriginalPageFiles/";
        String destination = "EditedPageFiles/";
        File srcDir = new File(fileSource);
        File destDir = new File(destination);
        copyAll(srcDir, destDir);
        // PageTable and TLB are passed in
        p = pt;
        t = tlb;
    }

    public static void main(String[] args) throws IOException {
        // to test that Copying is happening correctly
        OS op = new OS();

        PageTable p = new PageTable();
        TLBcache tl = new TLBcache();
        System.out.println(tl.getEntry(0).getReference());
        tl.getEntry(0).setReference();
        System.out.println(tl.getEntry(0).getReference());
        op.resetRef(p, tl);
        System.out.println(tl.getEntry(0).getReference());
        System.out.println(op);

        // if(pfolder.isDirectory())
        //     System.out.println("Got IT!!!");
        // else
        //     System.out.println("Don't Got it (-_-)");

        // for(File pfile: pfolder.listFiles()){
        //     System.out.println(pfile.getName());
        // }
    }

    // Function to copy original files
    public void copyAll(File sourceDir, File destDir) throws IOException {
        if (sourceDir.isDirectory()) {
            copyDir(sourceDir, destDir);
        } else {
            copyFile(sourceDir, destDir);
        }
    }
    // copy directories
    private void copyDir(File source, File target) throws IOException {
        if (target.exists() != true) {
            target.mkdir();
        }
        for (String s : source.list()) { // .list returns all of the file names in the directory
            copyAll(new File(source, s), new File(target, s));
        }
    }
    // copy files
    private void copyFile(File source, File target) throws IOException {
        // try catch finally so even if there is an exception buffer still closes
        try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target);) {
            byte[] b = new byte[1024]; // buffer to hold file contents for copying
            int length;
            while ((length = in.read(b)) > 0) { // keeps copying until end of file
                out.write(b, 0, length);
            }
        }
    }

    // reset reference bits must pass in Page Table and TLB
    public void resetRef(PageTable p, TLBcache t) {
        // reset PageTable Reference bits
        for (int j = 0; j < p.length; j++) {
             p.getEntry(j).resetReference();
        }
        // reset TLB R bits
        for (int i = 0; i < t.length; i++) {
             t.getEntry(i).resetReference();
        }
    }

    // clock replacement for page table
    public int pageReplace(PageTable p, TLBcache t, int pageNum){
        // reset R bit until an entry is found with R bit already == 0
        while(p.getEntry(pointer).getReference() == 1){
            p.getEntry(pointer).resetReference();
            
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
        // if the page table at pointer already has (R bit == 0)
        if(true){}
        return 0; // CHANGE THIIS TO RETURN EVICTED PAGE
    }
}