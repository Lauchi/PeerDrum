package Domain;

import java.util.ArrayList;
import java.util.List;

public class DrumMachine implements Runnable{
    private final MidiSender midiSender;
    List<DrumTrack> tracks = new ArrayList<>();
    public long sleepTime = 1000 / 128;
    private int currentStep = 1;
    private int maxSteps;

    public DrumMachine(int maxSteps) {
        this.midiSender = new MidiSender();
        this.maxSteps = maxSteps;
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < maxSteps; i++) {
                    TimeStep timeStep = tracks.get(i).getSteps().get(currentStep);
                    this.midiSender.Send(timeStep);
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
