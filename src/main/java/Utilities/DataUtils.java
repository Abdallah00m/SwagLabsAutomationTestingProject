package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Properties;

public class DataUtils {
    public static final String TestData_PATH = "src/test/resources/TestData/";

    //Read data from json file
    public static String getJsondata(String jsonFileName, String field){
        try{
            //define object of file reader
            FileReader reader = new FileReader(TestData_PATH+jsonFileName+".json");
            //parse the json into json element
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //get properties from any .properties fie
    public static String getPropertyData(String fileName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TestData_PATH+fileName+".properties"));
        return properties.getProperty(key);
    }
}
