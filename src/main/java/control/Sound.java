package control;

import boardifier.control.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utils.FileUtils;

import java.io.File;

/**
 * The {@code Sound} class provides methods to manage and play sound effects and music
 * within the game "Le Roi des Roses".
 */
public class Sound {

    /** The current background music player. */
    static MediaPlayer currentMusic;

    /** Flag indicating if music is enabled. */
    static boolean musicOn = true;

    /** Flag indicating if sound effects are enabled. */
    static boolean soundOn = true;

    /** Volume level for music. */
    static double musicVolume = 0.5;

    /** Volume level for sound effects. */
    static double soundVolume = 0.5;

    /**
     * Plays a sound effect.
     *
     * @param sound the name of the sound file to play
     */
    public static void playSound(String sound) {
        if (soundOn) {
            try {
                final File audioFile = FileUtils.getFileFromResources("sounds/" + sound);
                final String path = audioFile.toPath().toUri().toString();
                Logger.trace("Playing sound: " + sound);
                final Media media = new Media(path);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }
    }

    /**
     * Plays background music.
     *
     * @param sound the name of the music file to play
     */
    public static void playMusic(String sound) {
        try {
            final File audioFile = FileUtils.getFileFromResources("sounds/" + sound);
            final String path = audioFile.toPath().toUri().toString();
            Logger.trace("Playing music from: " + path);
            final Media media = new Media(path);
            if (currentMusic != null) {
                if (currentMusic.getMedia().getSource().contains(sound)) return;
                currentMusic.stop();
            }
            currentMusic = new MediaPlayer(media);
            if (!musicOn) return;
            currentMusic.setOnEndOfMedia(() -> {
                currentMusic.seek(Duration.millis(0));
                currentMusic.setVolume(musicVolume);
                currentMusic.play();
            });
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * Plays background music starting from a specified time.
     *
     * @param sound the name of the music file to play
     * @param ms    the start time in milliseconds
     */
    public static void playMusic(String sound, long ms) {
        try {
            final File audioFile = FileUtils.getFileFromResources("sounds/" + sound);
            final String path = audioFile.toPath().toUri().toString();
            Logger.trace("Playing music from: " + path);
            final Media media = new Media(path);
            if (currentMusic != null) {
                if (currentMusic.getMedia().getSource().contains(sound)) return;
                currentMusic.stop();
            }
            currentMusic = new MediaPlayer(media);
            if (!musicOn) return;
            currentMusic.setOnEndOfMedia(() -> {
                currentMusic.seek(Duration.millis(ms));
                currentMusic.setVolume(musicVolume);
                currentMusic.play();
            });
            currentMusic.setStartTime(Duration.millis(ms));
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * Toggles the music on or off.
     */
    public static void musicSwitch() {
        if (musicOn) {
            try {
                final File audioFile = FileUtils.getFileFromResources("sounds/p1.wav");
                final String path = audioFile.toPath().toUri().toString();
                final Media media = new Media(path);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
            currentMusic.stop();
        }
        if (!musicOn) {
            try {
                final File audioFile = FileUtils.getFileFromResources("sounds/p2.wav");
                final String path = audioFile.toPath().toUri().toString();
                final Media media = new Media(path);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
            currentMusic.stop();
            currentMusic.setOnEndOfMedia(() -> {
                currentMusic.seek(Duration.millis(0));
                currentMusic.setVolume(musicVolume);
                currentMusic.play();
            });
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        }
        musicOn = !musicOn;
    }

    /**
     * Toggles the sound effects on or off.
     */
    public static void soundSwitch() {
        if (soundOn) {
            try {
                final File audioFile = FileUtils.getFileFromResources("sounds/p10.wav");
                final String path = audioFile.toPath().toUri().toString();
                final Media media = new Media(path);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        } else {
            try {
                final File audioFile = FileUtils.getFileFromResources("sounds/p9.wav");
                final String path = audioFile.toPath().toUri().toString();
                final Media media = new Media(path);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }
        soundOn = !soundOn;
    }

    /**
     * Stops the current background music.
     */
    public static void stopMusic() {
        currentMusic.stop();
    }

    /**
     * Sets the volume for sound effects.
     *
     * @param volume the volume level to set (0.0 to 1.0)
     */
    public static void setSoundVolume(double volume) {
        soundVolume = volume;
    }

    /**
     * Sets the volume for background music.
     *
     * @param volume the volume level to set (0.0 to 1.0)
     */
    public static void setMusicVolume(double volume) {
        musicVolume = volume;
        currentMusic.setVolume(volume);
    }

    /**
     * Gets the current volume level for sound effects.
     *
     * @return the current volume level for sound effects
     */
    public static double getSoundVolume() {
        return soundVolume;
    }

    /**
     * Gets the current volume level for background music.
     *
     * @return the current volume level for background music
     */
    public static double getMusicVolume() {
        return musicVolume;
    }
}
