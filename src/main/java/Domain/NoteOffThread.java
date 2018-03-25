package Domain;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class NoteOffThread extends Thread {

    private final ShortMessage messageOff;
    private final Receiver receiver;

    public NoteOffThread(ShortMessage messageOff, Receiver receiver) {

        this.messageOff = messageOff;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        receiver.send(messageOff, -1);
    }
}
