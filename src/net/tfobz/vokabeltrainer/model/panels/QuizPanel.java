package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton, checkButton, nextButton;
    private JTextField inputField;
    private JLabel wordLabel, correctAnswerLabel;
    private boolean isAnswerCorrect = false; // Placeholder for input validation logic

    // Constructor
    public QuizPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        setLayout(null);
        setDefaultScreen();
        addEventListeners();
    }

    // Initialize GUI Components
    private void initializeComponents() {
        // Home Button
        homeButton = new JButton("icons/back.png");
        homeButton.setBounds(20, 10, 50, 40);
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        add(homeButton);

        // Word Label
        wordLabel = new JLabel("Word", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 30));
        wordLabel.setOpaque(true);
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setBounds(100, 80, 300, 80);
        add(wordLabel);

        // Correct Answer Label
        correctAnswerLabel = new JLabel("Correct Answer", SwingConstants.CENTER);
        correctAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        correctAnswerLabel.setForeground(Color.WHITE);
        correctAnswerLabel.setOpaque(true);
        correctAnswerLabel.setVisible(false);
        correctAnswerLabel.setBounds(150, 180, 200, 40);
        add(correctAnswerLabel);

        // Input Field
        inputField = new JTextField();
        inputField.setBounds(100, 270, 200, 40);
        add(inputField);

        // Check Button
        checkButton = new JButton("Check");
        checkButton.setBounds(310, 270, 90, 40);
        checkButton.setBackground(new Color(52, 152, 219));
        checkButton.setForeground(Color.WHITE);
        add(checkButton);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setBounds(310, 270, 90, 40);
        nextButton.setBackground(new Color(46, 134, 193));
        nextButton.setForeground(Color.WHITE);
        nextButton.setVisible(false);
        add(nextButton);
    }

    // Set Default Screen
    private void setDefaultScreen() {
        setBackground(new Color(13, 63, 141)); // Blue background
        wordLabel.setBackground(new Color(52, 109, 199)); // Blue Word Box
        correctAnswerLabel.setVisible(false);
        nextButton.setVisible(false);
        checkButton.setVisible(true);
        inputField.setText("");
    }

    // Set Correct Screen
    private void setCorrectScreen() {
        setBackground(new Color(0, 153, 0)); // Green background
        wordLabel.setBackground(new Color(144, 238, 144)); // Light Green Word Box
        correctAnswerLabel.setVisible(false);
        nextButton.setVisible(true);
        checkButton.setVisible(false);
    }

    // Set Wrong Screen
    private void setWrongScreen(String correctAnswer) {
        setBackground(new Color(153, 0, 0)); // Red background
        wordLabel.setBackground(new Color(255, 69, 58)); // Red Word Box
        correctAnswerLabel.setText(correctAnswer);
        correctAnswerLabel.setVisible(true);
        nextButton.setVisible(true);
        checkButton.setVisible(false);
    }

    // Event Listeners
    private void addEventListeners() {
        // Check Button Logic
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder: Replace with input validation logic
                String userInput = inputField.getText();
                String correctAnswer = "Correct Answer"; // Example correct answer

                if (userInput.equalsIgnoreCase(correctAnswer)) {
                    setCorrectScreen();
                } else {
                    setWrongScreen(correctAnswer);
                }
            }
        });

        // Next Button Logic
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDefaultScreen();
                // Placeholder: Add logic to load the next question
            }
        });

        // Home Button Logic
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder: Navigate back to the main frame
                mainFrame.switchToHomePanel();
            }
        });
    }
}
