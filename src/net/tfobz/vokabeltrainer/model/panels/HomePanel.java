package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.MainFrame;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private JLabel languageLabel;
    private JButton upButton, downButton, startButton, modifyButton, settingsButton;
    private Image backgroundImage;
    private MainFrame mainFrame;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Panel properties
        setLayout(null); // No layout manager
        backgroundImage = new ImageIcon("images/Bgd2.jpg").getImage();

        // Settings button
        settingsButton = new JButton(new ImageIcon("icons/settings.png"));
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setBounds(730, 10, 50, 50);
        settingsButton.addActionListener(e -> mainFrame.switchToSettingPanel());
        add(settingsButton);

        // Up arrow button
        upButton = new JButton(new ImageIcon("icons/up_arrow.png"));
        upButton.setBorderPainted(false);
        upButton.setFocusPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setBounds(213, 70, 64, 64);
        upButton.addActionListener(e -> moveUp());
        add(upButton);

        // Language label
        languageLabel = new JLabel(mainFrame.getLanguageLabels()[mainFrame.getCurrentLernkarteiIndex()], JLabel.CENTER);
        languageLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        languageLabel.setOpaque(true);
        languageLabel.setBackground(new Color(177, 194, 158));
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setBounds(50, 130, 400, 250);
        add(languageLabel);

        // Down arrow button
        downButton = new JButton(new ImageIcon("icons/down_arrow.png"));
        downButton.setBorderPainted(false);
        downButton.setFocusPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setBounds(213, 375, 64, 64);
        downButton.addActionListener(e -> moveDown());
        add(downButton);

        // Start button
        startButton = new JButton("Start");
        startButton.setBackground(new Color(255, 230, 169));
        startButton.setForeground(Color.black);
        startButton.setBounds(520, 180, 200, 55);
        startButton.addActionListener(e -> mainFrame.switchToFachPanel());
        add(startButton);

        // Modify button
        modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(255, 230, 169));
        modifyButton.setForeground(Color.black);
        modifyButton.setBounds(520, 260, 200, 55);
        modifyButton.addActionListener(e -> mainFrame.switchToModifyPanel());
        add(modifyButton);
    }

    /**
     * Move Up in the list of Lernkarteien.
     */
    private void moveUp() {
        mainFrame.moveToNextLernkartei();
        updateCurrentLernkartei();
    }

    /**
     * Move Down in the list of Lernkarteien.
     */
    private void moveDown() {
        mainFrame.moveToPreviousLernkartei();
        updateCurrentLernkartei();
    }

    /**
     * Update the currently selected Lernkartei and refresh the label.
     */
    private void updateCurrentLernkartei() {
        int currentIndex = mainFrame.getCurrentLernkarteiIndex();
        Lernkartei currentLernkartei = mainFrame.getCurrentLernkartei();
        languageLabel.setText(mainFrame.getLanguageLabels()[currentIndex]);

        System.out.println("Current Lernkartei: " + currentLernkartei.getBeschreibung());
        System.out.println("Lernkartei Number: " + currentLernkartei.getNummer());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
