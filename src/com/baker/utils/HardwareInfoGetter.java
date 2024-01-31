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
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Baker
 */
public class HardwareInfoGetter {

    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();

        // Información del sistema (ya implementado en la respuesta anterior)
        info.append("------ INFORMACIÓN BÁSICA ------ \n");
        info.append("Sistema Operativo: ").append(System.getProperty("os.name")).append("\n");
        info.append("Versión del Sistema Operativo: ").append(System.getProperty("os.version")).append("\n");
        info.append("Arquitectura del Sistema: ").append(System.getProperty("os.arch")).append("\n");
        info.append("User Home Directory: ").append(System.getProperty("user.home")).append("\n");
        info.append("User Working Directory: ").append(System.getProperty("user.dir")).append("\n\n");

        // Obtener información sobre la RAM
        info.append("------ INFORMACIÓN JAVA ------ \n");
        info.append("Java Vendor: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Java VM Vendor: ").append(System.getProperty("java.vm.vendor")).append("\n");
        info.append("Java VM Version: ").append(System.getProperty("java.vm.version")).append("\n\n");

        info.append("------ INFORMACIÓN HARDWARE ------ \n");

        if (systemType().equals("win")) {
            info.append("Información sobre la RAM:\n");
            info.append(execCommand("wmic memorychip get capacity")).append("\n");  
            
            // Obtener información sobre la tarjeta gráfica
            info.append("Información sobre la Tarjeta Gráfica:\n");
            info.append(execCommand("wmic path win32_videocontroller get caption")).append("\n"); 

            // Obtener información sobre el procesador
            info.append("Información sobre el Procesador:\n");
            info.append(execCommand("wmic cpu get caption")).append("\n");  
            
        } else if (systemType().equals("linux")) {
            info.append("Información sobre la RAM:\n");
            info.append(execCommand("free -h")).append("\n");  

            // Obtener información sobre la tarjeta gráfica
            info.append("Información sobre la Tarjeta Gráfica:\n");
            info.append(execCommand("lspci | grep VGA")).append("\n");  

            // Obtener información sobre el procesador
            info.append("Información sobre el Procesador:\n");
            info.append(execCommand("lscpu")).append("\n"); 
        } else {
            info.append("Ha ocurrido un error al verificar tu sistema operativo");
        }

        return info.toString();
    }

    public void printHardwareInfo() {
        try {
            Process process = Runtime.getRuntime().exec("lshw"); // Comando para obtener información del hardware en sistemas basados en Unix
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String systemType() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return "win";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return "linux";
        } else {
            return "unknown";
        }
    }

    private static String execCommand(String command) {
        StringBuilder result = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            result.append("Error al ejecutar el comando: ").append(command);
        }

        return result.toString();
    }
}
