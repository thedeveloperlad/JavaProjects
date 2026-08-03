import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static java.awt.SystemColor.window;


public class MainWindow {
    JFrame frame = new JFrame("M3U8 File Generator");
    JLabel label = new JLabel("Enter m3u8 URL:");
    JTextField inputField = new JTextField(20);
    JButton submitButton = new JButton("Generate File");
    JButton clearButton = new JButton("Clear logger");
    JButton saveFileButton = new JButton("Save File");
    JTextArea logTextArea = new JTextArea(10, 30);
    JScrollPane scrollPane = new JScrollPane(logTextArea);
    JCheckBox parentalCheck = new JCheckBox("Remove +18 channels"); // Checkbox with text
    List<ReadM3U8File.ChannelInfo> result;

    public IPLookupPanel IPLookupPanel = new IPLookupPanel();
    public M3u8Properties m3u8PropertiesPanel = new M3u8Properties();

    MainWindow(){
        /*EventQueue.invokeLater(new Runnable() {
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
        });*/
    }

    public void m3u8FileGeneratorScreen(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);

        /*JLabel label = new JLabel("Enter m3u8 URL:");
        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Generate File");*/
        logTextArea.setEditable(false); // Make it read-only
        submitButton.addActionListener(this::submitButton);
        clearButton.addActionListener(this::clearButton);
        saveFileButton.addActionListener(this::saveFile);

        frame.add(label);
        frame.add(inputField);
        frame.add(submitButton, BorderLayout.SOUTH);

        frame.add(m3u8PropertiesPanel);

        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.add(parentalCheck);
        frame.add(saveFileButton);
        frame.add(clearButton);

        frame.setVisible(true);
    }

    private void submitButton(ActionEvent e) {
        System.out.println("submitButton button");
        String userInput = inputField.getText();

        if (!userInput.contains("http://")) {
            userInput = "http://" + userInput;
        }
        JOptionPane.showMessageDialog(frame, "You entered: " + userInput);

        JSONObject ipLookup = IPLookup.iPLocationFinder(userInput); // put those on labels.

        if(!ipLookup.isEmpty()){

            // Extract values /* status,message,country,regionName,isp,city */
            String status = ipLookup.getString("status");
            String country = ipLookup.getString("country");
            String isp = ipLookup.getString("isp");

            // new M3u8Properties().setIpLookupValues(status, country, isp);

            m3u8PropertiesPanel.setIpLookupValues(status, country, isp);



            /*for(int index = 0; index<jsonArray.length(); index++){
                JSONObject innerObj = jsonArray.getJSONObject(index);
                // System.out.println("innerObj= " + innerObj);

                System.out.printf("title: "+ innerObj.get("title") + " ");//getJSONObject("title"));
                System.out.printf("time: "+ innerObj.get("time") + " ");
                System.out.printf("category: "+ innerObj.get("category") + " ");
                System.out.printf("status: "+ innerObj.get("status") + " ");
                System.out.printf("link: "+ innerObj.get("link") + " ");
                System.out.printf("language: "+ innerObj.get("language") + " ");
                System.out.printf("\n");
            }*/

        }
        // List<ReadM3U8File.ChannelInfo> result;
        try {
            result = ReadM3U8File.readM3u8Attributes(userInput);

            StringBuilder sb = new StringBuilder();

            for(ReadM3U8File.ChannelInfo item: result){
                sb.append("").append(item).append(System.lineSeparator());
            }

            logTextArea.setText(sb.toString());
            // logTextArea.setText(result.toString());
            System.out.println(sb.toString());
            // System.out.println(result.toString());
            // System.out.println(result.get(0));
        } catch (IOException error) {
            error.printStackTrace();
        }
        // result.
        // ListToM3u8File.createM3u8File(result);
        // frame.dispose();
    }

    private void clearButton(ActionEvent e) {
        logTextArea.setText("");
        result.clear();
        m3u8PropertiesPanel.setIpLookupValues("-", "-", "-");
    }

    private void saveFile(ActionEvent e) {

        if(logTextArea.getText().isEmpty()){
            JOptionPane.showMessageDialog(frame, "Text Field is empty");
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            FileNameExtensionFilter filter = new FileNameExtensionFilter("M3u8 Files (*.m3u8)", "m3u8");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                ListToM3u8File.createM3u8File(result, fileToSave.getAbsolutePath(), "file name");
            } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                System.out.println("Save command was cancelled by the user.");
            }
        }
    }

    /*private void closeWindow() {
        Boolean decision = ConfirmBox.display("Are you sure you want to exit?", "Title");
        if(decision)
            window.close();
    }*/
}
