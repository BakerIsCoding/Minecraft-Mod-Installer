/*
 * Copyright (C) 2024 Baker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.baker.Requests;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipFile;

/**
 *
 * @author Baker
 */
public class DownloadFile {

    private String urlDownload = "http://192.168.1.149:5000/download/mods";
    private String folder = "./downloaded/";
    private String fullZipFilePath = folder + "mods.zip";

    public boolean downloadFile() throws IOException {
        Boolean done = false;
        
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        URL url = new URL(urlDownload);
        URLConnection conn = url.openConnection();
        try (InputStream inputStream = conn.getInputStream(); FileOutputStream outputStream = new FileOutputStream(fullZipFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            done = true;
        }
        return done;
    }

    public void unzip(String pasteDir) throws IOException {
        try (ZipFile zipFile = new ZipFile(fullZipFilePath)) {
            zipFile.stream().forEach(entry -> {
                try {
                    String filePath = pasteDir + File.separator + entry.getName();
                    if (entry.isDirectory()) {
                        Files.createDirectories(Paths.get(filePath));
                    } else {
                        try (InputStream is = zipFile.getInputStream(entry)) {
                            Files.copy(is, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    public void deleteOldMods(String modsDirPath) {
        // Ruta a la carpeta de mods de Minecraft
        modsDirPath = "./minecraft/mods";

        File modsDir = new File(modsDirPath);

        if (modsDir.exists() && modsDir.isDirectory()) {
            File[] files = modsDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isFile();
                }
            });

            // Elimina cada archivo encontrado
            for (File file : files) {
                if (!file.delete()) {
                    // Maneja el caso donde el archivo no pudo ser eliminado
                    System.out.println("No se pudo eliminar el mod: " + file.getName());
                }
            }
        } else {
            System.out.println("El directorio de mods no existe o no es un directorio.");
        }
    }

}
