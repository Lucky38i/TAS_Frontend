import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private String host;
    private int port;

    TCPConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
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
    }
}
