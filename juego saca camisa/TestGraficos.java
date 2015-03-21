import java.awt.Frame;
import java.awt.Color;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 import javax.swing.*; 

    /**
 * Write a description of class TestGraficos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestGraficos
{

   public static void main( String args[] )throws UnsupportedAudioFileException, LineUnavailableException, IOException
   {
    GraphicsConsole gc;

    gc = GraphicsConsole.getInstance();
    GestorGraficos g=new GestorGraficos();
   
    g.repartirMazo(true);
    //gc.setExtendedState( Frame.MAXIMIZED_BOTH );
    //gc.lockMaxScream();
   // gc.setSize(323,422);
  // System.out.print(+gc.getWidth()+"X"+gc.getHeight());
//gc.drawImage( ".\\imagenes\\fondo.jpg",0,0,gc.getWidth(),gc.getHeight());
   // g.clearSpace(true);
    Music.pullCards();
    String mensaje="jugador";
    g.mostrarMensaje( mensaje );
    g.contarCartas(8,9,8);
    g.ganador(false);
    g.ganador(true);
    g.mostrarMensaje( mensaje );
    //gc.drawImage( ".\\imagenes\\carta.jpg",gc.getWidth()/3-50, gc.getHeight()/3);
    //gc.drawGif(".\\imagenes\\gifs.gif");
    }
    
}

    

