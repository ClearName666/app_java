package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

public class HttpConnection {
    public static String CreateGetRequest(String urlStr, String urlParameters){
        HttpURLConnection connection = null;
        String ErrorMsg = new String();
        try {
            URL url = new URL(urlStr+ "?" + urlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception e) {
            Log.d("Ошибка", e.getClass().toString());
            ErrorMsg = e.getClass().toString();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return ErrorMsg;
    }

    public static String CreatePOSTRequest(String urlStr, String urlParameters) {
        HttpURLConnection connection = null;
        String ErrorMsg = new String();
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(urlParameters);
            out.flush();
            out.close();

            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            Log.d("Ошибка", e.getClass().toString());
            ErrorMsg = e.getClass().toString();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return ErrorMsg;
    }
}
