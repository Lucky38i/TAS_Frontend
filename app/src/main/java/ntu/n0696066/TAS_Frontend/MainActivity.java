package ntu.n0696066.TAS_Frontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Thread serverConnect;
    EditText et_IpAddress, et_ServerPort, et_ChatBox;
    Button btn_Connect;
    CheckBox chbx_Connected;
    String SERVER_IP;
    int SERVER_PORT;
    ObjectMapper mapper;
    Alert test;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_IpAddress = findViewById(R.id.et_ServerIp);
        et_ServerPort = findViewById(R.id.et_ServerPort);
        btn_Connect = findViewById(R.id.btn_Connect);
        chbx_Connected = findViewById(R.id.chkbx_Connected);
        et_ChatBox = findViewById(R.id.et_ChatBox);
        alert = new AlertDialog.Builder(this);




        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_IpAddress.getText().toString().isEmpty() || et_ServerPort.getText().toString().isEmpty()) {
                    alert.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                    alert.setMessage("Please complete all required field");
                    alert.setNeutralButton("OK", (dialog, which) -> dialog.dismiss());
                    alert.show();
                } else {
                    SERVER_IP = et_IpAddress.getText().toString();
                    SERVER_PORT = Integer.parseInt(et_ServerPort.getText().toString());
                    mapper = new ObjectMapper();
                    test = new Alert("Ford", 2080);
                    serverConnect = new Thread(new bgThread());
                    serverConnect.start();
                }
            }
        });
    }
    class bgThread implements Runnable {

        @Override
        public void run() {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                         StandardCharsets.UTF_8));
                 BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                         StandardCharsets.UTF_8))) {

                runOnUiThread(()-> {
                    et_ChatBox.append("Connected to: " + socket.getInetAddress() + "\n");
                    chbx_Connected.setChecked(true);
                });

                String jsonString = mapper.writeValueAsString(test);

                toServer.write(jsonString);
                toServer.flush();
                String fromServerString = fromServer.readLine();
                runOnUiThread(()-> et_ChatBox.append(fromServerString+"\n"));
            }
            catch(IOException e ) {
                e.printStackTrace();
            }
            finally {
                runOnUiThread(()-> chbx_Connected.setChecked(false));
            }
        }
    }
}
