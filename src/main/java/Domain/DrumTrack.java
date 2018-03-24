package Domain;

import java.util.ArrayList;
import java.util.List;

public class DrumTrack {
    private int size;
    private int number;
    private List<TimeStep> steps;

    public DrumTrack(int number) {
        this.number = number;
        this.steps = new ArrayList<>();
        for (int i = 0; i <16; i++) {
            steps.add(new TimeStep());
        }
    }

    public List<TimeStep> getSteps() {
        return steps;
    }

    public int getSize() {
        return steps.size();
    }

    public void toggleStep(int stepNo) {
        this.steps.get(stepNo).isSet = !this.steps.get(stepNo).isSet;
    }
}
