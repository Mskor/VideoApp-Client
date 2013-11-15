package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A handle class to JavaFX interface.
 * The structure of the interface is defined in sample.fxml
 * file that is generated by JavaFX Scene Builder 1.1
 * {@literal @FXML} tag is used before a field or a method to grant it visibility
 * to FXML Loader so they can be handled in a correct way.
 *
 * To observe exact process of upload see {@link Uploader}
 * and exactly {@link Uploader#run()} method.
 *
 * @author Yakovlev Oleg NNTU
 * @version 0.4
 */
public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * fx:id refers to the File Chooser text field
     * which allows to choose a file for upload
     */
    @FXML
    private TextField FilePth;

    /**
     * fx:id refers to text field
     * that defines host address
     */
    @FXML
    private TextField HostName;

    @FXML
    private AnchorPane InputPane;

    @FXML
    private AnchorPane MainFrame;

    @FXML
    private AnchorPane OutputPane;

    /**
     * fx:id refers to the
     * "Quit" button
     */
    @FXML
    private Button QuitButton;

    @FXML
    private Button RunButton;

    /**
     * Text area to output status
     * information
     */
    @FXML
    static private TextArea TextOut;

    /**
     * Handles "Quit" button
     * If left-clicked the app is closed
     * @since 0.1
     * @param event a handle for a particular mouse event
     *              related to "Quit" button
     */
    @FXML
    void QuitClickHandle(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY){
              System.exit(0);
        }
    }

    /**
     * Handles a left-click of the "Run" button
     * If the click is done launches server socket listener cycle,
     * according to information, defined by user in appropriate text fields
     *
     * For every client accepted it creates new thread,
     * so it is thread safe
     *
     * @since 0.1
     * @param event a handle for a particular mouse event
     *              related to "Run" button
     */

    @FXML
    void RunUpload(MouseEvent event){

        if(event.getButton() == MouseButton.PRIMARY){

        CheckDefine();
        final String host = HostName.getText();
        final String FileName = FilePth.getText();

            Thread RunThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TextOut.appendText("Pending the host server... \n");
                        Socket ClientSocket = new Socket(host, 8080);
                        TextOut.appendText("Connected! \n");
                        new Uploader(ClientSocket, FileName);
                    } catch (UnknownHostException uhe){
                        TextOut.appendText("Can't resolve host name");
                    }catch (IOException ioe){
                        TextOut.appendText("Can't reach host destination");
                    }
                }
            });

        RunThread.start();
        }

    }

    /**
     * Handles click on File Chooser button
     *
     * On left-click sets text value on FilePth text field
     * to the path String to chosen file
     * @param event a handle for a particular mouse event
     *              related to "File Chooser" button
     */
    @FXML
    void GetFile (MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Video Files", "mp4", "wmv", "avi", "mkv");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                FilePth.setText(chooser.getSelectedFile().getPath());
            }
        }
    }
    @FXML
    void initialize() {
        assert FilePth != null : "fx:id=\"FileName\" was not injected: check your FXML file 'sample.fxml'.";
        assert HostName != null : "fx:id=\"HostName\" was not injected: check your FXML file 'sample.fxml'.";
        assert InputPane != null : "fx:id=\"InputPane\" was not injected: check your FXML file 'sample.fxml'.";
        assert MainFrame != null : "fx:id=\"MainFrame\" was not injected: check your FXML file 'sample.fxml'.";
        assert OutputPane != null : "fx:id=\"OutputPane\" was not injected: check your FXML file 'sample.fxml'.";
        assert QuitButton != null : "fx:id=\"QuitButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert RunButton != null : "fx:id=\"RunButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert TextOut != null : "fx:id=\"TextOut\" was not injected: check your FXML file 'sample.fxml'.";
    }

    /**
     * Checks if the file and the host
     * are defined in appropriate text fields.
     */
    void CheckDefine(){
        if(HostName.getText().isEmpty() || FilePth.getText().isEmpty()){
            TextOut.appendText("Following items undefined: \n");
            if(HostName.getText().isEmpty()){
                TextOut.appendText("Host name \n");
            }

            if(FilePth.getText().isEmpty()){
                TextOut.appendText("File path \n");
            }
        }
    }

    static void Print(String foo){
        TextOut.appendText(foo);
    }
}
