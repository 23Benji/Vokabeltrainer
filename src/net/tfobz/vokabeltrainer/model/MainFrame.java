package net.tfobz.vokabeltrainer.model;

import net.tfobz.vokabeltrainer.model.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Lernkartei currentLernkartei;
    private int FachNummer;
    private int currentLernkarteiIndex;  // To track the index of the current Lernkartei
    private String[] languageLabels; // Array for preloaded labels
    private List<Lernkartei> lernkarteien; // List of Lernkartei objects
    private boolean richtung = false;
    private boolean grossKleinschreibung;

    // Constructor
    public MainFrame() {
        setTitle("Vokabeltrainer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        preloadLanguageLabels();

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

    private void preloadLanguageLabels() {
        System.out.println("preloadLanguageLabels");
        lernkarteien = VokabeltrainerDB.getLernkarteien();
        System.out.println(lernkarteien.toString());

        languageLabels = new String[lernkarteien.size()];

        for (int i = 0; i < lernkarteien.size(); i++) {
            Lernkartei kartei = lernkarteien.get(i);
            if (kartei == null || kartei.getBeschreibung() == null) {
                System.out.println("Null entry detected");
                languageLabels[i] = "null";
            } else {
                languageLabels[i] = kartei.toString();
            }
        }

        // Set initial index to 0
        currentLernkarteiIndex = 0;
        currentLernkartei = lernkarteien.get(currentLernkarteiIndex);
    }

    // Methods to switch between panels
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

    // Getter and Setter for Lernkartei
    public void setCurrentLernkartei(Lernkartei lernkartei) {
        this.currentLernkartei = lernkartei;
    }

    public Lernkartei getCurrentLernkartei() {
        return currentLernkartei;
    }

    // Getter and Setter for the Lernkartei Index
    public void setCurrentLernkarteiIndex(int index) {
        this.currentLernkarteiIndex = index;
        this.currentLernkartei = lernkarteien.get(index); // Update the current Lernkartei
    }

    public int getCurrentLernkarteiIndex() {
        return currentLernkarteiIndex;
    }

    public List<Lernkartei> getLernkarteien() {
        return lernkarteien;
    }

    public String[] getLanguageLabels() {
        return languageLabels;
    }

    // Methods for navigating between Lernkarteien
    public void moveToNextLernkartei() {
        currentLernkarteiIndex = (currentLernkarteiIndex + 1) % lernkarteien.size();
        currentLernkartei = lernkarteien.get(currentLernkarteiIndex);
    }

    public void moveToPreviousLernkartei() {
        currentLernkarteiIndex = (currentLernkarteiIndex - 1 + lernkarteien.size()) % lernkarteien.size();
        currentLernkartei = lernkarteien.get(currentLernkarteiIndex);
    }

    // Gross Kleinschreibung
    public void setGrossKleinschreibung(boolean grossKleinschreibung) {
        this.grossKleinschreibung = grossKleinschreibung;
    }

    public boolean isGrossKleinschreibung() {
        return grossKleinschreibung;
    }

    public int getFachNummer() {
        return FachNummer;
    }

    public void setFachNummer(int fachNummer) {
        FachNummer = fachNummer;
    }

}
