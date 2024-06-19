/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.baker.View;

import com.baker.Requests.RequestGet;
import com.baker.simpleExceptions.SimpleException;
import com.baker.utils.DownloadWorker;
import com.baker.utils.HardwareInfoGetter;
import com.baker.utils.Popups;
import com.baker.utils.ZipManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author Baker
 */
public class AllPanels extends javax.swing.JPanel {

    private int mouseX, mouseY;
    private RequestGet rget = new RequestGet();
    //Web
    public static final String domain = "https://btools.me";
    public static final String apikey = "API-EpVsPsKvqYhhcGKEeSNnWrZ1N5loZWlVK9iuumEP6wYsFBUUq6Ql";
    //Local
    //public static final String domain = "http://127.0.0.1";
    //public static String apikey = "API-gDjlVTn76N2ZpbaE8yuoVSgoOwGnXCHJJa7vMQOp";
    Popups popup = new Popups();
    HardwareInfoGetter hardware = new HardwareInfoGetter();

    /**
     * Creates new form UserInfoPanel
     *
     * @param allUserData
     * @param authToken
     * @throws com.baker.simpleExceptions.SimpleException
     */
    public AllPanels() {

        initComponents();
        setDefaultInfo();
        Popups popup = new Popups();

        //START VARIABLES
        statesVariables();

        //Set Text Area
        // Set size and location
        setSize(709, 353);
        setLocation(0, 0);

        try {
            userStatsPanel();
        } catch (SimpleException e) {
            System.out.println("error " + e.getMessage());
        }

        checkboxOpcionesAvanzadas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Verificar si el checkbox está seleccionado
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    changeButtonState(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    changeButtonState(false);
                }
            }
        });

        System.out.println("Started!");
        popup.youtubePopup("Recuerda!", "Recuerda que puedes ver un tutorial para saber como instalar los mods.\nPresiona el botón \"Ver Tutorial\" para verlo.", "https://www.youtube.com/watch?v=Jln_PDI6uts");

    }

    public void statesVariables() {
        checkboxDecompress.setSelected(true);
        checkboxDecompress.setEnabled(false);
        checkboxInstallMods.setSelected(true);
        checkboxInstallMods.setEnabled(false);
        checkboxInstallShaders.setSelected(true);
        checkboxInstallFancy.setSelected(true);
        checkboxInstallFancy.setEnabled(false);
        checkboxInstallDistantHorizons.setEnabled(true);
        checkboxInstallDistantHorizons.setSelected(true);

    }

    public void changeButtonState(Boolean state) {
        checkboxDecompress.setEnabled(state);
        checkboxInstallMods.setEnabled(state);
        checkboxInstallFancy.setEnabled(state);
    }

    public void updateSpeed(double speed) {
        SwingUtilities.invokeLater(() -> speedLabel.setText("Velocidad: " + speed + " MB/s"));
    }

    private void setDefaultInfo() {
        try {
            if (hardware.getOsType() == 0) {
                String appDataPath = System.getenv("APPDATA");
                String minecraftModsPath = appDataPath + File.separator + ".minecraft" + File.separator + "mods";

                File modsFolder = new File(minecraftModsPath);

                if (!modsFolder.exists()) {
                    // Intentar crear la carpeta si no existe
                    boolean wasCreated = modsFolder.mkdirs();
                    if (!wasCreated) {
                        popup.errorPopup("Error", "No se pudo crear la carpeta de mods.");
                    }
                }

                if (modsFolder.exists() && modsFolder.isDirectory()) {
                    //modRoute.setText(minecraftModsPath);
                    System.out.println("xd1");
                } else {
                    System.out.println("xd");
                    //modRoute.setText("");
                }
            }

            String currentPath = System.getProperty("user.dir");

            String downloadsPath = currentPath + File.separator + "downloaded";
            //downloadZipModRoute.setText(downloadsPath);
        } catch (Exception e) {
            popup.errorPopup("Error", e.getMessage());
        }

    }

    private void userStatsPanel() throws SimpleException {
        userInfoWriteOnLabels();

    }

    private void userInfoWriteOnLabels() throws SimpleException {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modsPanel = new javax.swing.JPanel();
        downloadButton = new javax.swing.JButton();
        titleMods = new javax.swing.JLabel();
        checkboxInstallMods = new javax.swing.JCheckBox();
        separator1 = new javax.swing.JSeparator();
        checkboxDecompress = new javax.swing.JCheckBox();
        separator2 = new javax.swing.JSeparator();
        titleDistantHorizons = new javax.swing.JLabel();
        separator4 = new javax.swing.JSeparator();
        titleConfigs = new javax.swing.JLabel();
        separator5 = new javax.swing.JSeparator();
        titleFancymenu = new javax.swing.JLabel();
        checkboxInstallFancy = new javax.swing.JCheckBox();
        titleAll = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        etaLabel = new javax.swing.JLabel();
        separator7 = new javax.swing.JSeparator();
        titleVoiceChat = new javax.swing.JLabel();
        separator8 = new javax.swing.JSeparator();
        titleShaders = new javax.swing.JLabel();
        separator9 = new javax.swing.JSeparator();
        checkboxInstallShaders = new javax.swing.JCheckBox();
        textfieldKeybind = new javax.swing.JTextField();
        buttonChangeKeybind = new javax.swing.JButton();
        subtitleTecla = new javax.swing.JLabel();
        checkboxOpcionesAvanzadas = new javax.swing.JCheckBox();
        titleShaders1 = new javax.swing.JLabel();
        separator11 = new javax.swing.JSeparator();
        descargaLabel = new javax.swing.JLabel();
        checkboxInstallDistantHorizons = new javax.swing.JCheckBox();

        downloadButton.setText("Iniciar Descargas");
        downloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                downloadButtonMousePressed(evt);
            }
        });

        titleMods.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        titleMods.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleMods.setText("Mods");

        checkboxInstallMods.setText("Descargar Mods");
        checkboxInstallMods.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxInstallModsItemStateChanged(evt);
            }
        });

        separator1.setForeground(new java.awt.Color(155, 216, 184));

        checkboxDecompress.setText("Instalar Mods");

        separator2.setForeground(new java.awt.Color(155, 216, 184));

        titleDistantHorizons.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titleDistantHorizons.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleDistantHorizons.setText("Distant Horizons");

        separator4.setForeground(new java.awt.Color(155, 216, 184));

        titleConfigs.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        titleConfigs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleConfigs.setText("Configs");

        separator5.setForeground(new java.awt.Color(155, 216, 184));

        titleFancymenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titleFancymenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleFancymenu.setText("FancyMenu");

        checkboxInstallFancy.setText("Instalar Menu");
        checkboxInstallFancy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxInstallFancyItemStateChanged(evt);
            }
        });

        titleAll.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        titleAll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleAll.setText("Mod Downloader");

        speedLabel.setText("SPEED (ELMINAR TEXTO)");

        etaLabel.setText("ETA (ELIMINAR TEXTO)");

        separator7.setForeground(new java.awt.Color(155, 216, 184));

        titleVoiceChat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titleVoiceChat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleVoiceChat.setText("Voice Chat");

        separator8.setForeground(new java.awt.Color(155, 216, 184));

        titleShaders.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titleShaders.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleShaders.setText("Shaders");

        separator9.setForeground(new java.awt.Color(155, 216, 184));

        checkboxInstallShaders.setText("Instalar Shaders");
        checkboxInstallShaders.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxInstallShadersItemStateChanged(evt);
            }
        });

        textfieldKeybind.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textfieldKeybind.setText("Ninguna tecla asignada");

        buttonChangeKeybind.setText("Cambiar");

        subtitleTecla.setText("Tecla:");

        checkboxOpcionesAvanzadas.setText("Habilitar");
        checkboxOpcionesAvanzadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxOpcionesAvanzadasActionPerformed(evt);
            }
        });

        titleShaders1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titleShaders1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleShaders1.setText("Opciones Avanzadas");

        separator11.setForeground(new java.awt.Color(155, 216, 184));

        descargaLabel.setText("Tamaño estimado: 0");

        checkboxInstallDistantHorizons.setText("Descargar e instalar");
        checkboxInstallDistantHorizons.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxInstallDistantHorizonsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout modsPanelLayout = new javax.swing.GroupLayout(modsPanel);
        modsPanel.setLayout(modsPanelLayout);
        modsPanelLayout.setHorizontalGroup(
            modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modsPanelLayout.createSequentialGroup()
                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(titleAll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separator1, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modsPanelLayout.createSequentialGroup()
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addComponent(titleMods, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 6, Short.MAX_VALUE))
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(titleShaders1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(modsPanelLayout.createSequentialGroup()
                                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(checkboxOpcionesAvanzadas, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                                    .addComponent(separator11))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(checkboxDecompress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(separator2)
                                            .addComponent(checkboxInstallMods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18))
                            .addGroup(modsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addComponent(checkboxInstallShaders)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(titleShaders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(separator9))
                                .addGap(18, 18, 18)))
                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleConfigs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separator4)
                            .addGroup(modsPanelLayout.createSequentialGroup()
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addComponent(checkboxInstallDistantHorizons, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkboxInstallFancy))
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(separator5, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(titleDistantHorizons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(titleFancymenu, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                            .addComponent(separator7))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(subtitleTecla)
                                    .addComponent(separator8)
                                    .addComponent(titleVoiceChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textfieldKeybind, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addGroup(modsPanelLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(buttonChangeKeybind, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(modsPanelLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(descargaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(downloadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44))
        );
        modsPanelLayout.setVerticalGroup(
            modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleAll)
                .addGap(0, 0, 0)
                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleMods)
                    .addComponent(titleConfigs))
                .addGap(0, 0, 0)
                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addComponent(separator4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(modsPanelLayout.createSequentialGroup()
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(titleDistantHorizons)
                                    .addComponent(titleFancymenu)
                                    .addComponent(titleVoiceChat))
                                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(separator5, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(separator7, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(separator8, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(checkboxDecompress)
                        .addGap(0, 0, 0)
                        .addComponent(checkboxInstallMods)))
                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkboxInstallFancy)
                        .addComponent(checkboxInstallDistantHorizons))
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addComponent(subtitleTecla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfieldKeybind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChangeKeybind, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(titleShaders)
                        .addGap(0, 0, 0)
                        .addComponent(separator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(checkboxInstallShaders)))
                .addGap(14, 14, 14)
                .addGroup(modsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modsPanelLayout.createSequentialGroup()
                        .addComponent(titleShaders1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(separator11, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkboxOpcionesAvanzadas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(descargaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(speedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etaLabel)
                        .addGap(6, 6, 6)))
                .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(modsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(modsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void checkboxOpcionesAvanzadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxOpcionesAvanzadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxOpcionesAvanzadasActionPerformed

    private void downloadButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadButtonMousePressed

        downloadButton.setEnabled(false);

        Popups popup = new Popups();
        ZipManager zipmanager = new ZipManager();
        String appDataPath = System.getenv("APPDATA");

        System.out.println(appDataPath);

        String zipFilePath = appDataPath + File.separator + ".minecraft-mod-installer" + File.separator + "temp" + File.separator;
        String destDirectory = appDataPath + File.separator + ".minecraft" + File.separator;

        Map<String, String> parametersApi = Map.of(
                "apikey", apikey
        );

        Map<String, String> parametersSize = Map.of(
                "apikey", apikey,
                "getfilesize", "pleasegivemethefilesize"
        );

        Map<String, String> parametersDownload = Map.of(
                "apikey", apikey,
                "link", "pleasegivemethedownloadlink"
        );

        Long totalSize = rget.getFileSize(domain + "/api/minecraft/getmods.php", parametersSize);

        String modsUrl = null, shaderUrl = null, configUrl = null, horizonsUrl = null;

        if (checkboxInstallMods.isSelected()) {
            modsUrl = rget.getDownloadLink(domain + "/api/minecraft/getmods.php", parametersDownload);
        }

        if (checkboxInstallShaders.isSelected()) {
            shaderUrl = rget.getDownloadLink(domain + "/api/minecraft/getshaders.php", parametersDownload);
        }

        if (checkboxInstallDistantHorizons.isSelected()) {
            horizonsUrl = rget.getDownloadLink(domain + "/api/minecraft/getdistanthorizons.php", parametersDownload);
        }
        
        if(checkboxInstallFancy.isSelected()){
            configUrl = rget.getDownloadLink(domain + "/api/minecraft/getconfigs.php", parametersDownload);
        }
        

        DownloadWorker worker = new DownloadWorker(
                modsUrl,
                shaderUrl,
                configUrl,
                horizonsUrl,
                zipFilePath,
                speedLabel,
                etaLabel,
                totalSize,
                zipmanager,
                destDirectory,
                downloadButton,
                descargaLabel
        );

        worker.setInstallMods(checkboxInstallMods.isSelected());
        worker.setRemovelZip(false);
        worker.execute();

    }//GEN-LAST:event_downloadButtonMousePressed

    private void checkboxInstallModsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkboxInstallModsItemStateChanged
        actualizeTamanyo();
    }//GEN-LAST:event_checkboxInstallModsItemStateChanged

    private void checkboxInstallShadersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkboxInstallShadersItemStateChanged
        actualizeTamanyo();
    }//GEN-LAST:event_checkboxInstallShadersItemStateChanged

    private void checkboxInstallFancyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkboxInstallFancyItemStateChanged
        actualizeTamanyo();
    }//GEN-LAST:event_checkboxInstallFancyItemStateChanged

    private void checkboxInstallDistantHorizonsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkboxInstallDistantHorizonsItemStateChanged
        actualizeTamanyo();
    }//GEN-LAST:event_checkboxInstallDistantHorizonsItemStateChanged
    public void startDownload(String fileUrl) {
        new Thread(() -> {
            try {
                // Simulación de la descarga de un archivo
                long startTime = System.currentTimeMillis();
                long downloadedBytes = 0;
                long totalBytes = 100000000; // 100 MB ejemplo

                while (downloadedBytes < totalBytes) {
                    // Simular la descarga de datos
                    downloadedBytes += 1000000; // simula la descarga de 1 MB
                    long currentTime = System.currentTimeMillis();
                    long timeElapsed = currentTime - startTime; // tiempo en milisegundos
                    double speed = (downloadedBytes / 1024.0 / 1024.0) / (timeElapsed / 1000.0); // MB/s

                    updateSpeed(speed);
                    Thread.sleep(1000); // espera 1 segundo antes de la próxima actualización
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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

    public void enableDowloadButton() {
        downloadButton.setEnabled(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonChangeKeybind;
    private javax.swing.JCheckBox checkboxDecompress;
    private javax.swing.JCheckBox checkboxInstallDistantHorizons;
    private javax.swing.JCheckBox checkboxInstallFancy;
    private javax.swing.JCheckBox checkboxInstallMods;
    private javax.swing.JCheckBox checkboxInstallShaders;
    private javax.swing.JCheckBox checkboxOpcionesAvanzadas;
    private javax.swing.JLabel descargaLabel;
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel etaLabel;
    private javax.swing.JPanel modsPanel;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator11;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator4;
    private javax.swing.JSeparator separator5;
    private javax.swing.JSeparator separator7;
    private javax.swing.JSeparator separator8;
    private javax.swing.JSeparator separator9;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JLabel subtitleTecla;
    private javax.swing.JTextField textfieldKeybind;
    private javax.swing.JLabel titleAll;
    private javax.swing.JLabel titleConfigs;
    private javax.swing.JLabel titleDistantHorizons;
    private javax.swing.JLabel titleFancymenu;
    private javax.swing.JLabel titleMods;
    private javax.swing.JLabel titleShaders;
    private javax.swing.JLabel titleShaders1;
    private javax.swing.JLabel titleVoiceChat;
    // End of variables declaration//GEN-END:variables
}
