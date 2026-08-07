import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.URI;

public class IPLookup {

    public static JSONObject iPLocationFinder(String url) {
        JSONObject json = null;

        try {
            URI uri = new URI(url);
            String targetIp = uri.getHost();

            System.out.println("IP: " + targetIp);
            // Using fields parameter to limit the response to country, region, city, lat, and lon
            String urlString = "http://ip-api.com/json/" + targetIp + "?fields=status,message,country,regionName,isp,city,lat,lon";
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            json = new JSONObject(response.body());
            System.out.println("Geolocation Data Response:\n" + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching geolocation: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    JSONObject convertStringToJsonObject(String str){
        // JSONParser parser = new JSONParser();
        //JSONObject jsonObject = new JSONObject(str);
        try {
            JSONObject jsonObject = new JSONObject(str);
            return jsonObject;
        }catch (JSONException err){
            System.out.printf("Error"+ err.toString());
        }
        return null;
    }

    JSONArray convertStringToJsonArray(String str){

        try {
            JSONArray jsonArray = new JSONArray(str);
            return jsonArray;
        }catch (JSONException err){
            System.out.printf("Error"+ err.toString());
        }
        return null;
    }

    JSONObject convertJsonArrayToJsonObject(JSONArray jsonArray){
        return jsonArray.getJSONObject(0);
    }
}
