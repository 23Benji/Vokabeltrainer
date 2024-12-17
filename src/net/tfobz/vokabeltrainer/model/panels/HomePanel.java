package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

import javax.swing.*;
import java.awt.*;


public class HomePanel extends JPanel {

    JLabel languageLabel;
    JButton upButton, downButton, startButton,modifyButton;
    public int LernKarteiCounter = 1;
    private Image backgroundImage;
    private MainFrame mainFrame ;



    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Set up the panel properties
        setLayout(null); // No layout manager
        backgroundImage = new ImageIcon("images/Bgd2.jpg").getImage();

        // Arrow icons
        ImageIcon upArrow = new ImageIcon("icons/up_arrow.png");   // Replace with your image path
        ImageIcon downArrow = new ImageIcon("icons/down_arrow.png");

        // Up arrow button
        upButton = new JButton(upArrow);
        upButton.setBorderPainted(false);
        upButton.setFocusPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setBounds(213, 70, 64, 64); // Position and size (x, y, width, height)
        upButton.addActionListener(e -> {
            System.out.println("Up button was pressed!");
            System.out.println("Lern Kartei: " + LernKarteiCounter);
            LernKarteiCounter++;
            if (LernKarteiCounter > VokabeltrainerDB.getLernkarteien().size()) {
                LernKarteiCounter = 1;
            }
            languageLabel.setText(VokabeltrainerDB.getLernkartei(LernKarteiCounter).toString());
        });
        add(upButton);

        // Language label
        languageLabel = new JLabel(VokabeltrainerDB.getLernkartei(LernKarteiCounter).toString(), JLabel.CENTER);
        languageLabel.setFont(new Font("Roboto", Font.BOLD, 30)); // Cool, modern font
        languageLabel.setOpaque(true);
        languageLabel.setBackground(new Color(177, 194, 158));
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setBounds(50, 130, 400, 250); // Position and size
        add(languageLabel);

        // Down arrow button
        downButton = new JButton(downArrow);
        downButton.setBorderPainted(false);
        downButton.setFocusPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setBounds(213, 375, 64, 64); // Position and size
        downButton.addActionListener(e -> {
            System.out.println("Down button was pressed!");
            LernKarteiCounter--;
            if (LernKarteiCounter < 1) {
                LernKarteiCounter = VokabeltrainerDB.getLernkarteien().size();
            }
            languageLabel.setText(VokabeltrainerDB.getLernkartei(LernKarteiCounter).toString());

        });
        add(upButton);
        add(downButton);

        // Start button
        startButton = new JButton("Start");
        startButton.setBackground(new Color(255, 230, 169));
        startButton.setForeground(Color.black);
        startButton.setBounds(520, 180, 200, 55); // Position and size
        startButton.addActionListener(e -> {
            System.out.println("Start button was pressed!");
            mainFrame.switchToFachPanel(LernKarteiCounter);
        });
        add(startButton);

        // Modify button
        modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(255, 230, 169));
        modifyButton.setForeground(Color.black);
        modifyButton.setBounds(520, 260, 200, 55); // Position and size

        modifyButton.addActionListener(e -> {
            System.out.println("Modify button was pressed!");
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
