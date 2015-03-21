import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Clase que contiene un main() para lanzar el juego del Saca Camisa.
 */
public class Principal
{
    public static void main( String args[] )throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        String nom1;
        int i=0,ren=JOptionPane.YES_NO_OPTION,renic=JOptionPane.NO_OPTION,term;
        GestorJuego gj;
        GestorGraficos g=new GestorGraficos();
        term= JOptionPane.showConfirmDialog(null, " GNU GENERAL PUBLIC LICENSE\n Licencia pública general GNU \nRequerimientos:\n*Resolucion entre 1360X768 y 1920X1080 o mayores\n*Placa de sonido\nCrisisGenCy 2012 - 1k1 \n Ing. Valerio Frittelli \nIng. Gustavo Federico Bett\n Ing. German Ariel Romani\nAcepta Los Términos...?","Términos y condiciones para la copia, distribución y modificación", JOptionPane.YES_NO_OPTION);
        if(term==JOptionPane.NO_OPTION)
        {
            JOptionPane.showMessageDialog(null,"ha Salido Con exito del juego.Gracia por juegar con nosotros Soporte crisisgency@outlook.com ","Exit", JOptionPane.PLAIN_MESSAGE);
            g.ocultTable();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"El juego De Saca Camisa Se Ajustara Automaticamente a su Pantalla \nRECUERDE :La Resoluciones Aceptada son >=1360 X 768 Y >= 1920X1080.\nActive el sonido","Ajuste de Resolucion de Pantalla", JOptionPane.WARNING_MESSAGE);
            do
            {
                    do{
                        if(i==0)
                        Music.bienvenido();
                        g.delay(200);
                        if(i==0)
                        Music.musicFondo();
                        nom1= JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador: ");
                            if(nom1!=null)
                            ren= JOptionPane.showConfirmDialog(null, nom1+" ...Confirma tu nombre de Jugador ","renobrar Player", JOptionPane.YES_NO_OPTION);
                       }while(ren==JOptionPane.NO_OPTION); 
                    if(nom1==null)
                        {
                            i++;
                            JOptionPane.showMessageDialog(null,"No Ha Ingresado ningun nombre","Advertencia Intentos["+i+" de 3]", JOptionPane.WARNING_MESSAGE);
                        }
            }while(nom1==null&&i<3);
            if(i>2)
                        {
                        Music.stop();
                        g.limpiarMesa();
                        g.ocultTable();
                        JOptionPane.showMessageDialog(null,"ha Salido del juego Intentos Permitidos 3.Gracia por juegar con nosotros Soporte crisisgency@outlook.com ","Exit", JOptionPane.PLAIN_MESSAGE);}
                        else
                        {
                            String nom2 = "Computadora";
                            JOptionPane.showMessageDialog(null,"Players:"+nom1+" VS "+nom2,"Jugadores", JOptionPane.INFORMATION_MESSAGE);
                            int conf = JOptionPane.showConfirmDialog(null, nom1+" ...Desea Iniciar el Juego? No,para salir del juego.","inicio de juego", JOptionPane.YES_NO_OPTION);
                            Music.stop();
                            if (conf == JOptionPane.YES_OPTION)
                            {
                                do{
                                    g.fondo();
                                    Progress frame = new Progress();
                                    frame.pack();
                                    frame.setVisible(true);
                                    frame.iterate();
                                    frame.setVisible(false);
                                    Music.startGame();
                                    g.delay( 2000 );
                                    gj = new GestorJuego(nom1, nom2);
                                    JOptionPane.showMessageDialog(null,"Players:"+nom1+" VS "+nom2+"\nGanador: "+ gj.jugar(),"Ganador", JOptionPane.PLAIN_MESSAGE);
                                    renic = JOptionPane.showConfirmDialog(null, nom1+" ...Desea Reiniciar el Juego? No salir del juego.","Reiniciar", JOptionPane.YES_NO_OPTION);
                                    if(renic==JOptionPane.YES_OPTION)
                                            {
                                                g.limpiarMesa();
                                                g.ocultTable();
                                                JOptionPane.showMessageDialog(null,"el juego se reinicira en unos segundos.Gracia por juegar con nosotros Soporte crisisgency@outlook.com ","Exit", JOptionPane.PLAIN_MESSAGE);
                                            }
                                            else
                                            {
                                                g.limpiarMesa();
                                                g.ocultTable();
                                                JOptionPane.showMessageDialog(null,"ha Salido Con exito del juego.Gracia por juegar con nosotros Soporte crisisgency@outlook.com ","Exit", JOptionPane.PLAIN_MESSAGE);
                                            }
                                   }while(renic==JOptionPane.YES_OPTION);
                            }
                            else
                            {
                                Music.stop();
                                g.limpiarMesa();
                                g.ocultTable();
                                JOptionPane.showMessageDialog(null,"ha Salido Con exito del juego.Gracia por juegar con nosotros Soporte crisisgency@outlook.com ","Exit", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
        }       
    }
}
