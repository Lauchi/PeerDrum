package Domain;

import javax.sound.midi.*;

public class MidiSender {
    public void Send(TimeStep timeStep) {
        if (timeStep.isSet) {
            try (Receiver receiver = MidiSystem.getReceiver()) {
                ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 93);
                receiver.send(message, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
