package Domain;

import java.util.ArrayList;
import java.util.List;

public class DrumSet {
    public List<DrumTrack> tracks;

    public DrumSet() {
        tracks = new ArrayList<>();
        tracks.add(new DrumTrack(1));
        tracks.add(new DrumTrack(2));
        tracks.add(new DrumTrack(3));
        tracks.add(new DrumTrack(4));
    }
}
