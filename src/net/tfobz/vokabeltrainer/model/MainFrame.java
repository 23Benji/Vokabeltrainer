package net.tfobz.vokabeltrainer.model;

import net.tfobz.vokabeltrainer.model.panels.FachPanel;
import net.tfobz.vokabeltrainer.model.panels.HomePanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private int LernKarteiNummer;

    public MainFrame() {
        setTitle("Vokabeltrainer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        initMainPanel();

        // Show frame
        setVisible(true);
    }

    private void initMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to the CardLayout
        mainPanel.add(new HomePanel(this), "Home");
        mainPanel.add(new FachPanel(this,LernKarteiNummer), "Fach");

        add(mainPanel, BorderLayout.CENTER);
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    public void switchToHomePanel() {
        cardLayout.show(mainPanel, "Home");
    }
    public void switchToFachPanel(int LernKarteiNummer) {
        this.LernKarteiNummer = LernKarteiNummer;
        cardLayout.show(mainPanel, "Fach");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
