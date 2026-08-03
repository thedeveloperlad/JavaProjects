import javax.swing.*;
import java.awt.*;

import javax.swing.JPanel;

public class M3u8Properties extends JPanel {
    private final IPLookupPanel lookupPanel;

    public M3u8Properties() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);

        add((lookupPanel = new IPLookupPanel()), gbc);
        gbc.gridy++;
        /*add((databasePane = new DatabasePane()), gbc);
        gbc.gridy++;
        add((systemDatabasePane = new SystemDatabasePane()), gbc);*/

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;
        gbc.weightx = 0;
        // add((actionPane = new ActionPane()), gbc);
    }
    /*statusValue.setText(status);
            countryValue.setText(country);
            ispValue.setText(isp);*/
    void setIpLookupValues(String status, String country, String isp){
        lookupPanel.setStatusValue(status);
        lookupPanel.setCountryValue(country);
        lookupPanel.setIspValue(isp);
    }
}
