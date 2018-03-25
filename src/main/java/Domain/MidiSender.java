package Domain;

import javax.sound.midi.*;

public class MidiSender {
    public void Send(TimeStep timeStep, int note) {
        if (timeStep.isSet) {
            try (Receiver receiver = MidiSystem.getReceiver()) {
                ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 0, 0 + note, 100);
                ShortMessage messageOff = new ShortMessage(ShortMessage.NOTE_OFF, 0, 0 + note, 100);
                receiver.send(message, -1);
                receiver.send(messageOff, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNote(int stepNo) {
        try (Receiver receiver = MidiSystem.getReceiver()) {
            ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 1, stepNo + 40, 100);
            ShortMessage messageOff = new ShortMessage(ShortMessage.NOTE_OFF, 1, stepNo + 40, 100);
            receiver.send(message, -1);


            NoteOffThread noteOffThread = new NoteOffThread(messageOff, receiver);
            noteOffThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
