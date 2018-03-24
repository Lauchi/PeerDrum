package ClientSide;

import Domain.DrumSet;
import Domain.DrumTrack;
import Domain.MidiSender;
import Domain.TimeStep;
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

public class PeerDrumClient implements TimerListener {

    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JButton button = new JButton("Send");
    JTextArea messageArea = new JTextArea(8, 40);
    private String serverIp;
    private int serverPort;
    private boolean isMaster;
    private long bpm;
    DrumSet drumSet = new DrumSet();
    private boolean isStarted;
    ObjectMapper objectMapper = new ObjectMapper();
    private long startTime;
    DrumTimer timer;
    private MidiSender midiSender;
    private boolean sendStartBit;

    public PeerDrumClient(String serverIp, int serverPort, boolean isMaster, long bpm) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.isMaster = isMaster;
        this.bpm = bpm;
        this.timer = new DrumTimer(this.bpm);
        this.midiSender = new MidiSender();

        sendStartBit = false;

        messageArea.setEditable(false);
        frame.getContentPane().add(button, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DrumSet drumSet = new DrumSet();
                try {
                    String json = objectMapper.writeValueAsString(drumSet);
                    out.println(json);
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
            }
        });

        timer.addListener(this);
        timer.start();
    }

    public void run() throws IOException {
        String serverAddress = this.serverIp;
        Socket socket = new Socket(serverAddress, this.serverPort);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (this.sendStartBit) {
                out.println("START");
                messageArea.append(line + "\n");
                this.sendStartBit = false;
            }
            if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
                this.drumSet = objectMapper.readValue(line.substring(8), DrumSet.class);
            } else if (line.startsWith("START")) {
                this.isStarted = true;
                long nanoTime = Long.parseLong(line.substring(6));
                this.startTime = nanoTime;
            }
        }
    }

    @Override
    public void tick(int timer) {

        messageArea.append("Tick!\n");
        for (DrumTrack drumTrack : this.drumSet.tracks) {
            TimeStep timeStep = drumTrack.getSteps().get(timer);
            midiSender.Send(timeStep);
        }
    }

    @Override
    public void start() {
        this.sendStartBit = true;
    }
}