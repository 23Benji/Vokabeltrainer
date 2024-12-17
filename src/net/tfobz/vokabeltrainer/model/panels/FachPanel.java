package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FachPanel extends JPanel {
    MainFrame mainFrame;
    private JButton homeButton, leftArrow, rightArrow;
    private JLabel headingLabel;
    private JButton[] subjectButtons;
    int LernKarteiNumber;
    int FachPage = 1;


    public FachPanel(MainFrame mainFrame, int LernKarteiNumber) {
        this.mainFrame = mainFrame;
        this.LernKarteiNumber = LernKarteiNumber;

        setLayout(null); // No layout manager for custom positioning
        setBackground(new Color(101, 146, 135));

        // Heading Label
        headingLabel = new JLabel("Wähle ein Fach aus...", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Roboto", Font.BOLD, 40)); // Cool, modern font
        headingLabel.setForeground(Color.white);
        headingLabel.setBounds(0, 20, 800, 40);
        add(headingLabel);

        homeButton = new JButton(new ImageIcon("icons/home.png")); // Assuming 'home_icon.png' is the home icon
        homeButton.setBorder(BorderFactory.createEmptyBorder());
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setBounds(10, 10, 32, 32); // Set bounds for the home button
        homeButton.addActionListener(e -> mainFrame.switchToHomePanel());
        homeButton.setVisible(true);
        add(homeButton);


        // Left navigation arrow
        leftArrow = new JButton(new ImageIcon("icons/left_arrow.png"));
        leftArrow.setBorder(BorderFactory.createEmptyBorder());
        leftArrow.setContentAreaFilled(false);
        leftArrow.setFocusPainted(false);
        leftArrow.setBounds(30, 257, 64, 64);
        leftArrow.addActionListener(e -> {
            if (FachPage > 1) {
                FachPage--;
                printFaecher(FachPage);
            }
        });
        add(leftArrow);

        // Right navigation arrow
        rightArrow = new JButton(new ImageIcon("icons/right_arrow.png"));
        rightArrow.setBorder(BorderFactory.createEmptyBorder());
        rightArrow.setContentAreaFilled(false);
        rightArrow.setFocusPainted(false);
        rightArrow.setBounds(675, 257, 64, 64);
        rightArrow.addActionListener(e -> {
            FachPage++;
            printFaecher(FachPage);
        });
        add(rightArrow);

        subjectButtons = new JButton[6];
        printFaecher(FachPage);

    }

    // Method to create a button for each "Fach"
    private JButton createSubjectButton(String fachLabel, String cardsLabel) {
        JButton button = new JButton(fachLabel + "\n" + cardsLabel);
        button.setBackground(new Color(39, 85, 107)); // Darker blue color
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setFocusPainted(false); // No focus border
        return button;
    }

    public void printFaecher(int page) {
        // Remove all components from the panel
        removeAll();

        // Re-add the common components (heading, home button, arrows)
        add(headingLabel);
        add(homeButton);
        add(leftArrow);
        add(rightArrow);

        // Update buttons for subjects
        int xOffset = 100, yOffset = 150;
        int buttonWidth = 175, buttonHeight = 120; // Size of each button
        int buttonSpacing = 20; // Space between buttons

        for (int i = 0; i < 3; i++) {
            subjectButtons[i] = createSubjectButton("Fach " + ((i + 1) + (page - 1) * 6), " X Karten");
            subjectButtons[i].setBounds(xOffset, yOffset, buttonWidth, buttonHeight);
            add(subjectButtons[i]);
            xOffset += buttonWidth + buttonSpacing; // Move to next position
        }

        yOffset += 150; // Move to next row
        xOffset = 100;
        for (int i = 3; i < 6; i++) {
            subjectButtons[i] = createSubjectButton("Fach " + ((i + 1) + (page - 1) * 6), "X Karten");
            subjectButtons[i].setBounds(xOffset, yOffset, buttonWidth, buttonHeight);
            add(subjectButtons[i]);
            xOffset += buttonWidth + buttonSpacing; // Move to next position
        }

        // Refresh the panel to reflect changes
        revalidate();
        repaint();
    }

}
