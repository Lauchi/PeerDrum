package Domain;

import java.util.List;

public class DrumTrack {
    private int size;
    private List<TimeStep> steps;

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
