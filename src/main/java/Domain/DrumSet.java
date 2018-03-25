package Domain;

import java.util.ArrayList;
import java.util.List;

public class DrumSet {
    public List<DrumTrack> tracks;

    public DrumSet() {
        tracks = new ArrayList<>();
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
        tracks.add(new DrumTrack());
    }

    public void setStep(int step, boolean isSet) {
        tracks.get(0).toggleStep(step);
    }
}
