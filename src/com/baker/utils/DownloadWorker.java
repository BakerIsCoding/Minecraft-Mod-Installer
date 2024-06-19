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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Baker
 */
public class DownloadWorker extends SwingWorker<Void, Void> {

    //URL to download
    private String downloadMods;
    private String downloadShader;
    private String downloadConfig;
    private String downloadHorizont;

    private String downloadPath;
    private JLabel speedLabel;
    private JLabel etaLabel;
    private long totalFileSize;
    private ZipManager zipManager;
    private String destDirectory;
    private JButton finalButton;
    private JLabel informativeLabel;

    private boolean removeZip;
    private boolean intallMods;

    public DownloadWorker(String downloadMods, String downloadShader, String downloadConfig, String downloadHorizont, String downloadPath, JLabel speedLabel, JLabel etaLabel, long totalFileSize, ZipManager zipManager, String destDirectory, JButton finalButton, JLabel informativeLabel) {
        this.downloadMods = downloadMods;
        this.downloadShader = downloadShader;
        this.downloadConfig = downloadConfig;
        this.downloadHorizont = downloadHorizont;
        this.downloadPath = downloadPath;
        this.speedLabel = speedLabel;
        this.etaLabel = etaLabel;
        this.totalFileSize = totalFileSize;
        this.zipManager = zipManager;
        this.destDirectory = destDirectory;
        this.finalButton = finalButton;
        this.informativeLabel = informativeLabel;
        this.removeZip = false;
        this.intallMods = false;
    }

