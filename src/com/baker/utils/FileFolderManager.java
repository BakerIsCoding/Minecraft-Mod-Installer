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
package com.baker.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.swing.Popup;

/**
 *
 * @author Baker
 */
public class FileFolderManager {

    public void createDirectories() {
        // Obtiene la ruta al directorio AppData
        String appDataPath = System.getenv("APPDATA");

        // Construye la ruta al directorio .minecraft-mod-installer dentro de AppData
        String modInstallerPath = appDataPath + File.separator + ".minecraft-mod-installer";

        // Construye la ruta a la carpeta temp dentro de .minecraft-mod-installer
        String credsPath = modInstallerPath + File.separator + "creds";
        String credentialsEncryptedPath = credsPath + File.separator + "creds.encrypted";
        String tempPath = modInstallerPath + File.separator + "temp";

        // Crea los directorios si no existen
        new File(tempPath).mkdirs();
        new File(credsPath).mkdirs();

        // Intenta crear el archivo creds.encrypted
        try {
            new File(credentialsEncryptedPath).createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear el archivo creds.encrypted: " + e.getMessage());
        }

        System.out.println("Directorios y archivo creds.encrypted creados en: " + credsPath);
    }

    public Boolean writeCredentials(String username, String password) {
        HashingManager hasherm = new HashingManager();
        Popups popup = new Popups();
        String appDataPath = System.getenv("APPDATA");
        String modInstallerPath = appDataPath + File.separator + ".minecraft-mod-installer";
        String credsPath = modInstallerPath + File.separator + "creds";
        String credentialsEncryptedPath = credsPath + File.separator + "creds.encrypted";
        String hashedUser = hasherm.encrypt(username);
        String hashedPassword = hasherm.encrypt(password);

        if (hashedUser == null || hashedPassword == null) {
            popup.errorPopup("Error", "Something went wrong, trying to encrypt Username or password");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsEncryptedPath))) {
            writer.write(hashedUser + ":" + hashedPassword);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public Boolean isCredsFileExisting(){
        String appDataPath = System.getenv("APPDATA");
        String modInstallerPath = appDataPath + File.separator + ".minecraft-mod-installer";
        String credsPath = modInstallerPath + File.separator + "creds";
        String credentialsEncryptedPath = credsPath + File.separator + "creds.encrypted";
        
        File credsEncryptedFile = new File(credentialsEncryptedPath);
        
        boolean exists = credsEncryptedFile.exists();
       
        return exists;
    }
    
    public String[] readCredFile() {
        String appDataPath = System.getenv("APPDATA");
        String modInstallerPath = appDataPath + File.separator + ".minecraft-mod-installer";
        String credsPath = modInstallerPath + File.separator + "creds";
        String credentialsEncryptedPath = credsPath + File.separator + "creds.encrypted";
        
        // Intenta leer el archivo creds.encrypted
        try (BufferedReader reader = new BufferedReader(new FileReader(credentialsEncryptedPath))) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(":");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo creds.encrypted: " + e.getMessage());
        }
        
        return null;
    }

}
