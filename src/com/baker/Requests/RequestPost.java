/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.Requests;

import com.baker.utils.TypesChangers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class RequestPost {

    public void postGameSaveData(String[] bodyThingsToSend) {
        try { // falta acabar
            Boolean developerMode = false;
            //TypesChangers typeChange = TypesChangers();
            TypesChangers utils = new TypesChangers();

            
            // POST REQUEST
            URL url = new URL("https://url.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Required headers
            connection.addRequestProperty("User-Agent", "UnityPlayer/2021.3.23f1 (UnityWebRequest/1.0, libcurl/7.84.0-DEV)");
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Accept-Encoding", "deflate, gzip");
            connection.addRequestProperty("content-type", "application/json");
            connection.addRequestProperty("game-version", "1.0.5");
            connection.addRequestProperty("X-Unity-Version", "2021.3.23f1");

            // POST REQUEST BODY
            OutputStream outputStream = connection.getOutputStream();
            outputStream.flush();
            outputStream.close();

            // Getting the response Code
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Imprimir la respuesta del servidor
            //if (responseCode.equals(typeChange.integerToString(responseCode))) {
            //}
            if (developerMode) {
                System.out.println("Código de respuesta: " + responseCode);
                System.out.println("Respuesta del servidor: " + response.toString());
            }

            // Closing the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
