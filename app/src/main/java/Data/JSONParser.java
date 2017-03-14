package Data;

import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;

/**
 * Created by jason on 7/30/16.
 */
public class JSONParser {
    public static String getRate (String data, String convertTo) {
        //create JsonObject from data
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject ratesObject = Utils.getObject("rates",jsonObject);
            return Utils.getString(convertTo,ratesObject);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
