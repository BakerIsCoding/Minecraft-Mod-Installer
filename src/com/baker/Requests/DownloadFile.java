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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Baker
 */
public class DownloadFile {

    private static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        try ( InputStream in = url.openStream();  FileOutputStream fos = new FileOutputStream(saveDir + File.separator + "mods.zip")) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        }
    }
}
