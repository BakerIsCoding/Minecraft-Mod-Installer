/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.Requests;

import com.baker.utils.JsonParser;
import com.baker.simpleExceptions.SimpleException;
import com.baker.utils.Popups;
import com.baker.utils.TypesChangers;
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
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class RequestGet {

    

    public String downloadFromUrl(String downloadUrl, String downloadPath, JLabel speedLabel, JLabel etaLabel, long totalFileSize) {
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setInstanceFollowRedirects(true);  // Asegurarse de que las redirecciones están habilitadas
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Manejo manual de redirecciones
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                // Obtener nueva URL de la cabecera "Location" y reconectar
                String newUrl = con.getHeaderField("Location");
                url = new URL(newUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                responseCode = con.getResponseCode();
                System.out.println("Followed Redirect: New Response Code: " + responseCode);
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                long startTime = System.currentTimeMillis();
                long downloadedBytes = 0;

                try (InputStream in = new BufferedInputStream(con.getInputStream()); FileOutputStream fileOutputStream = new FileOutputStream(downloadPath)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        downloadedBytes += bytesRead;

                        long currentTime = System.currentTimeMillis();
                        long timeElapsed = currentTime - startTime;
                        if (timeElapsed > 1000) {
                            double speed = (downloadedBytes / 1024.0 / 1024.0) / (timeElapsed / 1000.0);
                            double speedRounded = Math.round(speed * 100.0) / 100.0;
                            long remainingBytes = totalFileSize - downloadedBytes;
                            long eta = (long) (remainingBytes / (speed * 1024 * 1024));

                            SwingUtilities.invokeLater(() -> {
                                speedLabel.setText("Velocidad: " + speedRounded + " MB/s");
                                etaLabel.setText("ETA: " + eta + " s");
                            });
                        }
                    }
                }
                return null;
            } else {
                return "Error en la descarga: Código de respuesta = " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Excepción al descargar el archivo: " + e.getMessage();
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

    public long getFileSize(String url, Map<String, String> parameters) {
        TypesChangers changer = new TypesChangers();
        JSONObject responseJson = sendGetRequest(url, parameters);
        if (responseJson != null && responseJson.has("size")) {
            String sizeStr = responseJson.getString("size");
            return changer.StringSizeToLong(sizeStr);
        } else {
            // Maneja el caso en que la respuesta no contenga el tamaño o haya un error
            System.out.println("Error obteniendo el tamaño del archivo o la respuesta no contiene 'size'.");
            return -1; // Retornar -1 o manejar de otra manera según la lógica de tu aplicación
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
