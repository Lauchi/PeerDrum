package ClientSide;

import Domain.DrumSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class PeerDrumClient {

    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JButton button = new JButton("Send");
    JTextArea messageArea = new JTextArea(8, 40);
    private String serverIp;
    private int serverPort;

    public PeerDrumClient(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;

        messageArea.setEditable(false);
        frame.getContentPane().add(button, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DrumSet drumSet = new DrumSet();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(drumSet);
                    out.println(json);
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void run() throws IOException {
        String serverAddress = this.serverIp;
        Socket socket = new Socket(serverAddress, this.serverPort);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }
}