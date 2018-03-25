package Domain;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class SendNoteThread extends Thread {

    private int stepNo;

    public SendNoteThread(int stepNo){
        this.stepNo = stepNo;
    }

    @Override
    public void run() {
        try (Receiver receiver = MidiSystem.getReceiver()) {
            ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 1, stepNo + 40, 100);
            ShortMessage messageOff = new ShortMessage(ShortMessage.NOTE_OFF, 1, stepNo + 40, 100);

            receiver.send(message, -1);
            Thread.sleep(200);
            receiver.send(messageOff, -1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
