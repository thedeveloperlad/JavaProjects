import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadM3u8Files {

    ReadM3u8Files(){}

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
            return String.format("Channel: %s || URL: %s", title, url);
        }
    }

    List<ChannelInfo> readM3u8Attributes (String path) throws MalformedURLException {
        //String regex = "([\\\\w-]+)=(\"[^\"]+\"|[^,]+)";
        String regex = "tvg-logo=\\\"(.*?)\\\" group-title=\\\"(.*?)\\\".*,(.+?)$";
        Pattern attrPattern = Pattern.compile(regex);
        List<ChannelInfo> channels = new ArrayList<>();
        URL url = URI.create(path).toURL();

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
        }

        //System.out.println("  -> channels = " + channels);
        return channels;
    }

    public static boolean isLinkOnline(String m3u8Url) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        try {
            HttpRequest headRequest = HttpRequest.newBuilder()
                    .uri(URI.create(m3u8Url))
                    .header("User-Agent", "Mozilla/5.0") // Some CDNs block empty user-agents
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<Void> headResponse = client.send(headRequest, HttpResponse.BodyHandlers.discarding());

            if (headResponse.statusCode() == 200) {
                return true;
            }

            if (headResponse.statusCode() == 405 || headResponse.statusCode() == 501) {
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(URI.create(m3u8Url))
                        .header("User-Agent", "Mozilla/5.0")
                        .GET()
                        .timeout(Duration.ofSeconds(5))
                        .build();

                HttpResponse<Void> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.discarding());
                return getResponse.statusCode() == 200;
            }

        } catch (IOException | InterruptedException e) {
            // Connection timeout, DNS error, or host unreachable means it's offline
            System.err.println("Error checking link: " + e.getMessage());
        }
        return false;
    }
}
