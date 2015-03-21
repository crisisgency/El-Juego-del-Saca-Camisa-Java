import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * La clase GraphicsConsole representa una ventana de trabajo
 * sencilla para que un programa basado en consola estándar 
 * pueda visualizar objetos gráficos elementales como cuadrados, 
 * elipses, círculos, líneas, etc. También puede desplegar cadenas 
 * de caracteres en fuentes diversas, y gestionar el color de los
 * objetos mostrados. A partir de esta versión 1.1, se agrega también
 * la funcionalidad de poder desplegar una imagen que exista previamente
 * en un archivo, y una opción de menú que permite imprimir lo que se haya
 * dibujado, en una sola hoja. La impresión se recomienda con orientación
 * apaisada. Para imprimir en una sola página, la clase toma la gráfica que 
 * se haya generado, y crea una imagen para imprimir a una escala tal que 
 * pueda entrar en el área imprimible de la página, por lo cual es posible
 * que la impresión produzca algún efecto de estiramiento de la imagen con
 * respecto a la que se ve en pantalla.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Junio de 2011 - Versión 1.1.
 */
public class GraphicsConsole extends JFrame
{
    // atributo static: gestionado via Singleton Pattern.
    private static GraphicsConsole gc = new GraphicsConsole();
    
    // el objeto donde se harán los dibujos...
    private Lienzo lienzo;
    
    // un objeto imagen para usar como doble buffer...
    private Image db;
    
    // el contenedor gráfico del doble buffer...
    private Graphics cg;
    
    // las dimensiones de la pantalla al correr el programa...
    private Dimension dim;
    
    //dimencion de pantalla
    // Ancho
    private int width;
    //Alto
    private int height;
    
    //********************************************************
    //************************************ Métodos privados...
    //********************************************************
    
