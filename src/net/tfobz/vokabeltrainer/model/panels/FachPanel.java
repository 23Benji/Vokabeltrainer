package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FachPanel extends JPanel {
    MainFrame mainFrame;
    private JButton homeButton, leftArrow, rightArrow;
    private JLabel headingLabel;
    private JButton[] subjectButtons;
    private LanguageDirectionDialog languageDirectionDialog;
    private int FachPage = 1;


    public FachPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(null); // No layout manager for custom positioning
        setBackground(new Color(58, 78, 66)); // Background color from image

        // Heading Label
        headingLabel = new JLabel("Select a compartment", SwingConstants.CENTER);
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
    private JButton createSubjectButton(String fachLabel, int index) {
        JButton button = new JButton(fachLabel);
        button.setBackground(new Color(39, 85, 107)); // Darker blue color
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setFocusPainted(false); // No focus border
        button.addActionListener(e -> {
            System.out.println(index);
            new LanguageDirectionDialog(index);
        });
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

        // Fetch and cache the Karten list to avoid multiple method calls
        List<Fach> kartenList = VokabeltrainerDB.getFaecher(mainFrame.getLernKarteiNummer());
        boolean isEmpty;

        for (int i = 1; i <= 6; i++) {
            // Calculate dynamic Fach number
            String fachName = "Fach " + (i + (page - 1) * 6);

            // Create button
            subjectButtons[i - 1] = createSubjectButton(fachName, i + (page - 1) * 6);

            // Check if there are any Karten inside the Fach
            List<Karte> kartenForFach = VokabeltrainerDB.getKarten(i + (page - 1) * 6);
            isEmpty = (kartenForFach == null || kartenForFach.isEmpty());

            if (isEmpty) {
                subjectButtons[i - 1].setEnabled(false);
            }

            // Set position and add to UI
            subjectButtons[i - 1].setBounds(xOffset, yOffset, buttonWidth, buttonHeight);
            add(subjectButtons[i - 1]);

            // Update position
            xOffset += buttonWidth + buttonSpacing;
            if (i == 3) { // Move to next row after 3 buttons
                yOffset += 150;
                xOffset = 100;
            }
        }

        // Refresh the panel to reflect changes
        revalidate();
        repaint();
    }



    // Inner class for LanguageDirectionDialog JFrame
    private class LanguageDirectionDialog extends JFrame {
        private JLabel directionLabel;
        private JButton swapButton;
        private JButton startButton;

        public LanguageDirectionDialog(int FachNummer) {
            // Setup the frame
            setTitle("Language Direction");
            setSize(300, 150);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Setup the panel layout
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

            // Language direction label
            directionLabel = new JLabel("Deutsch -> Italian");
            panel.add(directionLabel);

            // Swap button
            swapButton = new JButton(new ImageIcon("icons/swap.png")); // Replace with actual path
            swapButton.addActionListener(e -> swapLanguages());
            panel.add(swapButton);

            // Start button
            startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startQuiz();
                }
            });
            panel.add(startButton);

            this.add(panel);
            this.setVisible(true);
        }

        // Method to swap the languages in the label
        private void swapLanguages() {
            String currentText = directionLabel.getText();
            if (currentText.equals("Deutsch -> Italian")) {
                directionLabel.setText("Italian -> Deutsch");
            } else {
                directionLabel.setText("Deutsch -> Italian");
            }
        }

        // Method to handle the start quiz action
        private void startQuiz() {
            mainFrame.switchToQuizPanel();
        }
    }

}
