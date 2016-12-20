import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class MusicPlayer extends Thread {
  private AudioStream as;
  private AudioPlayer p;
  private boolean playback;
  
  public void run() {
    startPlayback("NA");
  }
  
  public void startPlayback(String q) {
    playback = true;
    try {
    as = new AudioStream(new FileInputStream(q+".wav"));
    } catch (Exception e) {System.out.println("errors");}
      p.player.start(as);
    try {
      do {
      } while (as.available() > 0 && playback);
      if (playback) {
        startPlayback(q);
      }
    } catch (IOException ex) {
      Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  public void stopPlayback() {
    playback = false;
    p.player.stop(as);
  }
  
  private File[] getTracks() {
    File dir = new File(System.getProperty("user.dir") + "\\music");
    File[] a = dir.listFiles();
    ArrayList<File> list = new ArrayList<File>();
    for (File f : a) {
      if (f.getName().substring(f.getName().length() - 3, f.getName().length()).equals("wav")) {
        list.add(f);
      }
    }
    File[] ret = new File[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ret[i] = list.get(i);
    }
    return ret;
  }
  
  public static void main(String[] args) {
    MusicPlayer m = new MusicPlayer();
    m.startPlayback("Travel");
  }
}