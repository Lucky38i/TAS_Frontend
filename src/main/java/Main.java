import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class Main {


    public static void main(String[] args) {
        Alert test = new Alert("Toyota", 1010);
        ObjectMapper mapper = new ObjectMapper();
        TCPConnection newConn = new TCPConnection("192.168.0.52", 8080);
        try {
            mapper.writeValue(new File("test.json"), test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
