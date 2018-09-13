package GuiSMPP;

import javax.swing.*;
import java.awt.*;

public class Brain {
    private JPanel panel;
    private JButton send, New, Private;
    private JPanel butons, sends;
    private JFrame frame;

    private Brain() {
        Color lavanda = new Color(187,158,207);
        Font font = new Font( "Arial", Font.PLAIN, 15 );

        frame = new JFrame( "Relax Game" );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setSize( 350, 300 );
        frame.setVisible( true );
        frame.setLocationRelativeTo( null );
        frame.setLayout( new BorderLayout() );
    }

    public static void main(String[] args) {
        new Brain();
    }
}
