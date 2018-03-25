package ClientSide;

import Domain.DrumSet;
import Domain.MidiSender;
import Domain.TimeStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.DrumSetListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PeerDrumClient extends Thread implements TimerListener{

    BufferedReader in;
    PrintWriter out;
    private String serverIp;
    private int serverPort;
    private boolean _isMaster;
    private long bpm;
    public DrumSet drumSet = new DrumSet();
    ObjectMapper objectMapper = new ObjectMapper();
    DrumTimer timer;
    private MidiSender midiSender;
    private List<DrumSetListener>listeners = new ArrayList<>();

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

    public void setStepAndBroadcast(int channelNo, int stepNo, boolean isSet) {
        drumSet.setStep(channelNo, stepNo, isSet);
        this.broadCastDrumSet();
    }

    private void broadCastDrumSet() {
        try {
            String json = objectMapper.writeValueAsString(drumSet);
            out.println(json);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
    }

    public void sync() {
        out.println("START");
        this.broadCastDrumSet();
    }

    public void addListener(DrumSetListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void run() {
        String serverAddress = this.serverIp;
        try {
            Socket socket = new Socket(serverAddress, this.serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String line = in.readLine();
                if (line != null) {
                    if (line.startsWith("DRUMSET ")) {
                        this.drumSet = objectMapper.readValue(line.substring(8), DrumSet.class);
                        for (DrumSetListener listener : listeners) {
                            listener.updateDrumSet();
                        }
                    } else if (line.startsWith("START")) {
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
            midiSender.Send(timeStep, i);
        }
    }


}