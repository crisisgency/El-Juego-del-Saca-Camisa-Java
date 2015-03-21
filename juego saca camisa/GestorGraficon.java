import java.awt.Frame;
import java.awt.Color;
import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Clase encargada de realizar los gráficos de las cartas en la consola 
 * de gráficos.
 * 
 * @author Ing. Valerio Frittelli. 
 * @version Mayo de 2012.
 */
public class GestorGraficon
{
    private GraphicsConsole gc;
    
    /**
     * El ancho tomado como estándar en una carta.
     */
    protected static final int ANCHO = 80;//146
    
    /**
     * El alto tomado como estándar en una carta.
     */
    protected static final int ALTO = 129;//216
    
    // las coordenadas iniciales de visualizacion de las cartas en mesa...
    protected int x, y;
    protected int cont=0;
    /**
     * Crea un objeto GestorGraficos.
     */
    protected GestorGraficon()
    {
        gc = GraphicsConsole.getInstance();
        gc.lockMaxScream();
        if( ! gc.isVisible() ) gc.setVisible( true );
        x = gc.getWidth()/3+125;
        y = gc.getHeight()/3-50;
        gc.drawImage( ".\\imagenesn\\fondo.jpg",0,0,gc.getWidth(),gc.getHeight()-90);
    }
    protected void repartirMazon(boolean deley)
    {
      int d=0,c=0,ct1=1,ct2=1;
      while(ct1<=5)
      {  
      gc.drawImage( ".\\imagenesn\\carta.jpg",(gc.getWidth()/3-400)+d, gc.getHeight()/3);
      if(deley)
      delayn(100);
      d+=15;
      ct1++;
      }
      while(ct2<=5){
      gc.drawImage( ".\\imagenesn\\carta.jpg",(gc.getWidth()/3+650)+c, gc.getHeight()/3);
      if(deley)
      delayn(100);
      c+=15;
      ct2++;
      }
    }
    /**
     * dibuja una carta, comenzando en el punto de coordenadas (col, fil).
     * @param c la carta a dibujar.
     * @param col la coordenada de columna izquierda del rectángulo de la carta.
     * @param fil la coordenada de fila superior de la carta.
     */
    protected void dibujarn( Carta c )throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
       if(cont==7)
        {
         y+=132;//gc.getHeight()/3
         x= gc.getWidth()/3+125;
         cont=0;
        }
        cont++;
        // desplegar la carta c en la consola de graficos...
        int col = x, fil = y;  
        
        // primero, limpiar el area de dibujo...
        gc.setColor(Color.white);
        gc.fillRect(col, fil, ANCHO, ALTO);
        
        // dibujar el borde de la carta...
        gc.setColor( Color.black );
        gc.drawRect( col, fil, ANCHO, ALTO );
        
        // el valor de la carta...
        gc.drawString( Integer.toString( c.getValor() ), col + 5, fil + 15 );
        gc.drawString( Integer.toString( c.getValor() ), col + ANCHO - 18, fil + ALTO - 5 );
        
        // el palo de la carta...
       switch( c.getPalo() )
        {
            case Baraja.ESPADA:  
                     dibujarEspadan( col, fil );
                     break;
            
            case Baraja.ORO:
                     dibujarOron( col, fil );
                     break;
                     
            case Baraja.BASTO:
                     dibujarBaston( col, fil );
                     break;
            
            case Baraja.COPA:
                     dibujarCopan( col, fil );
                     break;
        }     
        Music.pullCards();
        // ajustar el valor de la columna de visualizacion de la proxima carta...
        // ... la fila se mantiene igual...
        //x += GestorGraficos.ANCHO + 5;
        x += 23;
        
