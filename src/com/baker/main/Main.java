 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.baker.main;

import com.baker.interfaces.FirstScreen;
//import com.baker.firstInterface.AllPanels;
//import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
//import javax.swing.UIManager;

/**
 *
 * @author Baker
 */
public class Main {

    public static void main(String[] args) {
        try {
            //Try to start the look and feel
            try {
                FlatMacDarkLaf.setup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //Starting the initial screen
            FirstScreen initialScreen = new FirstScreen();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
