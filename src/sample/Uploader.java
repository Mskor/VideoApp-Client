package sample;
import java.io.*;
import java.net.Socket;

/**
 *
 */
public class Uploader extends Thread {
    /**
     * Defines the host
     * this client should upload to.
     */
    Socket ClientSocket;

    /**
     * The file that should be uploaded to the host which is defined {@link #ClientSocket ClientSocket}.
     *
     */
    String FileName;

    private FileMetadata fileMetadata;
    Uploader(Socket ClientSocket, String FileName){
        this.FileName = FileName;
        this.fileMetadata = new FileMetadata(new File(FileName));
        this.ClientSocket = ClientSocket;
        this.run();
    }
    /**
     * Writes a file to the host
     * through the 8kB byte buffer
     */
    @Override
    public void run() {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientSocket.getOutputStream());
            objectOutputStream.writeObject(fileMetadata);


            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FileName));
            BufferedOutputStream bos = new BufferedOutputStream(ClientSocket.getOutputStream());
            Controller.Print("Buffers initialized! \n");

            int in;
            byte[] byteArray = new byte[8192];
            Controller.Print("Processing upload... \n");
            while ((in = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
            }
            bis.close();
            bos.close();

            Controller.Print("Upload finished! \n");
        }catch (FileNotFoundException fnfe){
            Controller.Print(fnfe.getMessage() + "\n");
            CloseChannel();
        }catch (Exception e){
            Controller.Print(e.getMessage() + "\n");
            CloseChannel();
        }
    }

    /**
     * Close the connection between the client and the host.
     */
    void CloseChannel(){
        try {
            ClientSocket.close();
        }catch (IOException ioe){
            Controller.Print(ioe.getMessage() + "\n");
        }
    }
}
