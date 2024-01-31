/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.Requests;
import com.baker.utils.JsonParser;
import com.baker.simpleExceptions.SimpleException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class RequestGet {
    public JSONObject requestApiUser(String userAuthToken) throws SimpleException {
        try {
            // GET REQUEST
            String url = "https://url.com";
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Required Headers
            connection.setRequestProperty("User-Agent", "UnityPlayer/2021.3.23f1 (UnityWebRequest/1.0, libcurl/7.84.0-DEV)");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Encoding", "deflate, gzip");
            
            // Getting the response Code
            int responseCode = connection.getResponseCode();

            // Reading the request response
            
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parsing and saving the requested data
            JsonParser parser = new JsonParser();
            JSONObject jsonParsed = parser.parseJson(response.toString());
            Integer responseCodeFromRequest = jsonParsed.getInt("code");
            
            // Closing the connections
            reader.close();
            connection.disconnect();
            
            //Checking the response Code
            if (responseCodeFromRequest == 0) {
                System.out.println("code 0");
            } else if (responseCodeFromRequest == -9){
                throw new SimpleException("wrong");
            }else{
                throw new SimpleException("Code: " + responseCodeFromRequest + "\n" + "Message: " + jsonParsed.getString("message"));
            }
            
            return jsonParsed;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
