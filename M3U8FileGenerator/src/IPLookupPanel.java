import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.GridBagConstraints;

public class IPLookupPanel extends JPanel {
    private JLabel statusLabel = new JLabel("Status: ");
    private final JTextField  statusValue = new JTextField (15);
    private JLabel countryLabel = new JLabel("Country: ");
    private final JTextField  countryValue = new JTextField (15);
    private JLabel ispLabel = new JLabel("ISP: ");
    private final JTextField  ispValue = new JTextField (15);

    public IPLookupPanel(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(605, 100));
        System.out.println("IPLookupPanel.Width: " + this.getWidth());
        System.out.println("IPLookupPanel.Height: " + this.getHeight());
        setBorder(new CompoundBorder(new TitledBorder("IP Lookup"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        /*
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        */

        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically

        add(statusLabel, gbc);
        gbc.gridy++;
        add(countryLabel, gbc);
        gbc.gridy++;
        add(ispLabel, gbc);
        gbc.gridy++;

        gbc.gridx++;
        gbc.gridy = 0;
        // gbc.weightx = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        statusValue.setEditable(false);
        countryValue.setEditable(false);
        ispValue.setEditable(false);

        statusValue.setSize(5,150);
        countryValue.setSize(5,150);
        ispValue.setSize(5,150);

        add(statusValue, gbc);
        gbc.gridy++;
        add(countryValue, gbc);
        gbc.gridy++;
        add(ispValue, gbc);
        gbc.gridy++;
    }

    public String getStatusValue() {
        return statusValue.getText();
    }

    public void setStatusValue(String value) {
        statusValue.setText(value);
    }

    public String getCountryValue() {
        return countryValue.getText();
    }

    public void setCountryValue(String value) {
        countryValue.setText(value);
    }

    public String getIspValue() {
        return ispValue.getText();
    }

    public void setIspValue(String value) {
        ispValue.setText(value);
    }

    public void setIpLookupValues(String status, String country, String isp) {
        setStatusValue(status);
        setCountryValue(country);
        setIspValue(isp);
    }
}
