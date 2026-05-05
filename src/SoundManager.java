import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, AudioClip> sfx = new HashMap<>();
    private static MediaPlayer bgmPlayer;
    private static final String SOUND_DIR = "src/Sounds/";
    private static boolean muted = false;

    static {
        // Initialize JavaFX platform (required for Media classes in Swing)
        new JFXPanel();
        
        // Load sounds from local directory with exact filenames provided
        loadSFX("pop", "pop_sound.mp3");
        loadSFX("shoot", "shooting_sound.mp3");
        loadSFX("drop", "drop_sound.mp3");
        loadSFX("win", "win_sound.mp3");
        loadSFX("lose", "lose_sound.mp3");
        loadSFX("click", "click_sound.mp3");
        
        loadBGM("BGM.mp3");
    }

    private static void loadSFX(String name, String fileName) {
        try {
            File file = new File(SOUND_DIR + fileName);
            if (file.exists()) {
                AudioClip clip = new AudioClip(file.toURI().toString());
                sfx.put(name, clip);
            } else {
                System.err.println("SFX file not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error loading SFX " + name + ": " + e.getMessage());
        }
    }

    private static void loadBGM(String fileName) {
        try {
            File file = new File(SOUND_DIR + fileName);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                bgmPlayer = new MediaPlayer(media);
                bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                bgmPlayer.setVolume(0.3); // Reduced volume by default
            } else {
                System.err.println("BGM file not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error loading BGM: " + e.getMessage());
        }
    }

    public static void play(String name) {
        if (muted) return;
        AudioClip clip = sfx.get(name);
        if (clip != null) {
            clip.play();
        }
    }

    public static void startBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.play();
            bgmPlayer.setMute(muted);
        }
    }

    public static void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
    }

    public static void setBGMVolume(double volume) {
        if (bgmPlayer != null) {
            bgmPlayer.setVolume(volume);
        }
    }

    public static void toggleMute() {
        muted = !muted;
        if (bgmPlayer != null) {
            bgmPlayer.setMute(muted);
        }
    }

    public static boolean isMuted() {
        return muted;
    }
}
