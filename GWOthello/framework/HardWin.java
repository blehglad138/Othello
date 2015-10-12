import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.*;
import java.awt.Font.*;
import java.awt.geom.*;


public class HardWin extends JFrame
{
    public HardWin()
    {
        setTitle( "Good Job! By the way, you need a life." );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
    }
    
    public void display()
    {

        ImageIcon image = new ImageIcon( "cake.jpeg" );
        String grats = "Congragulations are in order. Here is a picture of a cake for you";
        JLabel congrats = new JLabel( grats );

        FontMetrics metrics = getGraphics().getFontMetrics( getGraphics().getFont() );
        int sidel = metrics.stringWidth( grats );
        Image img = image.getImage();
        Image newimg = img.getScaledInstance( sidel,
            sidel,
            java.awt.Image.SCALE_SMOOTH );
        image = new ImageIcon( newimg );
        JLabel label = new JLabel( "", image, JLabel.CENTER );
        Box box = new Box( BoxLayout.X_AXIS );
        box.add( Box.createRigidArea( new Dimension( 15,8 ) ));
        box.add( congrats );
        JPanel panel = new JPanel( new BorderLayout() );
        panel.add( box, BorderLayout.NORTH );
        panel.add( label, BorderLayout.CENTER );

        setLayout( new BoxLayout( getContentPane(), BoxLayout.Y_AXIS ) );
        this.add( panel );
        this.setSize( new Dimension( sidel + 50, metrics.getHeight()
            + sidel + 50 ) );
        this.setResizable( false );
    }


    public static void main( String[] args )
    {
        HardWin h = new HardWin();
        h.setVisible( true );
        h.display();
    }
}
