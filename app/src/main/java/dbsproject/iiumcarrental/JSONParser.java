package dbsproject.iiumcarrental;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


public class JSONParser
{

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;
    private static final String TAG = "JSONParser";


    public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params)
    {
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet())
        {
            try
            {
                if (i != 0)
                {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST"))
        {
            // For request method POST
            try
            {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (method.equals("GET"))
        {
            // For request method GET
            if (sbParams.length() != 0)
            {
                url += "?" + sbParams.toString();
            }

            try
            {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            // Receive response from server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }

            Log.d(TAG, "Received response from server: " + result.toString());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(result.toString());
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}