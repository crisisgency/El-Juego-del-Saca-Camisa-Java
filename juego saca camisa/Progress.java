import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
public class Progress extends JFrame 
{
  JProgressBar current;
  JTextArea out;
  JButton find;
  Thread runner;
  int num = 0;
  public Progress() 
  {
      super("Progress");
      Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
      int height = pantalla.height;
      int width = pantalla.width;
      setSize(width/2, height/2);		
       setLocation( (width / 2)-150,height-270);
      //setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel pane = new JPanel();
      pane.setLayout(new FlowLayout());
      current = new JProgressBar(0, 2000);
      current.setValue(0);
      current.setStringPainted(true);
      pane.add(current);
      setContentPane(pane);
  }

  public void iterate() 
  {
    while (num < 2000) 
    {
        current.setValue(num);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) { }
        num += 95;
    }
  }
  public static void main(String[] arguments) 
  {
  Progress frame = new Progress();
  frame.pack();
  frame.setVisible(true);
  frame.iterate();
  }
}