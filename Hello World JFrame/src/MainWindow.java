import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainWindow {
    public static void mainScreen(){
        JFrame frame = new JFrame("My First Java Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Hello, World!", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}
