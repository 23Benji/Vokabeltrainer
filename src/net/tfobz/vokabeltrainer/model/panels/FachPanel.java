package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.*;

import javax.swing.*;
import java.awt.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.List;
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

    private JButton createSubjectButton(String fachLabel, int index, boolean isClickable) {
        JButton button = new JButton(fachLabel);
        button.setBackground(isClickable ? new Color(39, 85, 107) : new Color(100, 100, 100)); // Gray if not clickable
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        //button.setEnabled(isClickable); // Disable button if not clickable

//        if (isClickable) {
//            button.addActionListener(e -> ConfirmDialog(index));
//        }
        button.addActionListener(e -> {ConfirmDialog(index);});

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

        List<Fach> kartenForFach = VokabeltrainerDB.getFaecher(mainFrame.getCurrentLernkartei().getNummer());
        System.out.println("Fetched subjects: " + kartenForFach); // Debug

        if (kartenForFach == null || kartenForFach.isEmpty()) {
            JLabel emptyLabel = new JLabel("Keine verfügbaren Fächer.");
            emptyLabel.setBounds(xOffset, y, 400, 50);
            emptyLabel.setForeground(Color.WHITE);
            add(emptyLabel);
            rightArrow.setEnabled(false);
            leftArrow.setEnabled(false);
        } else {
            List<Fach> nonEmptyFaecher = kartenForFach.stream()
                    .filter(fach -> fach.getBeschreibung() != null && !fach.getBeschreibung().isEmpty()
                            && !VokabeltrainerDB.getKarten(fach.getNummer()).isEmpty())
                    .collect(Collectors.toList());

            System.out.println("Filtered subjects: " + nonEmptyFaecher); // Debug

            if (nonEmptyFaecher.isEmpty()) {
                JLabel emptyLabel = new JLabel("Keine verfügbaren Fächer.");
                emptyLabel.setBounds(xOffset, y, 400, 50);
                emptyLabel.setForeground(Color.WHITE);
                add(emptyLabel);
                rightArrow.setEnabled(false);
                leftArrow.setEnabled(false);
            } else {
                // Ensure subjectButtons array is initialized to hold 3 buttons at max for the page
                JButton[] subjectButtons = new JButton[3];
                int startIndex = (page - 1) * 3;
                int endIndex = Math.min(startIndex + 3, nonEmptyFaecher.size());

                for (int i = startIndex; i < endIndex; i++) {
                    Fach fach = nonEmptyFaecher.get(i);
                    String fachName = fach.getBeschreibung();
                    System.out.println("Creating button for subject: " + fachName); // Debug

                    boolean isClickable = isFachAccessible(fach);
                    String buttonLabel = fachName;

                    // Commented out the part related to the timeLabel
                    // JLabel timeLabel = null;
                    // if (!isClickable) {
                    //     Date lastAccess = fach.getGelerntAm();
                    //     LocalDateTime lastAccessTime = Instant.ofEpochMilli(lastAccess.getTime())
                    //             .atZone(ZoneId.systemDefault())
                    //             .toLocalDateTime();

                    //     long hoursLeft = 24 - Duration.between(lastAccessTime, LocalDateTime.now()).toHours();
                    //     timeLabel = new JLabel("verfügbar in " + hoursLeft + " Std.");
                    //     timeLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
                    //     timeLabel.setForeground(Color.white);
                    //     timeLabel.setBounds(xOffset, y + buttonHeight, buttonWidth, 20);
                    //     add(timeLabel);
                    // }

                    // Initialize the button and set its bounds
                    subjectButtons[i - startIndex] = createSubjectButton(buttonLabel, fach.getNummer(), isClickable);
                    subjectButtons[i - startIndex].setBounds(xOffset, y, buttonWidth, buttonHeight);
                    System.out.println("Button created: " + subjectButtons[i - startIndex].getText()); // Debug
                    add(subjectButtons[i - startIndex]);

                    xOffset += buttonWidth + buttonSpacing;
                }

                leftArrow.setEnabled(page > 1);
                rightArrow.setEnabled(endIndex < nonEmptyFaecher.size());
            }
        }

        revalidate(); // After adding all components
        repaint();    // After adding all components
    }



    private boolean isFachAccessible(Fach fach) {
//        LocalDate localDate = LocalDate.of(2025, 1, 1);
//        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        fach.setGelerntAm(date);
        Date lastAccess = fach.getGelerntAm(); // Fetch last access time
        System.out.println(fach.toString());

        if (lastAccess == null) {
            return true; // Accessible if never accessed before
        }

        // Convert Date to LocalDateTime
        LocalDateTime lastAccessTime = Instant.ofEpochMilli(lastAccess.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return Duration.between(lastAccessTime, LocalDateTime.now()).toHours() >= 24;
    }





    private void ConfirmDialog(int fachNummer) {
        // Create the dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Start Lesson", true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(177, 194, 158)); // Soft greenish background

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(101, 146, 135)); // Deep teal
        JLabel titleLabel = new JLabel("Lernkartei: " + mainFrame.getCurrentLernkartei().toString(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE); // White text for clarity
        titlePanel.add(titleLabel);
        dialog.add(titlePanel, BorderLayout.NORTH);

        // Fach Number Display
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        centerPanel.setBackground(new Color(177, 194, 158)); // Match main background

        JLabel fachLabel = new JLabel("Fach Nr. " + fachNummer);
        fachLabel.setFont(new Font("Arial", Font.BOLD, 20));
        fachLabel.setForeground(new Color(101, 146, 135)); // Deep teal for consistency
        centerPanel.add(fachLabel);

        dialog.add(centerPanel, BorderLayout.CENTER);

        // Start Button
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

