 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.baker;

import com.baker.View.LoaderScreen;
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
            new LoaderScreen();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
