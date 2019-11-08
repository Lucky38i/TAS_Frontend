import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {


    public static void main(String[] args) {
        String host = "192.168.0.52";
       int portNumber = 8080;

        try(Socket socket = new Socket(host, portNumber);
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))){


            System.out.println("Connected to: " + socket.getInetAddress());

            toServer.write("Hello from Java Client");
            toServer.flush();


            System.out.println("Reading from Server");
            System.out.println(fromServer.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}
