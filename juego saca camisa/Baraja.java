import java.util.Random;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Clase que representa la Baraja Española para la implementación de 
 * un juego de cartas. En esa baraja, los palos son espada, basto, oro
 * y copa. La baraja completa tiene 12 cartas de cada palo. El 1 de cada 
 * palo se conoce como "as". Las tres últimas cartas de cada palo en la
 * baraja completa, se conocen como "figuras" o "cartas negras", y 
 * son respectivamente el 10 ("la sota"), el 11 ("el caballo") y el 12
 * ("el rey").
 * 
 * La clase provee constantes públicas de tipo numérico (int) para 
 * representar en forma simple a los cuatro palos. Esas constantes públicas 
 * son:
 *     public static final int ESPADA = 0;
 *     public static final int BASTO  = 1;
 *     public static final int ORO    = 2;
 *     public static final int COPA   = 3;
 * 
 * En algunos juegos de cartas, se usa toda la baraja completa, y en otros 
 * se usa la baraja reducida, en la cual se eliminan previamente los ochos 
 * y los nueves. La clase cuenta con dos constantes públicas que representan
 * a la baraja completa y a la reducida. Uno de los constructores de la clase
 * puede tomar un parámetro para configurar la baraja como completa o reducida.
 * 
 * @author Ing. Valerio Frittelli. 
 * @version Mayo de 2012.
 */

public class Baraja
{
    // Constantes públicas para representar los palos 
    /**
     * Constante para representar el palo de Espada.
     */
    public static final int ESPADA = 0;
    
    /**
     * Constante para representar el palo de Basto.
     */
    public static final int BASTO  = 1;
    
    /**
     * Constante para representar el palo de Oro.
     */
    public static final int ORO    = 2;
    
    /**
     * Constante para representar el palo de Copa.
     */
    public static final int COPA   = 3;
    
    // Constantes públicas para indicar si se usan todas las cartas
    /**
     * Constante para representar indicar baraja completa.
     */
    public static final int COMPLETA = 0;  // juegan todas
    
    /**
     * Constante para indicar baraja reducida.
     */
    public static final int REDUCIDA = 1;  // fuera los ochos y los nueves
    
    // el mazo de cartas...
    private LinkedList < Carta > mazo;
    
    // completa, reducida...
    private int tipo;
    
    /**
     * Crea una baraja no vacia, con un mazo inicial completo y ordenado:
     * primero todas las espadas del 1 al 12, luego los bastos, 
     * los oros, y finalmente las copas.
     */
    public Baraja()
    {
        this( COMPLETA, false, false );
    }
    
    /**
     * Crea una baraja no vacia y completa. La misma quedará ordenada a menos 
     * que el parámetro "mezcle" sea true, en cuyo caso el mazo será 
     * mezclado en forma aleatoria.
     * @param mezcle true: el mazo se crea y se mezcla - false: el mazo se crea y queda ordenado.
     */
    public Baraja( boolean mezcle )
    {
        this( COMPLETA, mezcle, false );
    }
    
    /**
     * Crea una baraja no vacia y mezclada. El parámetro t permite seleccionar entre crear una baraja completa 
     * (t = Baraja.COMPLETA), reducida t = Baraja.REDUCIDA). 
     * @param t si vale 0, la baraja se crea completa. Si vale 1, se crea reducida. 
     */
    public Baraja( int t )
    {
        this(t, false, false);
    }
    
    /**
     * Crea una baraja no vacia. La misma quedará ordenada a menos que el parámetro "mezcle" sea true, 
     * en cuyo caso el mazo será mezclado en forma aleatoria. El primer parámetro permite seleccionar
     * entre crear una baraja completa (t = Baraja.COMPLETA) o reducida t = Baraja.REDUCIDA). 
     * @param t si vale 0, la baraja se crea completa. Si vale 1, se crea reducida. Si vale 3, se crea vacia.
     * @param mezcle true: el mazo se crea y se mezcla - false: el mazo se crea y queda ordenado. 
     */
    public Baraja( int t, boolean mezcle )
    {
        this(t, mezcle, false);
    }    
    
    
      /**
     * Crea una baraja. La misma quedará ordenada a menos que el parámetro "mezcle" sea true, 
     * en cuyo caso el mazo será mezclado en forma aleatoria. El primer parámetro permite seleccionar
     * entre crear una baraja completa (t = Baraja.COMPLETA) o reducida t = Baraja.REDUCIDA). El 
     * parametro empty indica si la baraja debe crearse vacia (sin carta alguna: empty = true) o no. 
     * Si la baraja se crea vacia, no contendrá carta alguna y debera llenarse luego, invocando a alguno de los 
     * metodos que la clase provee.
     * @param t si vale 0, la baraja se crea completa. Si vale 1, se crea reducida. 
     * @param mezcle true: el mazo se crea y se mezcla - false: el mazo se crea y queda ordenado. No tiene efecto 
     * si la baraja se crea vacia.
     * @param empty true: la baraja se crea vacia (sin cartas). 
     */
    public Baraja( int t, boolean mezcle, boolean empty)
    {
        if( t != COMPLETA && t != REDUCIDA ) t = COMPLETA;
        tipo = t;
        
        init(empty);
        if( mezcle && !empty) mezclar();
    }
    /**
     * Retorna la cantidad de cartas que quedan en esa Baraja
     */
    public int cantidad()
    {
        return mazo.size();
    }
    /**
     * Agrega una carta a la baraja. El metodo verifica que la carta no este ya en esa
     * Baraja, y no agrega la carta si la misma esta repetida. Retorna true si pudo 
     * agregar la carta, o false en caso contrario. Este metodo deberia usarse en casos
     * en que la Baraja comenzo vacia, y debe ir llenandose poco a poco (por ejemplo, 
     * para representar el conjunto de cartas que se descartan en la mesa...)
     * @param la carta que debe agregarse.
     * @return true si la carta pudo agregarse.
     */
    public boolean agregar(Carta c)
    {
        if(mazo.contains(c)) return false;
        mazo.addFirst(c);
        return true;
    }
   
