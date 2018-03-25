package ClientSide;

import Domain.DrumSet;
import Domain.DrumTrack;
import Domain.MidiSender;
import Domain.TimeStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PeerDrumClient extends Thread implements TimerListener {

    BufferedReader in;
    PrintWriter out;
    private String serverIp;
    private int serverPort;
    private boolean _isMaster;
    private long bpm;
    DrumSet drumSet = new DrumSet();
    ObjectMapper objectMapper = new ObjectMapper();
    DrumTimer timer;
    private MidiSender midiSender;

    public PeerDrumClient(String serverIp, int serverPort, final boolean isMaster, long bpm) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this._isMaster = isMaster;
        this.bpm = bpm;
        this.timer = new DrumTimer(this.bpm);
        this.midiSender = new MidiSender();

        timer.addListener(this);
        timer.start();
    }

    public void setStep(int noteNo, int stepNo, boolean isSet) {
        drumSet.setStep(noteNo, stepNo, isSet);
    }

    public void sendDrumSet(DrumSet drumSet) {
        try {
            String json = objectMapper.writeValueAsString(drumSet);
            out.println(json);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
    }

    public void sync() {
        if (_isMaster) out.println("START");
    }

    @Override
    public void run() {
        String serverAddress = this.serverIp;
        try {
            Socket socket = new Socket(serverAddress, this.serverPort);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String line = in.readLine();
                if (line != null) {
                    if (line.startsWith("DRUMSET ")) {
                        this.drumSet = objectMapper.readValue(line.substring(8), DrumSet.class);
                    } else if (line.startsWith("START")) {
                        System.out.println("Start");
                        this.timer.resetStartTime();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick(int timer) {
        for (int i = 0; i < drumSet.tracks.size(); i++) {
            TimeStep timeStep = drumSet.tracks.get(i).getSteps().get(timer);
            System.out.println("TICK " + timer + " " +  i + " " +  timeStep.isSet);
            midiSender.Send(timeStep, i);
        }
    }
}