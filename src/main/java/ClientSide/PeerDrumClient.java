package ClientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

/**
 * A simple Swing-based client for the chat server.  Graphically
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole dialog.
 *
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */
public class PeerDrumClient {

    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JButton button = new JButton("Send");
    JTextArea messageArea = new JTextArea(8, 40);
    private String cliendId;
    private String serverIp;

    public PeerDrumClient(String cliendId, String serverIp) {

        this.cliendId = cliendId;
        this.serverIp = serverIp;
        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(button, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        button.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
                out.println("Lul");
            }
        });
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    public void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = this.serverIp;
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(this.cliendId);
            } else if (line.startsWith("NAMEACCEPTED")) {
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }
}