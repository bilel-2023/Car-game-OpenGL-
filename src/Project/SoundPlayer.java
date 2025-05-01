package Project;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {
    private Clip clip;
    private List<Clip> activeClips = new ArrayList<>(); // List to keep track of all active clips

    // Play a sound given the file path and whether to loop it
    public void playSound(String filePath, boolean loop) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // ⏪ loop forever
            } else {
                clip.start(); // play once
            }

            activeClips.add(clip); // Add the clip to the active list

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Stop the currently playing sound
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Stop all running sounds
    public void stopAllSounds() {
        for (Clip activeClip : activeClips) {
            if (activeClip != null && activeClip.isRunning()) {
                activeClip.stop();
            }
        }
        activeClips.clear(); // Clear the list after stopping all clips
    }

    // Play a crash sound and stop background music
    public void playCrashSound(String filePath) {
        stop(); // Stop the background music
        playSound(filePath, false); // Play the crash sound once
    }

    // Play the win sound and stop background music
    public void WinSound(String filePath) {
        stop(); // Stop the background music
        playSound(filePath, false); // Play the win sound once
    }

    // Play the money sound, but don't stop background music
    public void MoneySound(String filePath) {
        playSound(filePath, false); // Play the money sound once (no stop)
    }
}