        // pausa antes de seguir...
       gc.delay( 1000 );
    }
    /**
     * Dibuja una espada, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    protected void dibujarEspadan( int col, int fil )
    {
        gc.drawImage( ".\\imagenesn\\Espada.jpg", col + 25, fil + 16 );
    }
    /**
     * Dibuja un oro, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    protected void dibujarOron( int col, int fil )
    {
        gc.drawImage( ".\\imagenesn\\Oro.jpg", col + 4, fil + 28 ); 
    }
    /**
     * Dibuja un basto, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    protected void dibujarBaston( int col, int fil )
    {
        gc.drawImage( ".\\imagenesn\\Basto.jpg", col + 23, fil + 16 );
    }
    /**
     * Dibuja una copa, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    protected void dibujarCopan( int col, int fil )
    {
        gc.drawImage( ".\\imagenesn\\Copa.jpg", col + 10, fil + 19 );
    }
    /**
     * Muestra un mensaje general indicando quien juega en ese turno.
     * @param mensaje el mensaje a mostrar (normalmente, el juagador que juega)
     */
    protected void mostrarMensajen( String mensaje )
    {
        int htext = 40;
        //int col = 1000, fil = 500 - htext;
        int col =(gc.getWidth()/3)+100,fil = ((gc.getHeight()/3)-120) - htext;
        gc.setColor(Color.white);
        gc.drawImage(".\\imagenesn\\clear.jpg", col - 100, fil -35);
        Font f=new Font("TimesRoman",Font.BOLD,18);
        gc.setFont(f);
        //gc.clearRect(col - 5, fil - 10, 400, 40);
        gc.setColor(Color.RED);
        gc.drawString(mensaje, col, fil);
        gc.setColor(Color.white);       
        if(mensaje.equals("Juega Computadora"))
         {gc.drawImage(".\\imagenesn\\perfilpcj.jpg",gc.getWidth()/3+350, gc.getHeight()/3-100);
          gc.drawImage(".\\imagenesn\\humanonj.jpg",gc.getWidth()/3-180, gc.getHeight()/3-100);
             
            }
         else
        { gc.drawImage(".\\imagenesn\\perfilpcnj.jpg",gc.getWidth()/3+350, gc.getHeight()/3-100);
         gc.drawImage(".\\imagenesn\\humanoj.jpg",gc.getWidth()/3-180, gc.getHeight()/3-100);}
    }
    protected void ocultTablen()
    {
        gc.setVisible( false );
    }
    protected void maximPantallan()
    {gc.lockMaxScream();}
    protected void limpiarMesan()
    {
        gc.drawImage( ".\\imagenesn\\fondo.jpg",0,0,gc.getWidth(),gc.getHeight()-90);
        repartirMazon(false);
        x = gc.getWidth()/3+125;
        y = gc.getHeight()/3-50;
        cont=0;
    }
    protected void fondon()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        //gc.setSize(1366,768);
        gc.setColor(Color.BLACK);
        gc.fillRect(0,0,gc.getWidth(),gc.getHeight());
        
        gc.drawImage( ".\\imagenesn\\intro0.jpg",gc.getWidth()/9,gc.getHeight()/9,1066,468);
        Music.startPlay();
        gc.delay( 1500 );
        gc.drawImage(".\\imagenesn\\intro1.jpg",400,20);
        gc.delay( 2000 );
        gc.drawImage(".\\imagenesn\\intro2.jpg",400,500);
        
        
     }
    protected void clearSpacn(boolean space)
     {    if(space)
         {gc.drawImage(".\\imagenesn\\clearleft.jpg",0,0,261,gc.getHeight()-90);}
         else
         {gc.drawImage( ".\\imagenesn\\clearright.jpg",gc.getWidth()-261,0,261,gc.getHeight()-90);}
     }
    public void contarCartasn(int j,int op,int me)
    {   
          Font f=new Font("TimesRoman",Font.BOLD,18);
          gc.setFont(f);
          gc.setColor(Color.YELLOW);
          gc.fillRect(gc.getWidth()/3-325,gc.getHeight()/3+250,60,40);
          gc.setColor(Color.BLACK);
          gc.drawString(Integer.toString(j),gc.getWidth()/3-305, gc.getHeight()/3+275);
          gc.setColor(Color.YELLOW);
          gc.fillRect(gc.getWidth()/3+170,gc.getHeight()/3-100,60,40);
          gc.setColor(Color.BLACK);
          gc.drawString(Integer.toString(me),gc.getWidth()/3+190,gc.getHeight()/3-75);
          gc.setColor(Color.YELLOW);
          gc.fillRect(gc.getWidth()/3+700,gc.getHeight()/3+250,60,40);
          gc.setColor(Color.BLACK);
          gc.drawString(Integer.toString(op),gc.getWidth()/3+720, gc.getHeight()/3+275);
        
    }
    protected void ganadorn(boolean ganador)
     {
         if(ganador)
         gc.drawImage( ".\\imagenes\\ganador.jpg", gc.getWidth()/3-120, gc.getHeight()/3+245);
         else
         gc.drawImage( ".\\imagenes\\ganador.jpg",gc.getWidth()/3+390, gc.getHeight()/3+245);
        
     }
    protected void delayn(int milseg)
      {
        gc.delay( milseg );
      }
    protected void gifn()
        { 
        gc.setSize(160,112);
        gc.drawGif(".\\imagenesn\\anim.gif");
        }
}


