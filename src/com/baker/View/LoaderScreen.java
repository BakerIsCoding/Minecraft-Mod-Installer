/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.baker.View;

import com.baker.Requests.RequestGet;
import com.baker.simpleExceptions.SimpleException;
import com.baker.utils.HardwareInfoGetter;
import com.baker.utils.Popups;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class LoaderScreen extends javax.swing.JFrame {

    /**
     * Creates new form FirstScreen
     */
    private int mouseX, mouseY;

    public LoaderScreen() {

        // Ocultar la barra de título
        setUndecorated(false);
        initComponents();
        setResizable(false);

        //MouseListener para el arrastre
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });

        // Pantalla en el medio
        setLocationRelativeTo(null);

        // Mostrar la pantalla después de ocultar la barra de título
        setVisible(true);

        startNextPanel();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        versionText = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        versionText1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("Minecraft Mod Installer");

        separator.setForeground(new java.awt.Color(155, 216, 184));

        versionText.setText("Ver: 1.0");

        infoLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel.setText("Starting ");

        versionText1.setText("Made by baker with ♥");

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
            .addGroup(contentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentLayout.createSequentialGroup()
                        .addComponent(versionText1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(versionText))
                    .addComponent(separator, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionText)
                    .addComponent(versionText1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startNextPanel() {
        Popups popup = new Popups();
        Integer timesTried = 0;
        Boolean startedOtherPanel = false;
        try {
            while (timesTried != 3) {
                timesTried += 1;
                RequestGet rget = new RequestGet();
                Integer checksPassed = 0;

                infoLabel.setText("Contactando con el servidor ...");
                Thread.sleep(100);
                infoLabel.setText("Comprobando el endpoint \"login\" ...");
                Map<String, String> parameters = Map.of(
                        "alive", "areyoualive?"
                );
                JSONObject response = rget.sendGetRequest("https://btools.me/api/minecraft/login.php", parameters);

                if (response != null) {
                    checksPassed += 1;
                }
                Thread.sleep(100);
                infoLabel.setText("Comprobando el endpoint \"getallusers\" ...");

                response = rget.sendGetRequest("https://btools.me/api/minecraft/getallusers.php", parameters);

                if (response != null) {
                    checksPassed += 1;
                }

                Thread.sleep(100);
                //GetMOds
                infoLabel.setText("Comprobando el endpoint \"getmods\" ...");

                response = rget.sendGetRequest("https://btools.me/api/minecraft/getmods.php", parameters);

                if (response != null) {
                    checksPassed += 1;
                }
                Thread.sleep(100);
                infoLabel.setText("Comprobando la disponibilidad de los mods ...");

                Thread.sleep(100);
                infoLabel.setText("Comprobando el endpoint \"getshaders\" ...");

                response = rget.sendGetRequest("https://btools.me/api/minecraft/getshaders.php", parameters);

                if (response != null) {
                    checksPassed += 1;
                }
                Thread.sleep(100);
                infoLabel.setText("Comprobando la disponibilidad de los shaders ...");

                Thread.sleep(100);
                infoLabel.setText("Comprobando el endpoint \"getconfigs\" ...");

                response = rget.sendGetRequest("https://btools.me/api/minecraft/getconfigs.php", parameters);

                if (response != null) {
                    checksPassed += 1;
                }
                Thread.sleep(100);
                infoLabel.setText("Comprobando la disponibilidad de las configuraciones ...");

                Thread.sleep(100);
                infoLabel.setText("Conexiones con éxito " + checksPassed + "/5 ...");
                Thread.sleep(2000);

                if (checksPassed == 5) {
                    startedOtherPanel = true;
                    Thread.sleep(100);
                    infoLabel.setText("Iniciando Mod Installer ...");
                    Thread.sleep(50);
                    timesTried = 3;
                    LoginScreen loginPanel = new LoginScreen(this);

                    // Panel Content Overwrite
                    content.removeAll();
                    content.add(loginPanel, BorderLayout.CENTER);
                    content.revalidate();
                    content.repaint();

                    // Size and location
                    setSize(265, 300);
                    setLocation(0, 0);
                    setLocationRelativeTo(null);
                    loginPanel.setVisible(true);
                } else {
                    infoLabel.setText("<html><div style='text-align: center;'>No se ha podido contactar con todos los endpoints,<br>Reintentando ...</div></html>");
                    Thread.sleep(1300);

                }
            }
            if (timesTried == 3 && !startedOtherPanel) {
                infoLabel.setText("<html><div style='text-align: center;'>Se ha alcanzado el máximo de reintentos<br>Cerrando aplicación ...<br>3</div></html>");
                Thread.sleep(1000);
                infoLabel.setText("<html><div style='text-align: center;'>Se ha alcanzado el máximo de reintentos<br>Cerrando aplicación ...<br>2</div></html>");
                Thread.sleep(1000);
                infoLabel.setText("<html><div style='text-align: center;'>Se ha alcanzado el máximo de reintentos<br>Cerrando aplicación ...<br>1</div></html>");
                Thread.sleep(1000);
                System.out.println("AppClosed, times tried 3");
                dispose();

            }

        } catch (InterruptedException e) {
            popup.errorPopup("Ha ocurrido un error", "Error al intentar parar el hilo, " + e.getMessage());
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private javax.swing.JPanel content;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel versionText;
    private javax.swing.JLabel versionText1;
    // End of variables declaration//GEN-END:variables
}
