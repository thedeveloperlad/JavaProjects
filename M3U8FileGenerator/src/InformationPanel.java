import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InformationPanel extends JPanel {
    JTextArea logTextArea = new JTextArea(20, 40);
    JScrollPane scrollPane = new JScrollPane(logTextArea);

    public InformationPanel(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("m3u8 file information"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        /*add(statusLabel, gbc);
        gbc.gridy++;*/

        gbc.gridx++;
        gbc.weightx = 5;
        gbc.fill = GridBagConstraints.VERTICAL;

        add(scrollPane, gbc);
        gbc.gridy++;
    }

    void setLogTextArea(String textInformation){
        System.out.println(textInformation);
        logTextArea.setText(textInformation);
    }

    String getLogTextArea(){
        return logTextArea.getText();
    }

}
