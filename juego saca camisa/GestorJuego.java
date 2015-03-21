/**
 * Los objetos de esta clase permiten manejar una partida de Saca Camisa.
 * Cada objeto de esta clase, implicitamente representa tambien a uno de
 * los jugadores (concretamente, el que sera manejado por la computadora).
 * 
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2012.
 */
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class GestorJuego
{
    // la baraja con todas las cartas... que se reparte entre los jugadores...
    private Baraja baraja;
    
    // las cartas que estan sobre la mesa...
    private Baraja mesa;
    
    // el jugador humano...
    private Jugador humano;
    
    // el jugador virtual...
    private Jugador computadora;
    
    // el gestor de graficos general del juego...
    private GestorGraficos g;
           
    /**
     * Crea un objeto, con baraja completa y mezclada. Los nombres de los jugadores 
     * serán "Humano" y "Computadora". Abre la consola de graficos.
     */
    public GestorJuego()
    {
        this( "Humano", "Computadora" );
    }
    
    /**
     * Crea un objeto, con baraja completa y mezclada. Los nombres de los jugadores
     * serán los informados en los parametros nomh y nomc. Los jugadores arrancan 
     * cada uno con la mitad de la baraja. Abre la consola de graficos.
     * @param nomh el nombre del jugador humano.
     * @param nomc el nombre del jugador virtual.
     */
    public GestorJuego( String nomh, String nomc )
    {
        baraja = new Baraja( Baraja.COMPLETA, true );
                
        if( nomh == null ) nomh = "Humano";
        humano = new Jugador(nomh, baraja.repartir());
        
        if( nomc == null ) nomc = "Computadora";
        computadora = new Jugador(nomc, baraja);
        
        mesa = new Baraja(Baraja.COMPLETA, true, true);
        
        g = new GestorGraficos();
        g.contarCartas(humano.cantCart(),computadora.cantCart(),mesa.cantidad());
    }
       
    /**
     * Pide una carta al Jugador entrado como parámetro. El metodo tambien 
     * despliega la carta en la consola de graficos. Si el jugador puede 
     * entregar una carta, el metodo esa carta. Si el jugador no puede
     * entregar una carta por estar vacio su mazo, el metodo null.
     * @param j el Jugador al que se le pide la carta.
     * @return la carta entregada por el jugador.
     */
    public Carta pedir( Jugador j )throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        g.contarCartas(humano.cantCart(),computadora.cantCart(),mesa.cantidad()+1);
        Carta c = j.entregar();
        if( c == null ) return null;
        // dibujar la carta en la consola de gráficos...
        g.dibujar( c );
        return c;
    }
    /**
     * Lanza el juego. Simula el proceso completo, mostrando las cartas
     * y cambiando el Jugador segun a quien le toque el turno. El proceso
     * termina cuando uno de los dos jugadores pierde. El metodo retorna 
     * el nombre del jugador que gano el juego.
     */
    public String jugar()throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {       
       Jugador ganador = null;
       //variables locales auxiliares
       int n,i; Baraja x;
       // que Jugador juega en este turno?
       Jugador enturno = humano, oponente = computadora; 
       String mensaje = "Juega " + enturno.getNombre();
     
       do
        {            
            g.mostrarMensaje(mensaje);
            Carta c = pedir(enturno);
            if( c == null )
            { ganador = oponente;
              g.clearSpace(true);
              if(ganador.getNombre().equals("Computadora"))
                       {g.clearSpace(true);
                        g.ganador(false);
                       }
                         else
                       { g.clearSpace(false);
                        g.ganador(true);
                        }
              mensaje = "Juega " + oponente.getNombre();
              g.mostrarMensaje(mensaje);
            }  
            else
            {
                mesa.agregar(c);
                if( c.getValor() > 3 )
                {
                    Jugador aux = enturno;
                    enturno = oponente;
                    oponente = aux;
                    mensaje = "Juega " + enturno.getNombre();
                }
                else
                {
                    n=c.getValor();
                    for(i=1;i<=n&&mesa.vacia()==false;i++)
                    {
                        mensaje = "Juega " + oponente.getNombre();
                        g.mostrarMensaje(mensaje);
                        c=pedir(oponente);
                        mesa.agregar(c);
                        if(c==null)
                           {
                                ganador = enturno;
                                if(ganador.getNombre().equals("Computadora"))
                                {
                                  g.clearSpace(true);
                                  g.ganador(false);
                                }
                                else
                                {
                                  g.clearSpace(false);
                                  g.ganador(true);
                                }
                                mensaje = "Juega " + enturno.getNombre();
                                g.mostrarMensaje(mensaje);  
                            }
                            if(ganador==null)
                            {
                                if(i==n&&c.getValor()>3)
                                {
                                mensaje = "Juega " + enturno.getNombre();
                                g.mostrarMensaje(mensaje);   
                                x=enturno.getMazo();
                                x.juntar(mesa);
                                enturno.setMazo(x);
                                g.limpiarMesa();
                                }
                                else
                                {
                                if(c.getValor()<=3)
                                {
                                 n=c.getValor();
                                 i=0;
                                 Jugador aux = enturno;
                                 enturno = oponente;
                                 oponente = aux;
                                }
                            }
                        }    
                    }
                }
            }
        }
        while( ganador == null );
       Music.finGame();
       g.contarCartas(humano.cantCart(),computadora.cantCart(),mesa.cantidad()-1);
        return ganador.getNombre();
    }
    
    /**
     * Retorna una cadena con los nombres de los jugadores.
     * @return una cadena con los nombres de los jugadores.
     */
    public String toString()
    {
        return "Nombre jugador humano: " + humano.getNombre() + "\tNombre jugador virtual: " + computadora.getNombre();
    }
}
