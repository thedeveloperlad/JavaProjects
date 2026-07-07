import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends Component {

    JFrame frame = new JFrame("Notepad");
    JTextArea textArea = new JTextArea();
    JMenuItem newFile = new JMenuItem("New");
    JMenuItem open = new JMenuItem("Open");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem print = new JMenuItem("Print");
    JMenuItem close = new JMenuItem ("Close");
    JMenuItem cut = new JMenuItem("Cut");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");

    MainWindow(){

    }

    public void NotepadScreen(){
        // JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {}

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu file = new JMenu("File");

        /*JMenuItem newFile = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem print = new JMenuItem("Print");*/

        file.add(newFile);
        file.add(open);
        file.add(save);
        file.add(print);

        JMenu edit = new JMenu("Edit");

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(close);

        frame.setJMenuBar(menuBar);

        // Close
        close.addActionListener(this::closeAction);
        newFile.addActionListener(this::newFileAction);
        // open.addActionListener(this::openAction);
        save.addActionListener(this::saveAction);
        print.addActionListener(this::printAction);

        cut.addActionListener(this::cutAction);
        copy.addActionListener(this::copyAction);
        paste.addActionListener(this::pasteAction);

        // frame.add(textArea);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();

        return menuBar;
    }

    private void closeAction(ActionEvent e) {
        System.out.println("close button");
        frame.dispose();
    }

    private void newFileAction(ActionEvent e) {
        System.out.println("close button");
        textArea.setText("");
        // frame.dispose();
    }

    private void openAction(ActionEvent e) throws IOException {
        JFileChooser j = new JFileChooser();

        int r = j.showOpenDialog(this);

        if (r == JFileChooser.APPROVE_OPTION) {
            File file = j.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            textArea.read(br, null);
            br.close();
        }
    }

    private void saveAction(ActionEvent e) {
        System.out.println("saveAction");
        JFileChooser jFile = new JFileChooser();

        try {
            int r = jFile.showSaveDialog(this);

            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFile.getSelectedFile();

                BufferedWriter w = new BufferedWriter(new FileWriter(file));
                w.write(textArea.getText());
                w.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void printAction(ActionEvent e) {
        // textArea.print();
    }

    private void cutAction(ActionEvent e) {
        textArea.cut();
    }

    private void copyAction(ActionEvent e) {
        textArea.copy();
    }

    private void pasteAction(ActionEvent e) {
        textArea.paste();
    }
}
