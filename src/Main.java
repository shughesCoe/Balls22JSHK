import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(new MousePanel());
        frame.getContentPane().add(new LaunchPanel(3));

        frame.pack();
        frame.setVisible(true);
	// write your code here
    }
}
