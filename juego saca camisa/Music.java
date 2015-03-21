import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Music
{
    private static Clip sonido;
    public Music()throws UnsupportedAudioFileException, LineUnavailableException, IOException
        {
            sonido = AudioSystem.getClip();

    
        }
    public static void bienvenido()throws UnsupportedAudioFileException, LineUnavailableException, IOException
        {
      sonido = AudioSystem.getClip();
      File file = new File(".\\music\\bienvenido.wav");
      sonido.open(AudioSystem.getAudioInputStream(file));
      sonido.loop(0); 
        }
    public static void musicFondo()throws UnsupportedAudioFileException, LineUnavailableException, IOException
        {
            sonido = AudioSystem.getClip();
            File file = new File(".\\music\\fondo.wav");
            sonido.open(AudioSystem.getAudioInputStream(file));
            sonido.loop(0);
        }
    public static void pullCards()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
      sonido = AudioSystem.getClip();
      File file = new File(".\\music\\pascart.wav");
      sonido.open(AudioSystem.getAudioInputStream(file));
      sonido.loop(0); 
    }

    public static void startPlay()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
      sonido = AudioSystem.getClip();
      File file = new File(".\\music\\jueginc.wav");
      sonido.open(AudioSystem.getAudioInputStream(file));
      sonido.loop(0);


    }
    public static void startGame()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
      sonido = AudioSystem.getClip();
      File file = new File(".\\music\\stargame.wav");
      sonido.open(AudioSystem.getAudioInputStream(file));
      sonido.loop(0);
    }
    public static void finGame()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
      sonido = AudioSystem.getClip();
      File file = new File(".\\music\\final.wav");
      sonido.open(AudioSystem.getAudioInputStream(file));
      sonido.loop(0);
    }

    public static void stop()
    {
        sonido.stop(); 
    }
}