/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.baker.View;

import com.baker.Requests.RequestGet;
import static com.baker.View.AllPanels.apikey;
import static com.baker.View.AllPanels.domain;
import com.baker.simpleExceptions.SimpleException;
import com.baker.utils.FileFolderManager;
import com.baker.utils.HardwareInfoGetter;
import com.baker.utils.HashingManager;
import com.baker.utils.Popups;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class LoginScreen extends javax.swing.JPanel {

    private int mouseX, mouseY;
    private JFrame parentFrame;
    Popups popup = new Popups();
    HardwareInfoGetter hardware = new HardwareInfoGetter();

    /**
     * Creates new form UserInfoPanel
     *
     * @param allUserData
     * @param authToken
     * @throws com.baker.simpleExceptions.SimpleException
     */
    public LoginScreen(JFrame frame) {
        HardwareInfoGetter hardware = new HardwareInfoGetter();
        FileFolderManager ffm = new FileFolderManager();
        HashingManager hashmanager = new HashingManager();
        this.parentFrame = frame;

        initComponents();
        chargeEventsCustom();

        Boolean credsFileExists = ffm.isCredsFileExisting();
        if (credsFileExists) {
            String[] encryptedCreds = ffm.readCredFile();
            if (encryptedCreds != null) {
                rememberCredentials.setSelected(true);
                String decryptedUsername = hashmanager.decrypt(encryptedCreds[0]);
                String decryptedPassword = hashmanager.decrypt(encryptedCreds[1]);

                usernameField.setText(decryptedUsername);
                passwordField.setText(decryptedPassword);
            }
        }
        //Set Text Area

        // Set size and location
        setSize(250, 380);
        setLocation(0, 0);

        try {
            userStatsPanel();
        } catch (SimpleException e) {
            System.out.println("error " + e.getMessage());
        }

        System.out.println("Started!");

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

        content = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        rememberCredentials = new javax.swing.JCheckBox();
        submitButton = new javax.swing.JButton();

        title.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Iniciar Sesión");

        separator.setForeground(new java.awt.Color(155, 216, 184));

        usernameLabel.setText("Nombre de usuario");

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordLabel.setText("Contraseña");

        rememberCredentials.setText("Recordar credenciales");

        submitButton.setText("Iniciar Sesión");
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rememberCredentials, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
                .addGap(36, 36, 36))
            .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contentLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(31, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentLayout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(rememberCredentials)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(title)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(197, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void submitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitButtonMouseClicked
        buttonAction();
    }//GEN-LAST:event_submitButtonMouseClicked

    private void chargeEventsCustom() {

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonAction();
                }
            }
        });

    }

    private void buttonAction() {
        Popups popup = new Popups();

        String username = usernameField.getText();
        String password = passwordField.getText();
        RequestGet requestGet = new RequestGet();

        Map<String, String> parameters = Map.of(
                "username", username,
                "password", password,
                "apikey", apikey
        );

        JSONObject response = requestGet.sendGetRequest(domain + "/api/minecraft/login.php", parameters);
        if (response != null) {
            if (!response.getBoolean("success")) {
                popup.errorPopup("Error al iniciar sesión", response.getString("message"));
            } else {
                FileFolderManager ffm = new FileFolderManager();
                ffm.createDirectories();

                if (rememberCredentials.isSelected()) {
                    ffm.writeCredentials(username, password);
                }

                AllPanels allpanels = new AllPanels();
                // Panel Content Overwrite
                JFrame allPanelsFrame = new JFrame("All Panels");
                
                allPanelsFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                allPanelsFrame.add(allpanels, BorderLayout.CENTER);
                allPanelsFrame.setSize(675, 400);
                allPanelsFrame.setResizable(false);
                allPanelsFrame.setLocationRelativeTo(null);
                allPanelsFrame.setVisible(true);

                this.parentFrame.dispose();
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JCheckBox rememberCredentials;
    private javax.swing.JSeparator separator;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel title;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
