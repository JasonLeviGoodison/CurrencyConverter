package Data;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Utils;

/**
 * Created by jason on 7/29/16.
 */
public class HttpClient {
    public String getData (String convertTo) throws IOException{
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            Log.v("HTTP", "about to open connection");
            connection = (HttpURLConnection) (new URL(Utils.BASE_URL)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }

            inputStream.close();
            connection.disconnect();
            Log.v("HttpClient", "JUst disconnected. have data");
            return stringBuffer.toString();
        }
        catch (IOException e) {
            Log.v("Connecting", "Could not connect");
            throw e;
        }

    }
}
