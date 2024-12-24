package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FachPanel extends JPanel {
    MainFrame mainFrame;
    private JButton homeButton, leftArrow, rightArrow;
    private JLabel headingLabel;
    private JButton[] subjectButtons;
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
        homeButton.setBounds(20, 10, 32, 32); // Set bounds for the home button
        homeButton.addActionListener(e -> mainFrame.switchToHomePanel());
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

    private JButton createSubjectButton(String fachLabel, int index) {
        JButton button = new JButton(fachLabel);
        button.setBackground(new Color(39, 85, 107));
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.addActionListener(e -> showLanguageDirectionDialog(index));
        return button;
    }

    public void printFaecher(int page) {
        removeAll();
        add(headingLabel);
        add(homeButton);
        add(leftArrow);
        add(rightArrow);

        int xOffset = 100, y = 225;
        int buttonWidth = 175, buttonHeight = 120;
        int buttonSpacing = 20;

        // Get non-empty Fächer from the database
        List<Fach> kartenForFach = VokabeltrainerDB.getFaecher(mainFrame.getLernKarteiNummer());

        if (kartenForFach == null || kartenForFach.isEmpty()) {
            JLabel emptyLabel = new JLabel("Keine verfügbaren Fächer.");
            emptyLabel.setBounds(xOffset, y, 400, 50);
            add(emptyLabel);
            rightArrow.setEnabled(false); // Disable right arrow if no subjects exist
        } else {
            // Filter non-empty Fächer
            List<Fach> nonEmptyFaecher = kartenForFach.stream()
                    .filter(fach -> fach.getBeschreibung() != null && !fach.getBeschreibung().isEmpty())
                    .collect(Collectors.toList());

            // Pagination Logic
            int startIndex = (page - 1) * 3;
            int endIndex = Math.min(startIndex + 3, nonEmptyFaecher.size());

            // Display up to 3 non-empty Fächer per page
            for (int i = startIndex; i < endIndex; i++) {
                Fach fach = nonEmptyFaecher.get(i);
                String fachName = fach.getBeschreibung();

                subjectButtons[i - startIndex] = createSubjectButton(fachName, fach.getNummer());
                subjectButtons[i - startIndex].setBounds(xOffset, y, buttonWidth, buttonHeight);
                add(subjectButtons[i - startIndex]);

                xOffset += buttonWidth + buttonSpacing;
            }

            // Disable right arrow if there are no more pages
            boolean hasNextPage = endIndex < nonEmptyFaecher.size();
            rightArrow.setEnabled(hasNextPage);
        }

        revalidate();
        repaint();
    }


    private void showLanguageDirectionDialog(int fachNummer) {
        System.out.println(VokabeltrainerDB.getKarten(fachNummer).toString());
        System.out.println("Random: "+VokabeltrainerDB.getZufaelligeKarte(mainFrame.getLernKarteiNummer(), fachNummer).toString());
        // Create the dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Language Direction", true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(177, 194, 158)); // Soft greenish background

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(101, 146, 135)); // Deep teal
        JLabel titleLabel = new JLabel("Select Language Direction");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE); // White text for clarity
        titlePanel.add(titleLabel);
        dialog.add(titlePanel, BorderLayout.NORTH);

        // Retrieve the current Lernkartei
        Lernkartei lernkartei = VokabeltrainerDB.getLernkartei(mainFrame.getLernKarteiNummer());
        String languageDirection = "Not Available"; // Default fallback
        if (lernkartei != null) {
            languageDirection = lernkartei.toString().replace(" ", " -> ");
        }

        // Center Panel for Language Direction and Buttons
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        centerPanel.setBackground(new Color(177, 194, 158)); // Match main background

        JLabel directionLabel = new JLabel(languageDirection);
        directionLabel.setFont(new Font("Arial", Font.BOLD  , 20));
        directionLabel.setForeground(new Color(101, 146, 135)); // Deep teal for consistency
        directionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton swapButton = new JButton(new ImageIcon("icons/swap.png"));
        swapButton.setToolTipText("Swap Language Direction");
        swapButton.setFocusPainted(false);
        swapButton.setBorderPainted(false);
        swapButton.setContentAreaFilled(false);
        swapButton.setPreferredSize(new Dimension(50, 50));

        swapButton.addActionListener(e -> {
            String currentText = directionLabel.getText();

            if (currentText.contains(" ") && !currentText.contains("->")) {
                String[] parts = currentText.split(" ");
                if (parts.length == 2) {
                    directionLabel.setText(parts[0] + " -> " + parts[1]);
                }
            } else if (currentText.contains("->")) {
                String[] parts = currentText.split(" -> ");
                if (parts.length == 2) {
                    directionLabel.setText(parts[1] + " -> " + parts[0]);
                }
            }
        });

        centerPanel.add(directionLabel);
        centerPanel.add(swapButton);

        dialog.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel with Start Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(177, 194, 158)); // Warm earthy orange

        JButton startButton = new JButton("Start Lesson");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(101, 146, 135)); // Deep teal for contrast
        startButton.setPreferredSize(new Dimension(150, 40));

        startButton.addActionListener(e -> {
            mainFrame.setFachNummer(fachNummer);
            dialog.dispose();
            mainFrame.switchToQuizPanel();
        });

        bottomPanel.add(startButton);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        // Final settings
        dialog.setResizable(false);
        dialog.setVisible(true);
    }


}

