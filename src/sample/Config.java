package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "./client.properties";

    public static int PORT;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try{
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
        } catch (FileNotFoundException fnfe){
            Controller.Print("Properties config file not found");
        } catch (IOException ioe){
            Controller.Print(ioe.getLocalizedMessage());
        } finally {
            try{
                propertiesFile.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
