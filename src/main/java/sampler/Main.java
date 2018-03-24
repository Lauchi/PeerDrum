package sampler;

import javax.sound.sampled.*;
import java.io.File;

public class Main {
    private static Clip get_clip(String name) throws Exception {
        AudioInputStream stream = AudioSystem.getAudioInputStream(new File(name));
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
        Clip clip = (Clip)AudioSystem.getLine(info);
        clip.open(stream);
        return clip;
    }
    public static void main(String[] args) {
        try {
            Clip c1 = get_clip("clap01.wav");
            Clip c2 = get_clip("kick01.wav");
            c1.start();
            c2.start();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
