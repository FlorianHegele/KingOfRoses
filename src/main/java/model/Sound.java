package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Sound {

    static MediaPlayer currentMusic;

    public static void playSound(String sound){
        final Media media = new Media(Paths.get(sound).toUri().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void playMusic(String sound){
        final Media media = new Media(Paths.get(sound).toUri().toString());
        if(currentMusic != null){
            if(currentMusic.getMedia().getSource().contains(sound)) return;
            currentMusic.stop();
        }
        currentMusic = new MediaPlayer(media);
        currentMusic.play();
    }

}
