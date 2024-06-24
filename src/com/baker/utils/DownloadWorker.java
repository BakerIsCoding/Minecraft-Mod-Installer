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

import com.baker.Requests.RequestGet;
import static com.baker.View.AllPanels.apikey;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
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
    private String downloadLauncher;
    private String downloadFabric;
    private String domain;

    private String downloadPath;
    private JLabel speedLabel;
    private JLabel etaLabel;
    private ZipManager zipManager;
    private String destDirectory;
    private JButton finalButton;
    private JLabel informativeLabel;
    private RequestGet rget;

    private boolean removeZip;
    private boolean intallMods;
    private boolean canDownloadSklauncher;
    private boolean canDownloadFabric;

    public DownloadWorker(String downloadMods, String downloadShader, String downloadConfig, String downloadHorizont, String downloadPath, JLabel speedLabel, JLabel etaLabel, ZipManager zipManager, String destDirectory, JButton finalButton, JLabel informativeLabel, String domain) {
        this.downloadMods = downloadMods;
        this.downloadShader = downloadShader;
        this.downloadConfig = downloadConfig;
        this.downloadHorizont = downloadHorizont;
        this.downloadPath = downloadPath;
        this.downloadLauncher = "https://skmedix.pl/binaries/skl/3.2.8/SKlauncher-3.2.8.jar";
        this.downloadFabric = "https://maven.fabricmc.net/net/fabricmc/fabric-installer/1.0.1/fabric-installer-1.0.1.jar";
       
        this.speedLabel = speedLabel;
        this.etaLabel = etaLabel;
        this.zipManager = zipManager;
        this.destDirectory = destDirectory;
        this.finalButton = finalButton;
        this.informativeLabel = informativeLabel;
        this.domain = domain;
        this.rget = new RequestGet();
        
        this.removeZip = false;
        this.intallMods = false;
        this.canDownloadSklauncher = false;
        this.canDownloadFabric = false;
    }

    @Override
    protected Void doInBackground() throws Exception {

        try {
            long totalSize;
            
            Map<String, String> parametersSize = Map.of(
                "apikey", apikey,
                "getfilesize", "pleasegivemethefilesize"
            );
            
            if(canDownloadSklauncher){
                informativeLabel.setText("Descargando Laucher");
                totalSize = 2;
                downloadFiles(downloadLauncher, "Sklaucher.jar",totalSize);
                copyLauncher();
                
            }
            if(canDownloadFabric){
                informativeLabel.setText("Descargando Fabric");
                totalSize = 2;
                downloadFiles(downloadFabric, "Fabric.jar",totalSize);
                executeFabric();
            }

            if (downloadMods != null) {
                informativeLabel.setText("Descargando Mods");
                totalSize = rget.getFileSize(domain + "/api/minecraft/getmods.php", parametersSize);
                downloadFiles(downloadMods, "mods.zip",totalSize);
                if (intallMods) {
                    installMods();
                }
            }

            if (downloadShader != null) {
                informativeLabel.setText("Descargando Shaders");
                totalSize = rget.getFileSize(domain + "/api/minecraft/getshaders.php", parametersSize);
                downloadFiles(downloadShader, "shaders.zip",totalSize);
                installShaders();
            }

            if (downloadConfig != null) {
                informativeLabel.setText("Descargando Configuraciones");
                totalSize = rget.getFileSize(domain + "/api/minecraft/getconfigs.php", parametersSize);
                downloadFiles(downloadConfig, "Config.zip",totalSize);
                intallConfig();
            }

            if (downloadHorizont != null) {
                informativeLabel.setText("Descargando Horizon");
                totalSize = rget.getFileSize(domain + "/api/minecraft/getdistanthorizons.php", parametersSize);
                downloadFiles(downloadHorizont, "Horizon.zip",totalSize);
                installHorizont();
            }

            informativeLabel.setText("Descargas completadas");
            new Popups().successPopup("Descargas", "Se ha descargado e instalado correctamente");

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
                new File(downloadPath + "mods.zip").delete();
                System.out.println("mods Zip Deleted");
            }

        } else {
            System.out.println("No es un directorio válido.");
        }

    }

    private void installShaders() {

        File tempDirectori = new File(downloadPath), mineDirectori = new File(destDirectory);
        if (tempDirectori.exists() && mineDirectori.exists()) {
            File shaders = new File(downloadPath + "shaders.zip");
            File directShaders = new File(destDirectory + "shaderpacks");

            File destino = new File(directShaders, "shaders.zip");

            try {
                Files.copy(shaders.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo copiado a: " + destino.getPath());

                if (removeZip) {
                    shaders.delete();
                    System.out.println("shaders Zip Deleted");
                }

            } catch (IOException e) {
                System.out.println("No se pudo copiar el archivo.");
            }

        } else {
            System.out.println("El directorio temporal o minecraft no existe");
        }
    }

    private void intallConfig() {

        File configFile = new File(downloadPath + "Config.zip");

        if (configFile.exists()) {
            zipManager.unzip(downloadPath + "Config.zip", destDirectory);
            System.out.println("Unzip Config Succes");
        } else {
            System.out.println("File Config.zip not exist");
        }

        if (removeZip) {
            new File(downloadPath + "Config.zip").delete();
            System.out.println("Config Zip Deleted");
        }

    }

    private void installHorizont() {

        File configFile = new File(downloadPath + "Horizon.zip");

        if (configFile.exists()) {
            zipManager.unzip(downloadPath + "Horizon.zip", destDirectory);
            System.out.println("Unzip Horizon Succes");
        } else {
            System.out.println("File Horizon.zip not exist");
        }

        if (removeZip) {
            new File(downloadPath + "Horizon.zip").delete();
            System.out.println("Horizon Zip Deleted");
        }

    }

    private void downloadFiles(String downloadUrl, String nameFile, long totalFileSize) throws MalformedURLException, ProtocolException, IOException, InterruptedException {

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
    
    private void copyLauncher() throws IOException{
        System.out.println("Entro");
        String launcherFilePath = downloadPath + "Sklaucher.jar";
        File launcherFile = new File(launcherFilePath);

        String desktopPath = System.getProperty("user.home") + File.separator+ "Desktop";
        File desktopDir = new File(desktopPath);
        
        System.out.println(launcherFile.getPath());
        System.out.println(desktopDir);
        
        if(launcherFile.exists()){
            File newFile = new File(desktopDir, launcherFile.getName());

            // Intentar mover el archivo usando renameTo
            if (launcherFile.renameTo(newFile)) {
                System.out.println("Archivo movido exitosamente a " + newFile.getAbsolutePath());
            } else {
                System.err.println("No se pudo mover el archivo a " + newFile.getAbsolutePath());
            }
        }

        System.out.println("Salgo");
    }
    
    private void executeFabric() throws IOException, InterruptedException{
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", downloadPath + "Fabric.jar");
        Process proces = pb.start();
        
        proces.waitFor();
               
        if(removeZip){
            new File(downloadPath + "Fabric.jar").delete();
            System.out.println("Frabric.jar deleted");
        }
        
        System.out.println("Fabric Installed");
    }
    
    public void setInstallMods(boolean x) {
        this.intallMods = x;
    }

    public void setRemovelZip(boolean x) {
        this.removeZip = x;
    }

    public void setCanDownloadSklauncher(boolean canDownloadSklauncher) {
        this.canDownloadSklauncher = canDownloadSklauncher;
    }

    public void setCanDownloadFabric(boolean canDownloadFabric) {
        this.canDownloadFabric = canDownloadFabric;
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
