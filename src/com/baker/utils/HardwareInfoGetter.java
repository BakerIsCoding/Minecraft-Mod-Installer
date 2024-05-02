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
        //Sistema Operativo
        info.append("OS NAME: ").append(System.getProperty("os.name")).append("\n");
        //Arquitectura del Sistema
        info.append("ARCH: ").append(System.getProperty("os.arch")).append("\n");
        //User Home Directory
        info.append("HOME DIR: ").append(System.getProperty("user.home")).append("\n");

        // Obtener información sobre la RAM
        info.append("JAVA\n---------\n");
        info.append("VENDOR: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("VERSION: ").append(System.getProperty("java.version")).append("\n");
        info.append("VM VENDOR: ").append(System.getProperty("java.vm.vendor")).append("\n");
        info.append("VM VERSION: ").append(System.getProperty("java.vm.version")).append("\n\n");

        return info.toString();
    }

    public Integer getOsType() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return 0;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return 1;
        } else {
            return null;
        }
    }

}
