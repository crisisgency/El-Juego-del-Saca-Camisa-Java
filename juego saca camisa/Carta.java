/**
 * Una clase para representar una carta de la baraja española, que 
 * pueda ser usada para implementar el juego del 7 y medio (u otro 
 * que sirva a esta baraja). El palo de cada carta se representa con
 * un valor numérico entre 0 y 3 mediante constantes declaradas en la
 * clase Baraja. Esas constantes públicas son:
 *     public static final int ESPADA = 0;
 *     public static final int BASTO  = 1;
 *     public static final int ORO    = 2;
 *     public static final int COPA   = 3;
 * 
 * @author Ing. Valerio Frittelli.
 * @version Mayo de 2011. 
 */
public class Carta
{
    // el número que corresponde a la carta
    private int valor;
    
    // el palo que corresponde a la carta (constantes clase Baraja...)
    private int palo;
    
    /**
     * Inicializa una carta con valor = 1 (el as...) y palo = ESPADA (...de espadas)
     */
    public Carta()
    {
        valor = 1;
        palo = Baraja.ESPADA;
    }
    
    /**
     * Inicializa una carta con valores tomados como parámetro. Si el parámetro v es incorrecto,
     * entonces el valor de la carta quedará en 1 (el as...) Y si el parámetro p es incorrecto, 
     * entonces el palo quedará valiendo ESPADA (...de espada).
     * @param v el valor a asignar para la carta creada.
     * @param p el palo a asignar para la carta creada.
     */
    public Carta( int v, int p )
    {
        if( v < 1 || v > 12 ) v = 1;
        valor = v;
        
        if( p != Baraja.ESPADA && p != Baraja.BASTO && p != Baraja.ORO && p != Baraja.COPA ) p = Baraja.ESPADA;
        palo = p;
    }
    
    /**
     * Retorna el valor de la carta.
     * @return el valor de la carta.
     */
    public int getValor()
    {
        return valor;
    }
    
    /**
     * Retorna el palo de la carta (que corresponde a una de las constantes de palo declaradas
     * la clase Baraja).
     * @return el palo de la carta.
     */
    public int getPalo()
    {
        return palo;
    }
    
    /**
     * Cambia el valor de la carta. Si nuevo valor es incorrecto, se dejará sin cambios el anterior. 
     * @param v el nuevo valor de la carta.
     */
    public void setValor( int v )
    {
        if( v < 1 || v > 12 ) return;
        valor = v;
    }
    
    /**
     * Cambia el palo de la carta. Si nuevo palo es incorrecto, se dejará sin cambios el anterior. 
     * @param p el nuevo palo de la carta.
     */
    public void setPalo( int p )
    {
        if( p != Baraja.ESPADA && p != Baraja.BASTO && p != Baraja.ORO && p != Baraja.COPA ) return;
        palo = p;
    }
    
    @Override
    public String toString()
    { 
        String p = "Espada";
        if( palo == Baraja.BASTO ) p = "Basto";
        if( palo == Baraja.ORO ) p = "Oro";
        if( palo == Baraja.COPA ) p = "Copa";
        
        return "{" + valor + " de " + p + "}";
    }
}
