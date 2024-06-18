package control;

import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoundTest {

    @BeforeEach
    void setUp() {
        Sound.currentMusic = null;
        Sound.musicOn = true;
        Sound.soundOn = true;
        Sound.musicVolume = 0.5;
        Sound.soundVolume = 0.5;
    }

    @Test
    void testPlaySound_withSoundOff() {
        Sound.soundOn = false;
        Sound.playSound("test-sound.mp3");
        // No exception should be thrown
    }


    @Test
    void testStopMusic() {
        MediaPlayer mockMediaPlayer = mock(MediaPlayer.class);
        Sound.currentMusic = mockMediaPlayer;

        Sound.stopMusic();

        verify(mockMediaPlayer).stop();
    }

    @Test
    void testMusicSwitch() {
        MediaPlayer mockMediaPlayer = mock(MediaPlayer.class);
        Sound.currentMusic = mockMediaPlayer;

        Sound.musicSwitch();

        assertFalse(Sound.musicOn);
        verify(mockMediaPlayer).stop();
    }

    @Test
    void testSetSoundVolume() {
        Sound.setSoundVolume(0.75);
        assertEquals(0.75, Sound.getSoundVolume());
    }

    @Test
    void testSetMusicVolume() {
        MediaPlayer mockMediaPlayer = mock(MediaPlayer.class);
        Sound.currentMusic = mockMediaPlayer;

        Sound.setMusicVolume(0.75);

        assertEquals(0.75, Sound.getMusicVolume());
        verify(mockMediaPlayer).setVolume(0.75);
    }

    @Test
    void testGetSoundVolume() {
        assertEquals(0.5, Sound.getSoundVolume());
    }

    @Test
    void testGetMusicVolume() {
        assertEquals(0.5, Sound.getMusicVolume());
    }
}
