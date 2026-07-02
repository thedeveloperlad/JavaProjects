import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ReadJsonFiles readJsonFiles = new ReadJsonFiles();
    static String resultInfo;
    static M3u8MediaExtractor m3u8MediaExtractor = new M3u8MediaExtractor();
    static ReadM3u8Files readM3u8Files = new ReadM3u8Files();
    DefaultTableModel dTableModel;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Swing List View");
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        /*System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }*/
        // https://streamtpnew.com/eventos.json?nocache=1775656111252
        // https://raw.githubusercontent.com/thedeveloperlad/links_channels/refs/heads/main/testing_json.json
        // https://streamtp-abc.net/global1.php?stream=disney4

        String targetUrl = "https://streamtp-abc.net/global1.php?stream=espn";
        String testingUrlBig = "https://raw.githubusercontent.com/media-api-dev/media_movies/refs/heads/main/movies_media_2025_2026.m3u8";
        String testingUrl2 = "https://raw.githubusercontent.com/thedeveloperlad/links_channels/refs/heads/main/stream_channels.m3u8";
        //String testingUrl = "http://191.97.61.32:8000/playlist.m3u8";
        String testingUrl = "http://45.176.240.128:8889/playlist.m3u8";
        List<ReadM3u8Files.ChannelInfo> result = readM3u8Files.readM3u8Attributes(testingUrl);
        //result.
        // System.out.println(result);
        //result.toString();

        JList<ReadM3u8Files.ChannelInfo> list = new JList<>((ListModel) result);
        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //result.forEach(System.out::println);
        /*for (int i = 0; i <= result.size(); i++) {
            System.out.println("i = " + result.get(i));
        }*/

        /* for (List<String, String, String> entry : result.set) {
            System.out.println("  -> " + entry.getKey() + " = " + entry.getValue());
        }*/
        //result.get(0).toString();
        //result.forEach(a->System.out.println());
        //showM3u8File(testingUrl);

        URL url = new URL("https://raw.githubusercontent.com/thedeveloperlad/links_channels/refs/heads/main/testing_json.json");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200){
          throw new RuntimeException("Error ocurred: " + responseCode);
        } else {
            StringBuilder infoString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()){
                infoString.append(scanner.nextLine());
            }

            /*System.out.printf("Data: ");
            System.out.printf(infoString.toString());*/
            resultInfo = infoString.toString();
            scanner.close();
        }

        JSONArray jsonArray = readJsonFiles.convertStringToJsonArray(resultInfo);
        JSONObject jsonObject = readJsonFiles.convertJsonArrayToJsonObject(jsonArray);

        /*System.out.printf(jsonObject.getString("title"));
        System.out.printf(jsonObject.getString("time"));
        System.out.printf(jsonObject.getString("category"));
        System.out.printf(jsonObject.getString("status"));
        System.out.printf(jsonObject.getString("link"));
        System.out.printf(jsonObject.getString("language"));*/

        if(!jsonObject.isEmpty()){

            /*System.out.printf("jsonObject.length() = " + jsonObject.length() + "\n");
            System.out.printf("jsonObject.object() = " + jsonObject.keys().toString() + "\n");
            System.out.printf("jsonObject.names() = " + jsonObject.names() + "\n");*/

            /*JSONObject jsonObj = new JSONObject("{'id':['5','6']},{'Tech':['Java']}");
            System.out.println(jsonObj.getString("id"));*/

            System.out.printf("jsonArray.length(): " + jsonArray.length());
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

        /*String[] data = {"One", "Two", "Three", "Four"};
        String [] index;
        index = new String[]{"title", "time", "category", "status", "link", "language"};

        // dTableModel = new DefaultTableModel(rawData, index);
        //data.
        JList<String> list = new JList<>(data);
        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/

        /*InputStreamReader reader = new InputStreamReader(url.openStream());

        String line;
        String data="";
        while((line = reader.readLine()) != null){
            data = data + line;
        }

        System.out.printf("Data: ");
        System.out.printf(infoString);*/

    }

    public static void showM3u8File(String targetUrl){
        try {
            System.out.println("Fetching HTML content...");
            String htmlContent = m3u8MediaExtractor.fetchHtml(targetUrl);

            System.out.println("Extracting .m3u8 links...");
            List<String> m3u8Links = m3u8MediaExtractor.extractM3u8Links(htmlContent);

            if (m3u8Links.isEmpty()) {
                System.out.println("No .m3u8 links found in the static HTML source.");
            } else {
                System.out.println("Found " + m3u8Links.size() + " link(s):");
                for (String link : m3u8Links) {
                    System.out.println(link);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}