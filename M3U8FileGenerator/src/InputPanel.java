import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class InputPanel extends JPanel {
    JLabel inputLabel = new JLabel("Enter m3u8 URL:");
    JTextField inputField = new JTextField(20);
    JButton submitButton = new JButton("Generate File");
    // M3u8Properties properties = new M3u8Properties();
    InformationPanel infoPanel = new InformationPanel();
    // List<ReadM3U8File.ChannelInfo> result;

    public InputPanel(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically
        gbc.anchor = GridBagConstraints.WEST;

        // submitButton.addActionListener(this::submitButton);

        add(inputLabel, gbc);
        gbc.gridx++;
        add(inputField, gbc);
        gbc.gridx++;
        add(submitButton, gbc);

    }

    public String getStatusValue() {
        return inputField.getText();
    }

    public void setStatusValue(String value) {
        inputField.setText(value);
    }

    /* public void submitButton(ActionEvent e) {
        System.out.println("submitButton button");
        String userInput = inputField.getText();

        if (!userInput.contains("http://")) {
            userInput = "http://" + userInput;
        }

        JOptionPane.showMessageDialog(this, "You entered: " + userInput);

        JSONObject ipLookup = IPLookup.iPLocationFinder(userInput); // put those on labels.

        if (!ipLookup.isEmpty()) {
            String status = ipLookup.getString("status");
            String country = ipLookup.getString("country");
            String isp = ipLookup.getString("isp");

            // new M3u8Properties().setIpLookupValues(status, country, isp);

            // m3u8PropertiesPanel.setIpLookupValues(status, country, isp);
            // properties.setIpLookupValues(status, country, isp);

        }

        try {
            result = ReadM3U8File.readM3u8Attributes(userInput);

            StringBuilder sb = new StringBuilder();

            for(ReadM3U8File.ChannelInfo item: result){
                sb.append("").append(item).append(System.lineSeparator());
            }
            // properties.setM3u8Information(sb.toString());
            infoPanel.setLogTextArea(sb.toString());

            // System.out.println(result.toString());
            // System.out.println(result.get(0));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }*/
}
