/*
*   CS 431 Project 2
*   Hamza Parekh
*   Matthew Li
*   Kunal Purohit
*   Dimitri Pierre-Louis
*/
import java.io.*;

public class OS {

    // constructor
    public OS() throws IOException{
        String fileSource = "OriginalPageFiles/";
        String destination = "EditedPageFiles/";
        File srcDir = new File(fileSource);
        File destDir = new File(destination);
        copyAll(srcDir, destDir);
    }

    public static void main(String[] args) throws IOException{
        // to test that Copying is happening correctly
        OS op = new OS();
        System.out.println(op);


        // if(pfolder.isDirectory())
        //     System.out.println("Got IT!!!");
        // else
        //     System.out.println("Don't Got it (-_-)");
        
        // for(File pfile: pfolder.listFiles()){
        //     System.out.println(pfile.getName());
        // }
    }

    public void copyAll(File sourceDir, File destDir) throws IOException{
        if (sourceDir.isDirectory()) {
            copyDir(sourceDir, destDir);
        }
        else {
            copyFile(sourceDir, destDir);
        }
    }
    private void copyDir(File source, File target) throws IOException{
        if(target.exists() != true){
            target.mkdir();
        }
        for(String s: source.list()){ // .list returns all of the file names in the directory
            copyAll(new File(source, s), new File(target, s));
        }
    }
    private void copyFile(File source, File target) throws IOException{
        // try catch finally so even if there is an exception buffer still closes
        try(
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target);
        ) {
            byte[] b = new byte[1024]; // buffer to hold file contents for copying
            int length;
            while((length = in.read(b))>0){ // keeps copying until end of file
                out.write(b, 0, length);
            }
        } 
    }
}