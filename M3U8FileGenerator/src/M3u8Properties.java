import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

public class M3u8Properties extends JPanel {

    private final IPLookupPanel lookupPanel;
    private final InformationPanel infoPanel;
    // private final InputPanel inputPanel;

    JLabel inputLabel = new JLabel("Enter m3u8 URL:");
    JTextField inputField = new JTextField(20);
    JButton submitButton = new JButton("Generate File");
    JButton clearLogButton = new JButton("Clear logger");
    JButton saveM3U8FileButton = new JButton("Save File");
    JPopupMenu popupMenu = new JPopupMenu();
    List<ReadM3U8File.ChannelInfo> result;

    public M3u8Properties() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically

        add(createUrlInputPanel(), gbc);
        gbc.gridy++;
        add((lookupPanel = new IPLookupPanel()), gbc);
        gbc.gridy++;


        /*gbc.gridx = 0;          // Same column
        gbc.gridy = 2;          // Next row (prevents overlap)
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;*/
        gbc.fill = GridBagConstraints.BOTH;
        add((infoPanel = new InformationPanel()), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(ActionButtonPanel(), gbc);
    }

    void setIpLookupValues(String status, String country, String isp){
        System.out.println("enter to setIpLookupValues");
        lookupPanel.setStatusValue(status);
        lookupPanel.setCountryValue(country);
        lookupPanel.setIspValue(isp);
    }

    void setM3u8Information(String textInformation){
        System.out.println("enter to setM3u8Information");
        infoPanel.setLogTextArea(textInformation);
    }

    public void submitButton(ActionEvent e) {
        System.out.println("submitButton button");
        String userInput = inputField.getText();

        if (!userInput.contains("http://")) {
            userInput = "http://" + userInput;
        }

        /* if(ReadM3U8File.urlValidator(userInput)){
            JOptionPane.showMessageDialog(this, "the URL is not in a valid form or the connection couldn't be established");
            return;
        } */

        // clearOutputs(); // Clear every output

        JOptionPane.showMessageDialog(this, "You entered: " + userInput);

        JSONObject ipLookup = IPLookup.iPLocationFinder(userInput); // put those on labels.

        if (!ipLookup.isEmpty()) {
            String status = ipLookup.getString("status");
            String country = ipLookup.getString("country");
            String isp = ipLookup.getString("isp");

            lookupPanel.setIpLookupValues(status, country, isp);

        }

        try {
            result = ReadM3U8File.readM3u8Attributes(userInput);

            if(result.isEmpty()){
                JOptionPane.showMessageDialog(this, "No data - Connection refused: connect");
                return;
            }

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
    }

    public JPanel createUrlInputPanel() {
        // 1. Create the container panel
        JPanel panel = new JPanel();

        panel.setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        System.out.println("M3u8Properties().createUrlInputPanel.Width: " + panel.getWidth());
        System.out.println("M3u8Properties().createUrlInputPanel.Height: " + panel.getHeight());
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically

        // 3. Add components to the panel in order
        panel.add(inputLabel);
        gbc.gridx++;
        panel.add(inputField);
        gbc.gridx++;
        panel.add(submitButton);
        gbc.gridx++;

        JMenuItem copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyItem.setText("Copy");

        JMenuItem cutItem = new JMenuItem(new DefaultEditorKit.CutAction());
        cutItem.setText("Cut");

        JMenuItem pasteItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        pasteItem.setText("Paste");

        // Add options to the popup menu
        popupMenu.add(copyItem);
        popupMenu.add(cutItem);
        popupMenu.addSeparator();
        popupMenu.add(pasteItem);

        inputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            // Cross-platform check for right-click context trigger
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        submitButton.addActionListener(this::submitButton);

        // 4. Return the completed panel
        return panel;
    }

    public JPanel ActionButtonPanel() {
        // container panel
        JPanel panel = new JPanel();

        panel.setBorder(new CompoundBorder(new TitledBorder("Actions"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically

        // Components to the panel in order
        panel.add(saveM3U8FileButton);
        gbc.gridx++;
        panel.add(clearLogButton);
        gbc.gridx++;

        // submitButton.addActionListener(this::submitButton);
        clearLogButton.addActionListener(this::clearButton);
        saveM3U8FileButton.addActionListener(this::saveFile);

        // 4. Return the completed panel
        return panel;
    }

    private void clearButton(ActionEvent e) {
        infoPanel.setLogTextArea("");
        result.clear();
        lookupPanel.setIpLookupValues("-", "-", "-");
    }

    private void clearOutputs(){
        infoPanel.setLogTextArea("");
        result.clear();
        lookupPanel.setIpLookupValues("-", "-", "-");
    }

    private void saveFile(ActionEvent e) {

        if(infoPanel.getLogTextArea().isEmpty()){
            JOptionPane.showMessageDialog(this, "Text Field is empty");
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            FileNameExtensionFilter filter = new FileNameExtensionFilter("M3u8 Files (*.m3u8)", "m3u8");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                String[] ipLookupValues = new String[3];
                ipLookupValues[0] = lookupPanel.getStatusValue();
                ipLookupValues[1] = lookupPanel.getCountryValue();
                ipLookupValues[2] = lookupPanel.getIspValue();

                ListToM3u8File.createM3u8File(result, fileToSave.getAbsolutePath(), "file name", ipLookupValues);
            } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                System.out.println("Save command was cancelled by the user.");
            }
        }
    }
}
