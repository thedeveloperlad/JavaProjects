import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Chrome Library */
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
// import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.WebDriver;

public class M3u8MediaExtractor {

    //WebDriver driver = new ChromeDriver(); // Successfully resolves openqa references

    public static String fetchHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Emulate a standard web browser to bypass basic anti-bot blocks
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP connection failed with error code: " + status);
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } finally {
            connection.disconnect();
        }

        return content.toString();
    }

    /**
     * Finds and isolates all .m3u8 links hidden within text or scripts via Regex.
     */
    public static List<String> extractM3u8Links(String html) {
        List<String> links = new ArrayList<>();

        // Non-greedy pattern matching URLs ending in .m3u8, including queries
        String regex = "(https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*?\\.m3u8[-a-zA-Z0-9+&@#/%=~_|]*)";
        System.out.println("REGEX= " + regex);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String match = matcher.group(1);
            // Deduplicate links
            if (!links.contains(match)) {
                links.add(match);
            }
        }

        return links;
    }
}
