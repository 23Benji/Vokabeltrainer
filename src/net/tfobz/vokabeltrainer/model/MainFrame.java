package net.tfobz.vokabeltrainer.model;

import net.tfobz.vokabeltrainer.model.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private int lernKarteiNummer = 1;
    private int fachNummer = 0;
    private boolean richtung = false;
    private boolean grossKleinschreibung;

    // Constructor
    public MainFrame() {
        setTitle("Vokabeltrainer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMainPanel();
        setVisible(true);
    }

    // Initialize main panel with CardLayout
    private void initMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        // Initially show the Home Panel
        switchToHomePanel();
    }

    // Methods to switch between panels - creating a new instance each time
    public void switchToHomePanel() {
        mainPanel.removeAll();
        mainPanel.add(new HomePanel(this), "Home");
        cardLayout.show(mainPanel, "Home");
        revalidate();
        repaint();
    }

    public void switchToFachPanel() {
        mainPanel.removeAll();
        mainPanel.add(new FachPanel(this), "Fach");
        cardLayout.show(mainPanel, "Fach");
        revalidate();
        repaint();
    }

    public void switchToSettingPanel() {
        mainPanel.removeAll();
        mainPanel.add(new SettingPanel(this), "Setting");
        cardLayout.show(mainPanel, "Setting");
        revalidate();
        repaint();
    }

    public void switchToQuizPanel() {
        mainPanel.removeAll();
        mainPanel.add(new QuizPanel(this), "Quiz");
        cardLayout.show(mainPanel, "Quiz");
        revalidate();
        repaint();
    }
    public void switchToModifyPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ModifyPanel(this), "Modify");
        cardLayout.show(mainPanel, "Modify");
        revalidate();
        repaint();
    }

    // Getter and Setter for LernKarteiNummer
    public void setLernKarteiNummer(int lernKarteiNummer) {
        this.lernKarteiNummer = lernKarteiNummer;
    }

    public int getLernKarteiNummer() {
        return lernKarteiNummer;
    }

    // Getter and Setter for FachNummer
    public void setFachNummer(int fachNummer) {
        this.fachNummer = fachNummer;
    }

    public int getFachNummer() {
        return fachNummer;
    }

    public void setGrossKleinschreibung(boolean grossKleinschreibung) {
        this.grossKleinschreibung = grossKleinschreibung;
    }

    public boolean isGrossKleinschreibung() {
        return grossKleinschreibung;
    }
}
