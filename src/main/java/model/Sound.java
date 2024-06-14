package model;

import boardifier.control.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;


public class Sound {

    static MediaPlayer currentMusic;

    public static void playSound(String sound){
        try {
            final Media media = new Media(Paths.get(sound).toUri().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            Logger.trace("Playing sound: " + sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void playSound(String sound, double volume){
        try {
            final Media media = new Media(Paths.get(sound).toUri().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            Logger.trace("Playing sound: " + sound);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void playMusic(String sound){
        try {
            String mediaPath = Paths.get(sound).toUri().toString();
            Logger.trace("Playing music from: " + mediaPath);
            final Media media = new Media(mediaPath);
            if(currentMusic != null){
                if(currentMusic.getMedia().getSource().contains(sound)) return;
                currentMusic.stop();
            }
            currentMusic = new MediaPlayer(media);
            currentMusic.setOnEndOfMedia(() -> {
                currentMusic.seek(Duration.millis(0));
                currentMusic.play();
            });
            currentMusic.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    public static void playMusic(String sound, long ms){
        try {
            String mediaPath = Paths.get(sound).toUri().toString();
            System.out.println("Playing music from: " + mediaPath);
            final Media media = new Media(mediaPath);
            if(currentMusic != null){
                if(currentMusic.getMedia().getSource().contains(sound)) return;
                currentMusic.stop();
            }
            currentMusic = new MediaPlayer(media);
            currentMusic.setOnEndOfMedia(() -> {
                currentMusic.seek(Duration.millis(ms));
                currentMusic.play();
            });
            currentMusic.setStartTime(Duration.millis(ms));
            currentMusic.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

}
