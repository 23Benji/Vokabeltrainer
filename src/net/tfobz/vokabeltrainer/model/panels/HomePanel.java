package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

import javax.swing.*;
import java.awt.*;


public class HomePanel extends JPanel {

    JLabel languageLabel;
    JButton upButton, downButton, startButton,modifyButton, settingsButton;
    private Image backgroundImage;
    private MainFrame mainFrame ;



    public HomePanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        // Set up the panel properties
        setLayout(null); // No layout manager
        backgroundImage = new ImageIcon("images/Bgd2.jpg").getImage();


        // Settings button
        settingsButton = new JButton(new ImageIcon("icons/settings.png")); // Replace with your settings icon path
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setBounds(730, 10, 50, 50); // Top-right corner
        settingsButton.addActionListener(e -> {
            mainFrame.switchToSettingPanel();
        });
        add(settingsButton);


        // Up arrow button
        upButton = new JButton(new ImageIcon("icons/up_arrow.png"));
        upButton.setBorderPainted(false);
        upButton.setFocusPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setBounds(213, 70, 64, 64); // Position and size (x, y, width, height)
        upButton.addActionListener(e -> {
            mainFrame.setLernKarteiNummer(mainFrame.getLernKarteiNummer()+1);
            if (mainFrame.getLernKarteiNummer() > VokabeltrainerDB.getLernkarteien().size()) {
                mainFrame.setLernKarteiNummer(1);
            }
            languageLabel.setText(VokabeltrainerDB.getLernkartei(mainFrame.getLernKarteiNummer()).toString());
        });
        add(upButton);

        // Language label
        languageLabel = new JLabel(VokabeltrainerDB.getLernkartei(mainFrame.getLernKarteiNummer()).toString(), JLabel.CENTER);
        languageLabel.setFont(new Font("Roboto", Font.BOLD, 30)); // Cool, modern font
        languageLabel.setOpaque(true);
        languageLabel.setBackground(new Color(177, 194, 158));
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setBounds(50, 130, 400, 250); // Position and size
        add(languageLabel);

        // Down arrow button
        downButton = new JButton(new ImageIcon("icons/down_arrow.png"));
        downButton.setBorderPainted(false);
        downButton.setFocusPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setBounds(213, 375, 64, 64); // Position and size
        downButton.addActionListener(e -> {
            mainFrame.setLernKarteiNummer(mainFrame.getLernKarteiNummer()-1);
            if (mainFrame.getLernKarteiNummer() < 1) {
                mainFrame.setLernKarteiNummer(VokabeltrainerDB.getLernkarteien().size());
            }
            languageLabel.setText(VokabeltrainerDB.getLernkartei(mainFrame.getLernKarteiNummer()).toString());

        });
        add(upButton);
        add(downButton);

        // Start button
        startButton = new JButton("Start");
        startButton.setBackground(new Color(255, 230, 169));
        startButton.setForeground(Color.black);
        startButton.setBounds(520, 180, 200, 55); // Position and size
        startButton.addActionListener(e -> {
            mainFrame.switchToFachPanel();
        });
        add(startButton);

        // Modify button
        modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(255, 230, 169));
        modifyButton.setForeground(Color.black);
        modifyButton.setBounds(520, 260, 200, 55); // Position and size

        modifyButton.addActionListener(e -> {
        });
        add(modifyButton);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}
