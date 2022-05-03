import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyPanel extends JPanel {
    private ArrayList<MovingDot> dotList;
    private LaunchPlatform platform;

    KeyPanel(){
        setPreferredSize(new Dimension(500, 500));
        dotList = new ArrayList<MovingDot>();
        platform = new LaunchPlatform(new Point(250,250), 100, 50, Color.BLACK, 1);
        KeyPlay kp = new KeyPlay();
        addKeyListener(kp);
    }

    private class KeyPlay implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                platform.moveLeft();
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                platform.moveRight();
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
