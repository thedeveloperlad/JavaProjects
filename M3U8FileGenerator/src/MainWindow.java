import javax.swing.*;
import java.awt.*;


public class MainWindow {
    JFrame frame = new JFrame("M3U8 File Generator");

    // JCheckBox parentalCheck = new JCheckBox("Remove +18 channels"); // Checkbox with text
    public M3u8Properties m3u8PropertiesPanel = new M3u8Properties();

    MainWindow(){
        /*

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new DatabasePropertiesPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        */
    }

    public void m3u8FileGeneratorScreen(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 650);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(m3u8PropertiesPanel);

        // frame.add(parentalCheck);

        frame.setVisible(true);
    }
}
