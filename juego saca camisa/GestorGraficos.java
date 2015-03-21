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
public class GestorGraficos extends GestorGraficon
{
    private GraphicsConsole gc;
    
    /**
     * El ancho tomado como estándar en una carta.
     */
    public static final int ANCHO = 146;
    
    /**
     * El alto tomado como estándar en una carta.
     */
    public static final int ALTO = 216;
    
    // las coordenadas iniciales de visualizacion de las cartas en mesa...
    private int x, y;
    
    /**
     * Crea un objeto GestorGraficos.
     */
    public GestorGraficos()
    {
       gc = GraphicsConsole.getInstance();
       gc.lockMaxScream();
       if( ! gc.isVisible() ) gc.setVisible( true );
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {
        x = gc.getWidth()/3+125;
        y = gc.getHeight()/3-50;
        }
       else
        {  
        x = gc.getWidth()/3+100;
        y = gc.getHeight()/3;
        }
       gc.drawImage( ".\\imagenes\\fondo.jpg",0,0,gc.getWidth(),gc.getHeight()-90);
       repartirMazo(true);
    }
    private void repartirMazo(boolean deley)
        {
            if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
            repartirMazon(true);
            else
            {
                int d=0;//gc.getWidth()/3+800,
                int ct1=1,ct2=1;
                int c=0;//gc.getWidth()/3+800
                while(ct1<=5)
                    {
                    gc.drawImage( ".\\imagenes\\carta.jpg",(gc.getWidth()/3-500)+d, gc.getHeight()/3);
                    if(deley)
                     delay(100);
                    d+=15;
                    ct1++;
                    }
                while(ct2<=5)
                    {
                        gc.drawImage( ".\\imagenes\\carta.jpg",(gc.getWidth()/3+900)+c, gc.getHeight()/3);
                        if(deley)
                        delay(100);
                        c+=15;
                        ct2++;
                    }
            }  
    }
    /**
     * dibuja una carta, comenzando en el punto de coordenadas (col, fil).
     * @param c la carta a dibujar.
     * @param col la coordenada de columna izquierda del rectángulo de la carta.
     * @param fil la coordenada de fila superior de la carta.
     */
    public void dibujar( Carta c )throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
         if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
         {dibujarn(c);}
         else
         {
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
                     dibujarEspada( col, fil );
                     break;
            
            case Baraja.ORO:
                     dibujarOro( col, fil );
                     break;
                     
            case Baraja.BASTO:
                     dibujarBasto( col, fil );
                     break;
            
            case Baraja.COPA:
                     dibujarCopa( col, fil );
                     break;
        }     
        Music.pullCards();
        // ajustar el valor de la columna de visualizacion de la proxima carta...
        // ... la fila se mantiene igual...
        //x += GestorGraficos.ANCHO + 5;
        x += 23;//10
        
