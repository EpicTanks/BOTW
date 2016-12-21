import java.io.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusicPlayer extends Thread {
 private AudioStream as;
 private boolean playback;
 private boolean change;
 private String nextSong;

 public void startPlayback(String q) {
  playback = true;
  AudioPlayer.player.stop(as);
  try {
   as = new AudioStream(new FileInputStream(q + ".wav"));
  } catch (Exception e) {
   System.out.println("errors");
  }
  AudioPlayer.player.start(as);
  try {
   do {
   } while (as.available() > 0 && playback);
   if (playback || change) {
    change = false;
    startPlayback(nextSong);
   }
  } catch (IOException e) {
   e.printStackTrace();
  }

 }

 public void stopPlayback() {
  playback = false;
  AudioPlayer.player.stop(as);
 }
 
 public void changeTrack(String q) {
  playback = false;
  change = true;
  nextSong = q;
 }

 public void start() {
  startPlayback("Title");
  nextSong = "Title";
 }
}