    // constructor privado... Singleton Pattern.
    private GraphicsConsole()
    {
        super( "Juego De Saca Camisa -Mesa Del Juego" );
        this.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );        
        this.setMenu();
        this.setInitialLook();
        this.initGraphicArea();
        this.initButton();        
    }
    
    // crea, activa y controla el menú superior de opciones.
    private void setMenu()
    {
         // activamos la barra del menú superior...
         JMenuBar  barra = new JMenuBar();
         this.setJMenuBar( barra );  
         
         // agregamos opciones horizontales en la barra del menú...
         JMenu file = new JMenu( "File" );
         JMenu help = new JMenu( "Help" );
         barra.add( file );
         barra.add( help );
                  
         // agregamos los items de opciones en cada opción horizontal...
         JMenuItem exit = new JMenuItem ( "Ocultar" );
         JMenuItem about = new JMenuItem ( "About" );
         JMenuItem print = new JMenuItem ( "Print..." );
         file.add( print );
         file.add( exit );
         help.add( about );
         
         // notificaciones de captura de eventos...
         exit.addActionListener( new ActionListener()
                                 { 
                                    public void actionPerformed( ActionEvent e )
                                    {
                                        doExit( );
                                    }
                                 }
         );
         
         about.addActionListener( new ActionListener()
                                  {
                                    public void actionPerformed( ActionEvent e )
                                    {
                                        doHelp();
                                    }
                                  }
         );
         
         print.addActionListener( new ActionListener()
                                  {
                                    public void actionPerformed( ActionEvent e )
                                    {
                                        doPrint();
                                    }
                                  }
         );
         
    }
    
    // configura el tamaño y la posición inicial de la ventana...
    private void setInitialLook()
    {         
         // tomamos la configuración actual de la pantalla...
         Toolkit kit = Toolkit.getDefaultToolkit();
         dim = kit.getScreenSize();
         int alto = dim.height;
         int ancho = dim.width;
        
         // fijamos el ancho, el alto y las coordenadas de arranque... 
         this.setSize ( ancho / 2, alto / 2 );       
         this.setLocation( ancho / 4, alto / 4);
    }
    
    // inicializa el área de dibujo de la ventana, agregando un tapiz blanco...
    private void initGraphicArea()
    {
        int alto = dim.height;
        int ancho = dim.width;
        
        lienzo = new Lienzo();       
        db = new BufferedImage( ancho, alto, BufferedImage.TYPE_INT_ARGB );
        cg = db.getGraphics();
        cg.setColor( Color.white );
        cg.fillRect( 0, 0, ancho, alto );
        Container c = getContentPane();
        c.add( lienzo );        
    }
    
    // agrega y configura el botón de salida de la ventana
    private void initButton()
    {
         JButton out = new JButton( "Ocultar" );
         out.addActionListener( new ActionListener()
                                {
                                   public void actionPerformed( ActionEvent e )
                                   {
                                      doExit();  
                                   }
                                }
         );
         
         JPanel jp = new JPanel();
         jp.add( out );
         
         Container c = this.getContentPane();
         c.add( BorderLayout.SOUTH, jp );
    }
    
    // cierra la ventana...
    private void doExit()
    {
        this.setVisible( false );
    }
    
    // muestra una ventanita con datos generales...
    private void doHelp()
    {
        String cad = "GraphicsConsole - Versión 1.1 - Junio de 2011\nReglas Del Juego :Consulte Documento Adjunto";
        cad += "\nPowered by Ing. Valerio Frittelli";
        JOptionPane.showMessageDialog( this, cad );
    }
    
    // activa un proceso de impresión...
    private void doPrint()
    {
        // una clase local al método... para controlar la impresión...
        class PrintHandler implements Printable
        {
            public int print( Graphics g, PageFormat pf, int pi ) throws PrinterException 
            {
                if( pi >= 1 ) return Printable.NO_SUCH_PAGE;
                Graphics2D g2 = ( Graphics2D ) g;
                g2.translate( pf.getImageableX(), pf.getImageableY() );
                Image img = db.getScaledInstance( (int) pf.getImageableWidth(), (int) pf.getImageableHeight(), Image.SCALE_AREA_AVERAGING );
                g2.drawImage( img, 0, 0, GraphicsConsole.this );
                return Printable.PAGE_EXISTS;
            }
        }
        
        // ajustar configuración de la página... se recomienda impresión apaisada...
        PageFormat pf = new PageFormat ();
        pf.setOrientation( PageFormat.LANDSCAPE );    
        
        PrintHandler ph = new PrintHandler();
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable( ph, pf );
        
        // mostrar cuadro de impresión, y si todo está bien, imprimir...
        if( pj.printDialog() )
        {
            try 
            {
                pj.print();  
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
        }
    }
    
    //********************************************************
    //************************ Métodos públicos y estáticos...
    //********************************************************
    
    /**
     *  Obtiene el único objeto existente de la clase GraphicsConsole. 
     *  Se aplica el patrón Singleton.
     *  @return el único objeto GraphicsConsole que la clase permite crear.
     */
    public static GraphicsConsole getInstance()
    {
        return gc;
    }
    
    /**
     * Emite un sonido de atención.
     */
    public static void beep()
    {
        Toolkit.getDefaultToolkit().beep();
    }
    
    /**
     * Provoca una pausa de "milis" milisegundos en la ejecución del programa.
     * @param milis la cantidad de milisegundos que durará la pausa en la ejecución.
     */
    public static void delay( long milis )
    {
        try
        {
            Thread.sleep( milis );
        }
        catch( InterruptedException e )
        {
        }
    }
    
    
    //********************************************************
    //************************************ Métodos públicos...
    //********************************************************
    
    /**
     * Borra el contenido del área rectangular especificada por los parámetros. 
     * Los límites izquierdo y derecho del área rectangular a limpiar son (x) y 
     * (x + width) respectivamente. Los límites superior e inferior del rectángulo 
     * son (y) y (y + height) respectivamente. Note que el área será rellenada con 
     * el color usado hasta ese momento como color de dibujo para la ventana. 
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     */
    public void clearRect( int x, int y, int width, int height )
    {        
        cg.fillRect( x, y, width, height );
        lienzo.repaint( x, y, x + width, y + height );
    }
    
    /**
     * Copia el contenido del área en el rectángulo indicado, hacia otra área 
     * desplazada en dx y dy pixels. El desplazamiento se hará de arriba hacia 
     * abajo y hacia la derecha. Si se desea un desplazamiento hacia arriba o 
     * hacia la izquierda, especifique valores negativos para dx y dy.
     * Los límites izquierdo y derecho del área rectangular a copiar son (x) y 
     * (x + width) respectivamente. Los límites superior e inferior del rectángulo 
     * son (y) y (y + height) respectivamente. 
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param dx desplazamiento horizontal para copiar los pixels.
     * @param dy desplazamiento vertical para copiar los pixels.
     */
    public void copyArea( int x, int y, int width, int height, int dx, int dy )
    {        
        cg.copyArea( x, y, width, height, dx, dy );
        lienzo.repaint( );
    }
    
    /**
     * Dibuja el contorno de un rectángulo con bordes trabajados para simular un 
     * efecto de sobre relieve (raised = true) o bajo relieve (raised = false).
     * Los límites izquierdo y derecho del rectángulo son (x) y (x + width) 
     * respectivamente, aunque gráficamente el rectángulo acupará un área que 
     * llegará hasta (width + 1) y (height + 1). Los límites superior e inferior 
     * del rectángulo son (y) y (y + height) respectivamente. El color usado será 
     * el que en ese momento esté fijado como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param raised un boolean para indicar si el rectángulo debe aparecer elevado o
     * hundido en la ventana.
     */
    public void draw3DRect( int x, int y, int width, int height, boolean raised )
    {        
        cg.draw3DRect( x, y, width, height, raised );
        lienzo.repaint( x, y, x + width + 1, y + height + 1 );
    }
    
    /**
     * Dibuja el contorno de un arco circular o elíptico, dentro de los límites 
     * del rectángulo indicado. Los límites izquierdo y derecho del rectángulo 
     * son (x) y (x + width) respectivamente. Los límites superior e inferior 
     * del rectángulo son (y) y (y + height) respectivamente. El arco comienza 
     * a dibujarse desde el ángulo indicado por (startAngle) y proseguirá hasta
     * barrer un ángulo equivalente a (arcAngle). El color usado será el que en 
     * ese momento esté fijado como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param startAngle el ángulo de inicio del arco a dibujar.
     * @param arcAngle el ángulo que será barrido por el arco.
     */
    public void drawArc( int x, int y, int width, int height, int startAngle, int arcAngle )
    {
        cg.drawArc( x, y, width, height, startAngle, arcAngle );
        lienzo.repaint( x, y, x + width + 1, y + height );
    }
    
    
    /**
     * Despliega tanto como sea posible una imagen, comenzando desde el punto 
     * superior izquierdo cuyas coordenadas son (x, y). Si el método logró cargar 
     * la imagen completamente, retorna true. Si no logró cargar por completo la 
     * imagen, retorna false. El parámetro img indica el nombre del archivo que
     * contiene a la imagen (que puede ser de tipo img, jpeg o png). Si no se indica 
     * la ruta de directorios de ese archivo, se supondrá que el mismo está en la 
     * carpeta del proyecto.
     * @param img el nombre del archivo que contiene a la imagen.
     * @param x coordenada de columna del punto superior izquierdo de la imagen.
     * @param y coordenada de fila del punto superior izquierdo. 
     */
    public boolean drawImage( String img, int x, int y )
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage( img );
        this.setIconImage( image );
        boolean ok = cg.drawImage( image, x, y, null );
        lienzo.repaint( x, y, image.getWidth(this), image.getHeight(this) );
        this.setIconImage( null );
        return ok;
    }
    
    /**
     * Despliega tanto como sea posible una imagen, comenzando desde el punto 
     * superior izquierdo cuyas coordenadas son (x, y). Si el método logró cargar 
     * la imagen completamente, retorna true. Si no logró cargar por completo la 
     * imagen, retorna false. El parámetro img indica el nombre del archivo que
     * contiene a la imagen que puede ser de tipo img, jpeg o png). Si no se indica 
     * la ruta de directorios de ese archivo, se supondrá que el mismo está en la 
     * carpeta del proyecto.
     * @param img el nombre del archivo que contiene a la imagen.
     * @param x coordenada de columna del punto superior izquierdo de la imagen.
     * @param y coordenada de fila del punto superior izquierdo. 
     * @param width el ancho del rectángulo que contendrá a la imagen.
     * @param height el alto del rectángulo.
     */
    public boolean drawImage( String img, int x, int y, int width, int height )
    {     
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage( img );
        this.setIconImage( image );
        boolean ok = cg.drawImage( image, x, y, width, height, this );
        lienzo.repaint( x, y, width, height );
        this.setIconImage( null );
        return ok;
    }
    
    
    /**
     * Dibuja una línea, comenzando desde el punto (x1, y1) y terminando en el 
     * punto (x2, y2). El color usado será el que en ese momento esté fijado como 
     * color de dibujo para la ventana.
     * @param x1 coordenada de columna del punto inicial de la línea.
     * @param y1 coordenada de fila del punto inicial.
     * @param x2 coordenada de columna del punto final de la línea.
     * @param y2 coordenada de fila del punto final.
     */
    public void drawLine( int x1, int y1, int x2, int y2 )
    {        
        cg.drawLine( x1, y1, x2, y2 );
        lienzo.repaint( x1, y1, x2 - x1, y2 - y1 );
    }
    
    /**
     * Dibuja el contorno de un círculo o de una elipse, inscripta en el rectángulo
     * dado. Los límites izquierdo y derecho del rectángulo son (x) y (x + width) 
     * respectivamente. Los límites superior e inferior del rectángulo son (y) y 
     * (y + height) respectivamente. El color usado será el que en ese momento esté 
     * fijado como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     */
    public void drawOval( int x, int y, int width, int height )
    {        
        cg.drawOval( x, y, width, height );
        lienzo.repaint( x, y, x + width, y + height );
    }

    /**
     * Dibuja un polígono cerrado con (n) puntos a modo de vértices. Las 
     * coordenadas (x, y) de cada uno de los (n) puntos están dadas por los 
     * arreglos (xPoints) e (yPoints). Automáticamente será trazada una línea
     * para unir el último punto con el primero, salvo que se trate del mismo 
     * punto. El color usado será el que en ese momento esté fijado como color 
     * de dibujo para la ventana.
     * @param xPoints un arreglo con los valores de las coordenadas de columna de
     * los puntos vértice del polígono.
     * @param yPoints un arreglo con los valores de las coordenadas de fila de
     * los puntos vértice del polígono.
     * @param n la cantidad de puntos a usar para dibujar el poligono.
     */
    public void drawPolygon( int[] xPoints, int[] yPoints, int n )
    {        
        cg.drawPolygon( xPoints, yPoints, n );
        lienzo.repaint();
    }
        
    /**
     * Dibuja el contorno de un rectángulo. Los límites izquierdo y derecho del 
     * rectángulo son (x) y (x + width) respectivamente. Los límites superior e 
     * inferior del rectángulo son (y) y (y + height) respectivamente. El color
     * usado será el que en ese momento esté fijado como color de dibujo para
     * la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     */
    public void drawRect( int x, int y, int width, int height )
    {        
        cg.drawRect( x, y, width, height );
        lienzo.repaint( x, y, x + width, y + height );
    }
    
    /**
     * Dibuja el contorno de un rectángulo con bordes redondeados. Los límites 
     * izquierdo y derecho del rectángulo son (x) y (x + width) respectivamente. 
     * Los límites superior e inferior del rectángulo son (y) y (y + height) 
     * respectivamente. El color usado será el que en ese momento esté fijado 
     * como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param arcWidth diámetro horizontal del arco usado en cada esquina.
     * @param arcHeight diámetro vertical del arco usado en cada esquina.
     */
    public void drawRoundRect( int x, int y, int width, int height, int arcWidth, int arcHeight )
    {        
        cg.drawRoundRect( x, y, width, height, arcWidth, arcHeight );
        lienzo.repaint( x, y, x + width, y + height );
    }
    
    /**
     * Dibuja (muestra...) el contenido de la cadena (s) usando la fuente y el color
     * actualmente activados en la ventana. La línea base del caracter más a la izquierda
     * de la cadena se ubicará en la posición (x, y).  
     * @param s la cadena a dibujar.
     * @param x la coordenada x (columna).
     * @param y la coordenada y (fila). 
     */
    public void drawString( String s, int x, int y )
    {
        FontMetrics fm = cg.getFontMetrics();
        Rectangle2D sb = fm.getStringBounds( s, cg );
        
        cg.drawString( s, x, y );

        int cx = ( int ) sb.getX();
        int cy = ( int ) sb.getY();
        int ancho = cx + ( int ) sb.getWidth();
        int alto = cy + ( int ) sb.getHeight();
        lienzo.repaint( cx, cy, ancho, alto );
    }

    /**
     * Dibuja y rellena (pinta por dentro) un rectángulo con bordes trabajados 
     * para simular un efecto de sobre relieve (raised = true) o bajo relieve 
     * (raised = false). Los límites izquierdo y derecho del rectángulo son (x) 
     * y (x + width) respectivamente, aunque gráficamente el rectángulo acupará un 
     * área que llegará hasta (width + 1) y (height + 1). Los límites superior e 
     * inferior del rectángulo son (y) y (y + height) respectivamente. El color 
     * usado será el que en ese momento esté fijado como color de dibujo para la 
     * ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param raised un boolean para indicar si el rectángulo debe aparecer elevado o
     * hundido en la ventana.
     */
    public void fill3DRect( int x, int y, int width, int height, boolean raised )
    {        
        cg.fill3DRect( x, y, width, height, raised );
        lienzo.repaint( x - 1, y - 1, x + width + 1, y + height + 1 );
    }
    
    /**
     * Dibuja y rellena un arco circular o elíptico, dentro de los límites del 
     * rectángulo indicado. Los límites izquierdo y derecho del rectángulo son (x) 
     * y (x + width) respectivamente. Los límites superior e inferior del rectángulo 
     * son (y) y (y + height) respectivamente. El arco comienza a dibujarse desde el 
     * ángulo indicado por (startAngle) y proseguirá hasta barrer un ángulo equivalente 
     * a (arcAngle). El color usado será el que en ese momento esté fijado como color 
     * de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param startAngle el ángulo de inicio del arco a dibujar.
     * @param arcAngle el ángulo que será barrido por el arco.
     */
    public void fillArc( int x, int y, int width, int height, int startAngle, int arcAngle )
    {
        cg.fillArc( x, y, width, height, startAngle, arcAngle );
        lienzo.repaint( x, y, x + width + 1, y + height );
    }
        
    /**
     * Dibuja un círculo o una elipse, pintada por dentro e inscripta en el rectángulo
     * dado. Los límites izquierdo y derecho del rectángulo son (x) y (x + width) 
     * respectivamente. Los límites superior e inferior del rectángulo son (y) y 
     * (y + height) respectivamente. El color usado será el que en ese momento esté 
     * fijado como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     */
    public void fillOval( int x, int y, int width, int height )
    {        
        cg.fillOval( x, y, width, height );
        lienzo.repaint( x, y, x + width, y + height );
    }

    /**
     * Dibuja un polígono cerrado y pintado por dentro con (n) puntos a modo 
     * de vértices. Las coordenadas (x, y) de cada uno de los (n) puntos están 
     * dadas por los arreglos (xPoints) e (yPoints). Automáticamente será trazada 
     * una línea para unir el último punto con el primero, salvo que se trate del 
     * mismo punto. El color usado será el que en ese momento esté fijado como color 
     * de dibujo para la ventana.
     * @param xPoints un arreglo con los valores de las coordenadas de columna de
     * los puntos vértice del polígono.
     * @param yPoints un arreglo con los valores de las coordenadas de fila de
     * los puntos vértice del polígono.
     * @param n la cantidad de puntos a usar para dibujar el poligono.
     */
    public void fillPolygon( int[] xPoints, int[] yPoints, int n )
    {        
        cg.fillPolygon( xPoints, yPoints, n );
        lienzo.repaint();
    }
        
    /**
     * Dibuja un rectángulo pintado por dentro. Los límites izquierdo y derecho del 
     * rectángulo son (x) y (x + width) respectivamente. Los límites superior e 
     * inferior del rectángulo son (y) y (y + height) respectivamente. El color
     * usado será el que en ese momento esté fijado como color de dibujo para
     * la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     */
    public void fillRect( int x, int y, int width, int height )
    {        
        cg.fillRect( x, y, width, height );
        lienzo.repaint( x, y, x + width, y + height );
    }
    
    /**
     * Dibuja un rectángulo pintado por dentro, con los bordes redondeados. Los 
     * límites izquierdo y derecho del rectángulo son (x) y (x + width) 
     * respectivamente. Los límites superior e inferior del rectángulo son (y) 
     * y (y + height) respectivamente. El color usado será el que en ese momento 
     * esté fijado como color de dibujo para la ventana.
     * @param x coordenada de columna del punto superior izquierdo del rectángulo.
     * @param y coordenada de fila del punto superior izquierdo del rectángulo.
     * @param width ancho (en pixels) del rectángulo.
     * @param height alto (en pixels) del rectángulo.
     * @param arcWidth diámetro horizontal del arco usado en cada esquina.
     * @param arcHeight diámetro vertical del arco usado en cada esquina.
     */
    public void fillRoundRect( int x, int y, int width, int height, int arcWidth, int arcHeight )
    {        
        cg.fillRoundRect( x, y, width, height, arcWidth, arcHeight );
        lienzo.repaint( x, y, x + width, y + height );
    }
    
    /**
     * Retorna el color actualmente usado en esta ventana.
     * @return un objeto Color que representa al color actual.
     */
    public Color getColor()
    {
        return cg.getColor();
    }
    
    /**
     * Retorna la fuente de caracteres actualmente usada en esta ventana.
     * @return un objeto Font que representa a la fuente actual.
     */
    public Font getFont()
    {
        return cg.getFont();
    }
    
    /**
     * Retorna las métricas de la fuente de caracteres actualmente usada en 
     * esta ventana.
     * @return un objeto FontMetrics que permite acceder a las propiedades de la 
     * fuente actual.
     */
    public FontMetrics getFontMetrics()
    {
        return cg.getFontMetrics();
    }
    
    /**
     * Cambia el color de dibujo. El color usado desde la invocación a este método 
     * en adelante, será el color (c) tomado como parámetro.
     * @param c el nuevo color a usar.
     */
    public void setColor(  Color c )
    {
        cg.setColor( c );
    }
    
    /**
     * Cambia el tipo de fuente usado para mostrar texto en esta ventana.
     * @param f un objeto representando a la nueva fuente.
     */
    public void setFont( Font f )
    {
        cg.setFont( f );
    }
    
    /**
     * Cambia origen de coordenadas del contexto gráfico usado, de forma que el 
     * nuevo origen coincidirá con (x, y). Todas las coordenadas usadas de aquí 
     * en adelante serán relativas al nuevo origen.
     * @param x la coordenada x del nuevo origen.
     * @param y la coordenada x del nuevo origen.
     */
    public void translate( int x, int y )
    {
        cg.translate( x, y );
    }
    
    /** 
     * Ancho de pantalla 
     * @return Ancho Scream
     */
    public int getWidth()
    { 
      Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
      width = pantalla.width;
      return width;
    }
    /** 
     * Altura de pantalla 
     * @return Altura Scream
     */
    public int getHeight()
    { 
      Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
         height = pantalla.height;
      return height;
    }
    
    public void lockMaxScream()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        
        //gc.setMinimumSize(new Dimension(width-12,height-10));
         gc.setSize(screenSize);
         //setLocationRelativeTo(null);
         gc.setMinimumSize(screenSize);
         gc.setMaximumSize(screenSize);
         gc.setExtendedState( Frame.MAXIMIZED_BOTH );
        //
    }
    public void drawGif(String url)
    {
        JLabel imageLabel = new JLabel();
         JLabel headerLabel = new JLabel();
          ImageIcon gif = new ImageIcon(this.getClass().getResource(url));
            imageLabel.setIcon(gif);
            gc.add(imageLabel, java.awt.BorderLayout.CENTER);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
    
    }

    //********************************************************
    //************************************* Clases privadas...
    //********************************************************
    
    // clase usada para representar al lienzo de dibujo usado 
    // para el doble buffering, y permitir un manejo adecuado 
    // del método paintComponent()
    private class Lienzo extends JPanel
    {
        public Lienzo()
        {
            setBackground( Color.white );
        }
        
        public void paintComponent( Graphics g )
        {
            super.paintComponent( g );
            Graphics2D g2 = ( Graphics2D ) g;
            g2.drawImage( db, 0, 0, this );
        }
    }
}
