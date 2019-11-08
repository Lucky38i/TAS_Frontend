import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {


    public static void main(String[] args) {
        String host = "localhost";
       int portNumber = 3848;

        try{

            Socket socket = new Socket(host, portNumber);
            if (socket.isConnected()) System.out.println("Connected");
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(socket.getLocalAddress());

            toServer.writeUTF("Hello this is the Java Client");
            toServer.flush();

            while(!socket.isClosed()) {
                if (fromServer.available() > 0) {
                    String input = fromServer.readUTF();
                    System.out.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}
