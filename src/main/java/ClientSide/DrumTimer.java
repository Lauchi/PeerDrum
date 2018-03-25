package ClientSide;

import java.util.ArrayList;
import java.util.List;

public class DrumTimer extends Thread {
    private double bpmToTime;
    private List<TimerListener> listeners = new ArrayList<>();
    long startTime;
    private int tick;

    public DrumTimer(double bpm) {
        this.bpmToTime = 60.0 / bpm / 4 * 1000000000.0;
        tick = 0;
        startTime = System.nanoTime();
    }

    public void addListener(TimerListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void run() {
        while (true) {
            long timeElapsed = System.nanoTime() - startTime;
            if (timeElapsed > this.bpmToTime) {
                for (TimerListener listener : listeners) {
                    listener.tick((this.tick ++) % 16);
                }

                this.startTime = System.nanoTime();
            }
        }
    }

    public void resetStartTime() {
        this.startTime = System.nanoTime();
        this.tick = 0;
    }
}
