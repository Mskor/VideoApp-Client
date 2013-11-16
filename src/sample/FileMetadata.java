package sample;

import java.io.File;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 *
 */
public class FileMetadata implements Serializable{
    String Extension;
    long size;
    FileMetadata(File file){
        this.size = file.length();
        StringTokenizer st = new StringTokenizer(file.getName(), ".");
        while(st.hasMoreTokens()) this.Extension = st.nextToken();
    }
}
