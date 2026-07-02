import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadJsonFiles {
    ReadJsonFiles(){}

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
