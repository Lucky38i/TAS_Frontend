import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {


    public static void main(String[] args) {
        Alert test = new Alert("Ford", 2080);
        ObjectMapper mapper = new ObjectMapper();
        final int port = 8080;
        final String host = "localhost";

        try (Socket socket = new Socket(host, port);
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))){

            System.out.println("Connection Successful:" + socket.getInetAddress());

            String jsonString = mapper.writeValueAsString(test);
            //System.out.println(jsonString);
            toServer.write(jsonString);
            toServer.flush();

            System.out.println(fromServer.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