        // pausa antes de seguir...
        gc.delay( 1000 );
      }
    }
    /**
     * Dibuja una espada, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    public void dibujarEspada( int col, int fil )
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {dibujarEspadan(col,fil);}
        else
        gc.drawImage( ".\\imagenes\\Espada.jpg", col + 38, fil + 54 );
    }
    /**
     * Dibuja un oro, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    public void dibujarOro( int col, int fil )
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {dibujarOron(col,fil);}
        else
        gc.drawImage( ".\\imagenes\\Oro.jpg", col + 38, fil + 54 ); 
    }
    /**
     * Dibuja un basto, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    public void dibujarBasto( int col, int fil )
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {dibujarBaston(col,fil);}
        else
        gc.drawImage( ".\\imagenes\\Basto.jpg", col + 38, fil + 54 );
    }
    /**
     * Dibuja una copa, comenzando en el punto de coordenadas (col, fil).
     * @param col la coordenada de columna izquierda del rectángulo del dibujo.
     * @param fil la coordenada de fila superior del rectángulo del dibujo.
     */
    public void dibujarCopa( int col, int fil )
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {dibujarCopan(col,fil);}
        else
        gc.drawImage( ".\\imagenes\\Copa.jpg", col + 38, fil + 54 );
    }
    /**
     * Muestra un mensaje general indicando quien juega en ese turno.
     * @param mensaje el mensaje a mostrar (normalmente, el juagador que juega)
     */
    public void mostrarMensaje( String mensaje )
    {
         if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
         {mostrarMensajen(mensaje);}
         else
         {
        int htext = 40;
        int col =(gc.getWidth()/3)+200,fil = ((gc.getHeight()/3)-80) - htext;
        gc.setColor(Color.white);
        gc.drawImage(".\\imagenes\\clear.jpg", col - 5, fil - 15);
        Font f=new Font("TimesRoman",Font.BOLD,24);
        gc.setFont(f);
        gc.setColor(Color.RED);
        gc.drawString(mensaje, col+10, fil+10);
        gc.setColor(Color.white);       
        if(mensaje.equals("Juega Computadora"))
         {
            gc.drawImage(".\\imagenes\\perfilpcj.jpg",gc.getWidth()/3+600, gc.getHeight()/3);
            gc.drawImage(".\\imagenes\\humanonj.jpg",gc.getWidth()/3-200, gc.getHeight()/3);
         }
         else
         { 
            gc.drawImage(".\\imagenes\\perfilpcnj.jpg",gc.getWidth()/3+600, gc.getHeight()/3);
            gc.drawImage(".\\imagenes\\humanoj.jpg",gc.getWidth()/3-200, gc.getHeight()/3);
         }
      }
    }
    public void ocultTable()
    {
        gc.setVisible( false );
    }
    public void maximPantalla()
    {gc.lockMaxScream();}
    public void limpiarMesa()
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {limpiarMesan();}
        else
        {
            gc.drawImage( ".\\imagenes\\fondo.jpg",0,0,gc.getWidth(),gc.getHeight()-90);
            repartirMazo(false);
            x = gc.getWidth()/3+100;
            y = gc.getHeight()/3;
        }
    }
    public void fondo()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
        {fondon();}
        else
        {
        //gc.setSize(1366,768);
        gc.setColor(Color.BLACK);
        gc.fillRect(0,0,gc.getWidth(),gc.getHeight());
        
        gc.drawImage( ".\\imagenes\\intro0.jpg", 300, 0);
        Music.startPlay();
        gc.delay( 1500 );
        gc.drawImage(".\\imagenes\\intro1.jpg",683,20);
        gc.delay( 2000 );
        gc.drawImage(".\\imagenes\\intro2.jpg",683,650);
       }  
     }
    public void clearSpace(boolean space)
     {
         if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
         {clearSpacn(space);}
         else
         {
         if(space)
        gc.drawImage( ".\\imagenes\\clearleft.jpg", 0, 0,423,gc.getHeight()-90);
        else
        gc.drawImage( ".\\imagenes\\clearright.jpg",gc.getWidth()-423,0,423,gc.getHeight()-90);
        }
        }
      public void contarCartas(int j,int op,int me)
      {   
          if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
            {contarCartasn(j,op,me);}
            else
          {
            Font f=new Font("TimesRoman",Font.BOLD,18);
            gc.setFont(f);
            gc.setColor(Color.YELLOW);
            gc.fillRect(gc.getWidth()/3-425,gc.getHeight()/3+250,60,40);
            gc.setColor(Color.BLACK);
            gc.drawString(Integer.toString(j),gc.getWidth()/3-405, gc.getHeight()/3+275);
            gc.setColor(Color.YELLOW);
            gc.fillRect(gc.getWidth()/3+250,gc.getHeight()/3+250,60,40);
            gc.setColor(Color.BLACK);
            gc.drawString(Integer.toString(me),gc.getWidth()/3+270,  gc.getHeight()/3+275);
            gc.setColor(Color.YELLOW);
            gc.fillRect(gc.getWidth()/3+975,gc.getHeight()/3+250,60,40);
            gc.setColor(Color.BLACK);
            gc.drawString(Integer.toString(op),gc.getWidth()/3+995, gc.getHeight()/3+275);
          }
    }
    public void ganador(boolean ganador)
     {
         if(( anchoPantalla()>=1360&&anchoPantalla ()<1920)&&(alturaPantalla()>=768 &&alturaPantalla()<1080))
         {ganadorn(ganador);}
         else
         {
            if(ganador)
            gc.drawImage( ".\\imagenes\\ganador.jpg", gc.getWidth()/3-145, gc.getHeight()/3+345);
            else
            gc.drawImage( ".\\imagenes\\ganador.jpg",gc.getWidth()/3+650, gc.getHeight()/3+345);
         }
     }
    public void delay(int milseg)
    {
        gc.delay( milseg );
    }
    public void gif()
    { 
        gc.setSize(160,112);
        gc.drawGif(".\\imagenes\\anim.gif");
    }
    public int anchoPantalla()
    {
        return gc.getWidth();
    }
    public int alturaPantalla()
    {
        return gc.getHeight();
    }
      
}
