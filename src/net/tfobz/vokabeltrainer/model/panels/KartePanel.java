package net.tfobz.vokabeltrainer.model.panels;

import javax.swing.*;
import java.awt.*;

public class KartePanel extends javax.swing.JPanel {

    public KartePanel() {
        setLayout(new BorderLayout());

        // Placeholder content
        JLabel label = new JLabel("Karten Panel", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
