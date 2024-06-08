/*
 * The MIT License
 *
 * Copyright 2024 Baker.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.baker.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Baker
 */
public class DownloadWorker extends SwingWorker<Void, Void> {

    private String downloadUrl;
    private String downloadPath;
    private JLabel speedLabel;
    private JLabel etaLabel;
    private long totalFileSize;
    private ZipManager zipManager;
    private String destDirectory;
    private boolean intallMods;

    public DownloadWorker(String downloadUrl, String downloadPath, JLabel speedLabel, JLabel etaLabel, long totalFileSize, ZipManager zipManager, String destDirectory) {
        this.downloadUrl = downloadUrl;
        this.downloadPath = downloadPath;
        this.speedLabel = speedLabel;
        this.etaLabel = etaLabel;
        this.totalFileSize = totalFileSize;
        this.zipManager = zipManager;
        this.destDirectory = destDirectory;
        this.intallMods = false;
    }

    @Override
    protected Void doInBackground() throws Exception {
        URL url = new URL(downloadUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setInstanceFollowRedirects(true);
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            long startTime = System.currentTimeMillis();
            long downloadedBytes = 0;
            long lastUpdateTime = 0;  // Almacena el tiempo de la última actualización

            try (InputStream in = new BufferedInputStream(con.getInputStream()); FileOutputStream fileOutputStream = new FileOutputStream(downloadPath)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    downloadedBytes += bytesRead;

                    long currentTime = System.currentTimeMillis();
                    long timeElapsed = currentTime - startTime;

                    // Actualiza los labels solo si ha pasado al menos un segundo desde la última actualización
                    if (currentTime - lastUpdateTime >= 1000) {
                        double speed = (downloadedBytes / 1024.0 / 1024.0) / (timeElapsed / 1000.0);
                        double speedRounded = Math.round(speed * 100.0) / 100.0;
                        long remainingBytes = totalFileSize - downloadedBytes;
                        long eta = (long) (remainingBytes / (speed * 1024 * 1024));

                        SwingUtilities.invokeLater(() -> {
                            speedLabel.setText("Velocidad: " + speedRounded + " MB/s");
                            etaLabel.setText("ETA: " + eta + " s");
                        });

                        lastUpdateTime = currentTime;  // Actualiza el tiempo de la última actualización
                    }
                }
            }
            Thread.sleep(100);
            if(intallMods){
                installMods();
            }
            
        } else {
            throw new IOException("Error en la descarga: Código de respuesta = " + responseCode);
        }
        return null;
    }
    
    
    
    public void installMods(){
        
        
        File directory = new File(destDirectory);
        if (directory.isDirectory()) {
            String[] files = directory.list();
            System.out.println("Archivos en el directorio:");
            if(files.length > 0){
                System.out.println("Ya hay mods, Borrando ...");
                for(String file : files){
                    
                    String[] filetipe = file.split("\\.");
                    if(filetipe[filetipe.length-1].equalsIgnoreCase("jar")){
                        System.out.println("Se borro: "+file);
                        new File(destDirectory+File.separator+file).delete();
                    }
                }
                System.out.println("Limpieza completada");
                
            }
            
            zipManager.unzip(downloadPath, destDirectory);
            
            
            new File(downloadPath).delete();
            

        } else {
            System.out.println("No es un directorio válido.");
        }
        
    }
    
    
    public void setInstallMods(boolean x){
        this.intallMods = x;
    }
    /*
    @Override
    protected void done() {
        try {
            get();  // Esto lanza cualquier excepción que ocurra durante doInBackground
            System.out.println("a descomprimir");
            zipManager.unzip(downloadPath, destDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error durante la descarga o descompresión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
*/
}
