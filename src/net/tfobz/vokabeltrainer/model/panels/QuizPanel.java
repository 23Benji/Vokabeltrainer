package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.model.Karte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton homeButton, checkButton, nextButton;
    private JTextField inputField;
    private JLabel wordLabel, correctAnswerLabel;
    private Karte currentCard; // Store the current Karte for validation

    // Constructor
    public QuizPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        setLayout(null);
        loadNextCard();
        addEventListeners();
    }

    // Initialize GUI Components
    private void initializeComponents() {
        homeButton = new JButton(new ImageIcon("icons/home.png"));
        homeButton.setBounds(20, 10, 32, 32);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        add(homeButton);

        wordLabel = new JLabel("Word", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 30));
        wordLabel.setOpaque(true);
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setBounds(100, 80, 300, 80);
        add(wordLabel);

        correctAnswerLabel = new JLabel("Correct Answer", SwingConstants.CENTER);
        correctAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        correctAnswerLabel.setForeground(Color.WHITE);
        correctAnswerLabel.setOpaque(true);
        correctAnswerLabel.setVisible(false);
        correctAnswerLabel.setBounds(150, 180, 200, 40);
        add(correctAnswerLabel);

        inputField = new JTextField();
        inputField.setBounds(100, 270, 200, 40);
        add(inputField);

        checkButton = new JButton("Check");
        checkButton.setBounds(310, 270, 90, 40);
        checkButton.setBackground(new Color(52, 152, 219));
        checkButton.setForeground(Color.WHITE);
        add(checkButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(310, 270, 90, 40);
        nextButton.setBackground(new Color(46, 134, 193));
        nextButton.setForeground(Color.WHITE);
        nextButton.setVisible(false);
        add(nextButton);

        setDefaultScreen();
    }

    // Load the next card from the database
    private void loadNextCard() {
        currentCard = VokabeltrainerDB.getZufaelligeKarte(mainFrame.getLernKarteiNummer(), mainFrame.getFachNummer());
        System.out.println(currentCard.toString());
        if (currentCard != null) {
            if (currentCard.getRichtung()) {
                wordLabel.setText(currentCard.getWortEins());
            } else {
                wordLabel.setText(currentCard.getWortZwei());
            }
            setDefaultScreen();
        } else {
            wordLabel.setText("No cards available");
            System.out.println();
            checkButton.setEnabled(false);
            inputField.setEnabled(false);
        }
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
        correctAnswerLabel.setText("Correct: " + correctAnswer);
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
                if (currentCard == null) return;

                String userInput = inputField.getText().trim();
                String correctAnswer = currentCard.getRichtung() ? currentCard.getWortZwei() : currentCard.getWortEins();

                if (currentCard.getRichtig(userInput)) {
                    VokabeltrainerDB.setKarteRichtig(currentCard); // Mark as correct in DB
                    setCorrectScreen();
                } else {
                    VokabeltrainerDB.setKarteFalsch(currentCard); // Mark as incorrect in DB
                    setWrongScreen(correctAnswer);
                }
            }


        });

        // Next Button Logic
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNextCard(); // Load a new question
            }
        });

        // Home Button Logic
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToHomePanel();
            }
        });
    }
}
