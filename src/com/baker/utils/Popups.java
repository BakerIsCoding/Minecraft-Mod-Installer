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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Baker
 */
public class Popups {

    public void errorPopup(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void successPopup(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void youtubePopup(String title, String message, String url) {
        final JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);

        JDialog dialog = pane.createDialog(null, title);

        JButton button = new JButton("Ver Tutorial");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo abrir el enlace", "Error", JOptionPane.ERROR_MESSAGE);
                    dialog.dispose();
                }
            }
        });

        JButton buttonClose = new JButton("Cerrar");
        pane.setOptions(new Object[]{buttonClose});

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        pane.setOptions(new Object[]{button, buttonClose});
        dialog.setVisible(true);
    }

}