    @Override
    protected Void doInBackground() throws Exception {

        try {

            if (downloadMods != null) {
                informativeLabel.setText("Dowloading mods");
                downloadFiles(downloadMods, "mods.zip");
                if (intallMods) {
                    installMods();
                }
            }

            if (downloadShader != null) {
                informativeLabel.setText("Dowloading Shaders");
                downloadFiles(downloadShader, "shaders.zip");
                intallShaders();
            }

            if (downloadConfig != null) {
                informativeLabel.setText("Dowloading Config");
                downloadFiles(downloadConfig, "Config.zip");
                intallConfig();
            }

            if (downloadHorizont != null) {
                informativeLabel.setText("Dowloading Horizont");
                downloadFiles(downloadHorizont, "Horizon.zip");
            }

            informativeLabel.setText("Dowloading successful");
            new Popups().successPopup("Download", "download successful");

        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        reenableButton();

        return null;

    }

    public void installMods() {
        System.out.println(destDirectory + "mods");
        File modDirectory = new File(destDirectory + "mods");
        if (modDirectory.isDirectory()) {
            String[] files = modDirectory.list();
            System.out.println("Archivos en el directorio:");
            if (files.length > 0) {
                System.out.println("Ya hay mods, Borrando ...");
                for (String file : files) {

                    String[] filetipe = file.split("\\.");
                    if (filetipe[filetipe.length - 1].equalsIgnoreCase("jar")) {
                        System.out.println("Se borro: " + file);
                        new File(destDirectory + "mods" + File.separator + file).delete();
                    }
                }
                System.out.println("Limpieza completada");

            }

            zipManager.unzip(downloadPath + File.separator + "mods.zip", destDirectory + "mods");

            if (removeZip) {
                new File(downloadPath+"mods.zip").delete();
                System.out.println("mods Zip Deleted");
            }

        } else {
            System.out.println("No es un directorio válido.");
        }

    }

    private void intallShaders() {
        
        File tempDirectori = new File(downloadPath), mineDirectori = new File(destDirectory);
        if (tempDirectori.exists() && mineDirectori.exists()) {
            System.out.println(downloadPath + "shaders.zip");
            System.out.println(destDirectory + "shaderpacks");

            File shaders = new File(downloadPath + "shaders.zip");
            File directShaders = new File(destDirectory + "shaderpacks");

            File destino = new File(directShaders, "shaders.zip");

            if (shaders.renameTo(destino)) {
                System.out.println("Archivo movido a: " + destino.getPath());
            } else {
                System.out.println("No se pudo mover el archivo.");
            }
            
            if (removeZip) {
                new File(downloadPath+"shaders.zip").delete();
                System.out.println("shaders Zip Deleted");
            }


        } else {
            System.out.println("El directorio temporal o minecraft no existe");
        }
    }

    private void intallConfig() {
        //downloadPath: C:\Users\Javier\AppData\Roaming\.minecraft-mod-installer\temp\
        //destDirectory: C:\Users\Javier\AppData\Roaming\.minecraft\
        System.out.println(downloadPath+"Config.zip");
        System.out.println(destDirectory);
        
        File configFile = new File(downloadPath+"Config.zip");
        String exist = configFile.exists() ? "Existe el archivo" : "No existe el archivo";
        System.out.println(exist);
        
        
        
    }

    private void installHorizont() {
        
        
        
    }

    private void downloadFiles(String downloadUrl, String nameFile) throws MalformedURLException, ProtocolException, IOException, InterruptedException {

        // Crear objeto URL a partir del string downloadUrl
        URL url = new URL(downloadUrl);

        // Abrir una conexión HTTP a la URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Configurar la conexión para seguir redirecciones automáticamente
        con.setInstanceFollowRedirects(true);

        // Establecer el método de solicitud HTTP a "GET"
        con.setRequestMethod("GET");

        // Obtener el código de respuesta del servidor
        int responseCode = con.getResponseCode();

        // Verificar si el código de respuesta es 200 (OK), lo que indica que la solicitud fue exitosa
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Guardar el tiempo de inicio de la descarga
            long startTime = System.currentTimeMillis();
            // Inicializar el contador de bytes descargados
            long downloadedBytes = 0;
            // Inicializar el tiempo de la última actualización de la interfaz de usuario
            long lastUpdateTime = 0;

            // Usar try-with-resources para abrir un InputStream y un FileOutputStream, asegurando que se cierren automáticamente
            try (InputStream in = new BufferedInputStream(con.getInputStream()); FileOutputStream fileOutputStream = new FileOutputStream(downloadPath + File.separator + nameFile)) {

                // Crear un búfer de 1 KB para leer datos
                byte[] dataBuffer = new byte[1024];
                // Declarar una variable para almacenar la cantidad de bytes leídos
                int bytesRead;

                // Leer datos del InputStream en bloques de 1 KB hasta que se alcance el final del flujo
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    // Escribir los datos leídos en el archivo de destino
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    // Actualizar el contador de bytes descargados
                    downloadedBytes += bytesRead;

                    // Obtener el tiempo actual
                    long currentTime = System.currentTimeMillis();
                    // Calcular el tiempo transcurrido desde el inicio de la descarga
                    long timeElapsed = currentTime - startTime;

                    // Verificar si ha pasado al menos un segundo desde la última actualización
                    if (currentTime - lastUpdateTime >= 1000) {
                        // Calcular la velocidad de descarga en MB/s
                        double speed = (downloadedBytes / 1024.0 / 1024.0) / (timeElapsed / 1000.0);
                        // Redondear la velocidad a dos decimales
                        double speedRounded = Math.round(speed * 100.0) / 100.0;
                        // Calcular los bytes restantes por descargar
                        long remainingBytes = totalFileSize - downloadedBytes;
                        // Calcular el tiempo estimado de llegada (ETA) en segundos
                        long eta = (long) (remainingBytes / (speed * 1024 * 1024));

                        // Actualizar la interfaz de usuario en el hilo de la interfaz gráfica de usuario (GUI)
                        SwingUtilities.invokeLater(() -> {
                            speedLabel.setText("Velocidad: " + speedRounded + " MB/s");
                            etaLabel.setText("Tiempo estimado: " + eta + " s");
                        });

                        // Actualizar el tiempo de la última actualización
                        lastUpdateTime = currentTime;
                    }
                }
            }

        } else {
            // Lanzar una excepción si el código de respuesta no es HTTP_OK (200)
            throw new IOException("Error en la descarga: Código de respuesta = " + responseCode);
        }

    }

    public void setInstallMods(boolean x) {
        this.intallMods = x;
    }

    public void setRemovelZip(boolean x) {
        this.removeZip = x;
    }

    private void reenableButton() {
        if (finalButton != null) {
            finalButton.setEnabled(true);
        }

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
