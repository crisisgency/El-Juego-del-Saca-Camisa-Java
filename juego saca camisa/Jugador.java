/**
 * Representa un Jugador para el juego del Saca Camisa.
 * 
 * @author Ing. Valerio Frittelli. 
 * @version Mayo de 2012.
 */
public class Jugador
{   
    // el nombre del jugador...
    private String nombre;
    
    // el mazo de cartas propio del jugador...
    private Baraja monton;
    
    /**
     * Crea un Jugador con nombre "No disponible" y un mazo vacio...
     */
    public Jugador()
    {
        this( "No disponible", null );
    }
    
    /**
     * Crea un jugador nombre igual a nom, y mazo igual mz. Si el parametro nom es
     * null, el nombre sera "No disponible". Si el parametro mz es null, el mazo 
     * se crea vacio.
     * @param nom el nombre del jugador.
     * @param mz el mazo del jugador.
     */
    public Jugador( String nom, Baraja mz )
    {        
        if( nom == null ) nom = "No disponible";
        nombre = nom;       
        
        if( mz == null ) monton = new Baraja(Baraja.COMPLETA, true, true);
        monton = mz;
    }
    
    /**
     * Accede al nombre del Jugador.
     * @return el nombre del Jugador.
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Cambia el nombre del Jugador
     */
    public void setNombre(String nom)
    {
        if(nom == null) return;
        nombre = nom;
    }
        
    /**
     * Retorna el mazo del jugador.
     * @return el mazo del jugador.
     */
    public Baraja getMazo()
    {
        return monton;
    }
    
    /**
     * Cambia mazo del jugador. Si el nuevo mazo es null, entonces el 
     * jugador mantiene su mazo anterior.
     * @param mz el nuevo mazo.
     */
    public void setMazo( Baraja mz )
    {
        if( mz == null ) return;
        monton = mz;
    }
    
    /**
     * Simula el proceso de entregar una carta, desde la parte superior del mazo 
     * del jugador. La carta entregada se retorna. El metodo retorna null si el 
     * mazo del jugador estaba vacio.  
     * @return la carta entregada.
     */
    public Carta entregar()
    {
        return monton.pedir();       
    }
    public int cantCart()
    {
       return monton.cantidad();
    
    }
}
