package control;

import boardifier.control.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;


public class Sound {

    static MediaPlayer currentMusic;
    static boolean musicOn = true;
    static boolean soundOn = true;
    static double musicVolume = 0.5;
    static double soundVolume = 0.5;

    public static void playSound(String sound){
        if(soundOn) {
            try {
                final Media media = new Media(Paths.get(sound).toUri().toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                Logger.trace("Playing sound: " + sound);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }
    }

    public static void playMusic(String sound){
            try {
                String mediaPath = Paths.get(sound).toUri().toString();
                Logger.trace("Playing music from: " + mediaPath);
                final Media media = new Media(mediaPath);
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

    public static void playMusic(String sound, long ms){
            try {
                String mediaPath = Paths.get(sound).toUri().toString();
                System.out.println("Playing music from: " + mediaPath);
                final Media media = new Media(mediaPath);
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

    public static void musicSwitch(){
        if(musicOn){
            try {
                final Media media = new Media(Paths.get("src/main/resources/p1.wav").toUri().toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
            currentMusic.stop();
        }
        if(!musicOn){
            try {
                final Media media = new Media(Paths.get("src/main/resources/p2.wav").toUri().toString());
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

    public static void soundSwitch(){
        if(soundOn) {
            try {
                final Media media = new Media(Paths.get("src/main/resources/p10.wav").toUri().toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        } else {
            try {
                final Media media = new Media(Paths.get("src/main/resources/p9.wav").toUri().toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }
        soundOn = !soundOn;
    }

    public static void stopMusic(){
        currentMusic.stop();
    }

    public static void setSoundVolume(double volume){
        soundVolume = volume;
    }

    public static void setMusicVolume(double volume){
        musicVolume = volume;
        currentMusic.setVolume(volume);
    }

    public static double getSoundVolume(){
        return soundVolume;
    }

    public static double getMusicVolume(){
        return musicVolume;
    }


}
