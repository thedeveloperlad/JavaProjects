import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.GridBagConstraints;

public class IPLookupPanel extends JPanel {
    private JLabel statusLabel = new JLabel("Status: ");
    private final JTextField  statusValue = new JTextField (10);
    private JLabel countryLabel = new JLabel("Country: ");
    private final JTextField  countryValue = new JTextField (10);
    private JLabel ispLabel = new JLabel("ISP: ");
    private final JTextField  ispValue = new JTextField (10);

    public IPLookupPanel(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("IP Lookup"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        add(statusLabel, gbc);
        gbc.gridy++;
        add(countryLabel, gbc);
        gbc.gridy++;
        add(ispLabel, gbc);
        gbc.gridy++;

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 5;
        gbc.fill = GridBagConstraints.VERTICAL;

        statusValue.setEditable(false);
        countryValue.setEditable(false);
        ispValue.setEditable(false);

        statusValue.setSize(5,15);
        countryValue.setSize(5,15);
        ispValue.setSize(5,15);

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
}
