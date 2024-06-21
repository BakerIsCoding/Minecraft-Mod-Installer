/*
 * The MIT License
 *
 * Copyright 2024 Javier.
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
import com.baker.View.AllPanels;
import static com.baker.View.AllPanels.apikey;
import static com.baker.View.AllPanels.domain;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 *
 * @author Javier
 */
public class TextUpdater implements Runnable{
    
    private AllPanels panel;
    private RequestGet rget;
    
    private JCheckBox checkboxInstallMods;
    private JCheckBox checkboxInstallShaders;
    private JCheckBox checkboxInstallDistantHorizons;
    private JCheckBox checkboxInstallFancy;
    
    private JLabel descargaLabel;
    
    
    public TextUpdater(AllPanels panel){
        this.panel = panel;
        
        this.checkboxInstallMods = panel.getCheckboxInstallMods();
        this.checkboxInstallShaders = panel.getCheckboxInstallShaders();
        this.checkboxInstallDistantHorizons = panel.getCheckboxInstallDistantHorizons();
        this.checkboxInstallFancy = panel.getCheckboxInstallFancy();
        
        this.descargaLabel = panel.getDescargaLabel();
        this.rget = panel.getRget();
        
    }
    
    @Override
    public void run() {
        actualizeTamanyo();
    }
    
    private void actualizeTamanyo() {
        long totalBytes = 0;

        Map<String, String> parametersSize = Map.of(
                "apikey", apikey,
                "getfilesize", "pleasegivemethefilesize"
        );

        if (checkboxInstallMods.isSelected()) {
            long byteTamaño = rget.getFileSize(domain + "/api/minecraft/getmods.php", parametersSize);
            totalBytes += byteTamaño;
        }

        if (checkboxInstallShaders.isSelected()) {
            long byteTamaño = rget.getFileSize(domain + "/api/minecraft/getshaders.php", parametersSize);
            totalBytes += byteTamaño;
        }

        if (checkboxInstallDistantHorizons.isSelected()) {
            long byteTamaño = rget.getFileSize(domain + "/api/minecraft/getdistanthorizons.php", parametersSize);
            totalBytes += byteTamaño;
        }

        if (checkboxInstallFancy.isSelected()) {
            long byteTamaño = rget.getFileSize(domain + "/api/minecraft/getconfigs.php", parametersSize);
            totalBytes += byteTamaño;
        }

        System.out.println("Total bytes: " + totalBytes);

        double totalMB = totalBytes / (1024.0 * 1024.0);

        if (totalMB > 1024) {
            double totalGB = totalMB / 1024.0;
            descargaLabel.setText("Tamaño estimado: " + Math.round(totalGB * 100.0) / 100.0 + " GB");
        } else {
            descargaLabel.setText("Tamaño estimado: " + Math.round(totalMB * 100.0) / 100.0 + " MB");
        }
    }
    
}
