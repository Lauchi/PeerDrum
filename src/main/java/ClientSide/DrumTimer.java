package ClientSide;

import java.util.ArrayList;
import java.util.List;

public class DrumTimer implements Runnable{
    private double timerTrigger;
    private List<TimerListener> listeners = new ArrayList<>();
    long startTime;

    public DrumTimer(double bpm) {
        this.timerTrigger = 1000 / bpm;
        startTime = System.nanoTime();
    }

    public void addListener(TimerListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void run() {
        while (true) {
            if (startTime + System.nanoTime() > this.timerTrigger) {

            }
        }
    }
}
