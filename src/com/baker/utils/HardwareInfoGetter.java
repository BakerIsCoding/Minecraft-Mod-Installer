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

        info.append("Sistema Operativo: ").append(System.getProperty("os.name")).append("\n");
        info.append("Versión del Sistema Operativo: ").append(System.getProperty("os.version")).append("\n");
        info.append("Arquitectura del Sistema: ").append(System.getProperty("os.arch")).append("\n");
        info.append("Java Vendor: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Java VM Vendor: ").append(System.getProperty("java.vm.vendor")).append("\n");
        info.append("Java VM Version: ").append(System.getProperty("java.vm.version")).append("\n");
        info.append("User Home Directory: ").append(System.getProperty("user.home")).append("\n");
        info.append("User Working Directory: ").append(System.getProperty("user.dir")).append("\n");

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
}