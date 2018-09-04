package numbertospeech.service;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** 
 * This class is created for easily accessing files with branch of methods.
 * 
 * @author Riyad
 */


public class FileLuncher {
    
    public String recordingPath;
    public long Gotmili = 0; 
    //public final String directoryPath = "C:/VoiceReminderFiles/";
    private AudioInputStream audioInputStream; 
    private Clip clip; 
    private File playedFile; 
    
    public FileLuncher(){
    }

    /**
     * To play a sound.
     * @param filePath the full file name with format.Its case sensitive
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public void playSound(String filePath) throws IOException, URISyntaxException{
        try {
            
            URL fileUrl = File.class.getResource(filePath);
            playedFile = new File(fileUrl.toURI());
            audioInputStream = AudioSystem.getAudioInputStream(playedFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            this.Gotmili= clip.getMicrosecondLength()/100000; //Saving the length of millis of the (file/100) for progress bar
            clip.start();
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     *To stop a played sound
     */
    public void stopSound(){
        playedFile = null;
        clip.stop();
        clip.close();
        try {
            audioInputStream.close(); 
        } catch (IOException ex) {
            Logger.getLogger(FileLuncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        clip = null;
        audioInputStream = null;
    }
}