    /**
     * Junta nuevamente todas las cartas en el mazo, dejándolo desordenado.
     */
    public void juntar()
    {
        init(false);
        mezclar();
    }
    
    /**
     * Junta con esta Baraja las cartas de la Baraja b. La Baraja original
     * (this) sera igual a la union de this con b. Las cartas se agregan al 
     * final de la Baraja original. La Baraja b quedara vacia.
     * @param b la Baraja a unir con la original. 
     */
    public void juntar (Baraja b)
    {
        int n = b.mazo.size();
        for(int i = 0; i < n; i++)
        {
            Carta carta = b.mazo.remove();
            this.mazo.add(carta);
        }
    }
    
    /**
     * Junta nuevamente todas las cartas en el mazo. El mazo quedará ordenado
     * si el parámetro "ordene" es true. De lo contrario, quedará desordenado.
     * @param ordene true: el mazo se junta y se ordena - false: se junta y queda desordenado.
     */
    public void juntar( boolean ordene )
    {
        init(false);
        if( ! ordene ) mezclar();
    }
    
    /**
     * Mezcla el mazo. La mezcla se hace con las cartas que en ese
     * momento queden en el mazo, sin garantía que el mazo esté
     * completo (por haber usado ya algunas cartas).
     */
    public void mezclar()
    {
        Collections.shuffle( mazo, new Random( System.currentTimeMillis() ) );
    }
    
    /**
     * Retira una carta del mazo, y la retorna (a menos que el mazo esté vacío, 
     * en cuyo caso retornará null). La carta retirada es la que en ese momento 
     * está "encima" del mazo (o sea, "la de arriba"...) La carta es retirada del 
     * mazo: esto quiere decir que una nueva invocación al método pedir(), no 
     * podrá retornar nunca una carta que ya haya sido devuelta (a menos que el 
     * objeto se vuelva a crear, o se invoque al método juntar()).
     * @return la primera carta de arriba del mazo.
     */
    public Carta pedir()
    {
        return ( mazo.isEmpty() )? null : mazo.removeFirst(); 
    }
    
    /**
     * Retira una carta del mazo, y la retorna (a menos que el mazo esté vacío, 
     * en cuyo caso retornará null). La carta retirada depende del valor del 
     * parámetro "arriba": si este es true, la carta retornada es la que en ese 
     * momento está "encima" del mazo (o sea, "la de arriba"...) Si el parámetro
     * es false, la carta retornada será la de "abajo" del mazo. La carta es 
     * retirada del mazo: esto quiere decir que una nueva invocación al método 
     * pedir(), no podrá retornar nunca una carta que ya haya sido devuelta (a 
     * menos que el objeto baraja se vuelva a crear, o se invoque al método juntar()).
     * @return la primera (o la última) carta de del mazo.
     * @param arriba true: retorna la carta de arriba - false: retorna la de abajo.
     */
    public Carta pedir( boolean arriba )
    {
        if( mazo.isEmpty() ) return null;
        
        if( ! arriba ) return mazo.removeLast();
        return mazo.removeFirst(); 
    }
    
    /**
     * Reparte la baraja. De la baraja inicial se toma la mitad de las cartas, se crea 
     * con ellas una segunda baraja, y se retorna. Tanto la original como la segunda baraja,
     * estaran ordenadas o desordenadas según haya estado ordenada o desordenada la original.
     * La segunda baraja sera del mismo tipo (COMPLETA, REDUCIDA) que la original. 
     * @return una baraja con la mitad de las cartas de la original (si es que la original
     * tenía cartas para repartir).
     */
    public Baraja repartir()
    {
        // crear una segunda baraja vacia, del mismo tipo que la original...
        Baraja segunda = new Baraja(this.tipo, true, true);
        int n = this.mazo.size() / 2;
        for(int i = 0; i < n; i++)
        {
            Carta carta = this.mazo.remove();
            segunda.mazo.add(carta);
        }
        return segunda;
    }
    
    /**
     * Redefine el metodo heredado desde Object.
     * @return una cadena con la representacion de la baraja.
     */
    public String toString()
    {
        return mazo.toString();   
    }
    
    /**
     * Permite determinar si la baraja esta vacia.
     * @return true: la baraja esta vacia.
     */
    public boolean vacia()
    {
        return mazo.isEmpty();
    }
    
    private void init(boolean empty)
    {
        mazo = new LinkedList < Carta > ();
        if(empty) return;
                
        for( int palo = 0; palo <= 3; palo++ )
        {
            for( int valor = 1; valor <= 12; valor++ ) 
            {
                if( tipo == REDUCIDA && ( valor == 8 || valor == 9 ) ) continue; 
                Carta c = new Carta( valor, palo );
                mazo.addLast( c );
            }
        }
    }
}
