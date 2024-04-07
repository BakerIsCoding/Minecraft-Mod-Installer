/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.Requests;

import com.baker.utils.JsonParser;
import com.baker.simpleExceptions.SimpleException;
import com.baker.utils.Popups;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class RequestGet {

    public String downloadFile(String url, Map<String, String> parameters, String downloadPath) {
        try {
            URL obj = new URL(url + "?" + getParamsString(parameters));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = con.getContentType();

                // Comprueba si la respuesta es un archivo o un JSON
                if ("application/json".equals(contentType)) {
                    // Manejar respuesta JSON (la API key podría no ser válida)
                    StringBuilder jsonResponse = new StringBuilder();
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            jsonResponse.append(inputLine);
                        }
                    }
                    return jsonResponse.toString();
                } else {
                    // Si la respuesta es un archivo, procede a descargarlo
                    try (InputStream in = new BufferedInputStream(con.getInputStream()); FileOutputStream fileOutputStream = new FileOutputStream(downloadPath)) {
                        byte[] dataBuffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);
                        }
                    }
                    return null; // La descarga fue exitosa
                }
            } else {
                // Si el código de respuesta no es OK, manejar como error
                return "Error en la solicitud: Código de respuesta = " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Excepción al procesar la solicitud: " + e.getMessage();
        }
    }

    public JSONObject sendGetRequest(String url, Map<String, String> parameters) {
        Popups popup = new Popups();
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url + "?" + getParamsString(parameters));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Imprime la respuesta completa (opcional)
                System.out.println(response.toString());

                // Convierte la respuesta StringBuilder a JSONObject
                return new JSONObject(response.toString());
            } else {
                //popup.errorPopup("Error", "Ha ocurrido un error al hacer la petición al servidor\nPrueba a reiniciar la aplicación o contacta a un administrador\nCódigo de respuesta: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }
            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1) // Elimina el último "&"
                    : resultString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
