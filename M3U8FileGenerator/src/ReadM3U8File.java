import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadM3U8File {

    ReadM3U8File(){}

    public static class ChannelInfo {
        public String title;
        public String url;
        public String duration;
        public String group;
        public String logo;

        /*public ChannelInfo(String groupTitle, int duration, String name, String url) {
            this.groupTitle = groupTitle;
            this.duration = duration;
            this.name = name;
            this.url = url;
        }*/

        @Override
        public String toString() {
            // return String.format("Channel: %s || URL: %s", title, url);
            return String.format("#EXTINF:-1 tvg-id=\"\" group-title=\"-\", %s \n%s", title, url);
        }
    }

    public static List<ChannelInfo> readM3u8Attributes(String path) throws MalformedURLException {

        String regex = "tvg-logo=\\\"(.*?)\\\" group-title=\\\"(.*?)\\\".*,(.+?)$";
        Pattern attrPattern = Pattern.compile(regex);
        List<ChannelInfo> channels = new ArrayList<>();
        URL url = URI.create(path).toURL();

        // IPGeolocator(path);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            //try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            ChannelInfo currentItem = null;
            int duration = 0;
            String title = "";

            while ((line = br.readLine()) != null) {
                // If the line contains attributes (e.g., #EXT-X-STREAM-INF or #EXTINF)
                Matcher matcher = attrPattern.matcher(line);
                line = line.trim();

                // Skip the header tag
                if (line.isEmpty() || line.equals("#EXTM3U")) {
                    continue;
                }

                if (line.startsWith("#EXTINF:")) {
                    currentItem = new ChannelInfo();
                    // Extract duration
                    int commaIndex = line.indexOf(',');
                    String extinfInfo = line.substring(8, commaIndex);
                    String[] parts = extinfInfo.split(" ");

                    //currentItem.groupTitle = title;
                    currentItem.duration = parts[0];

                    // Extract attributes and title using Regex
                    Matcher matcherAttributes = attrPattern.matcher(line);
                    if (matcherAttributes.matches()) {
                        currentItem.logo = matcherAttributes.group(1);
                        currentItem.group = matcherAttributes.group(2);
                        currentItem.title  = matcherAttributes.group(3);
                    } else {
                        currentItem.title = line.substring(commaIndex + 1);
                    }

                    //channels.add(currentItem);
                } else if (currentItem != null && !line.startsWith("#")) {
                    //System.out.println("Entro al ELSE");
                    // It's the URL/File path line
                    //channels.add(new ChannelInfo(title, duration, "name" ,line));

                    currentItem.url = line;
                    channels.add(currentItem);
                    currentItem = null; // Reset for next item
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Validation Failed: Target server is offline or unreachable." + e.getMessage());
        }

        //System.out.println("  -> channels = " + channels);
        return channels;
    }

    static boolean urlValidator(String inputURL){
        try {
            URL url = new URL(inputURL);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            // the URL is not in a valid form
            return false;
        } catch (IOException e) {
            // the connection couldn't be established
            return false;
        }
        return true;
    }
}
