package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

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
    private boolean allCardsAnswered = false; // Track if all cards are done
    private int correct;
    private int wrong;
    private int total;

    // Constructor
    public QuizPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        setLayout(null);
        loadNextCard();
        addEventListeners();
        addEnterKeyBinding();
        this.correct = 0;
        this.wrong = 0;
        this.total = 0;
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
        wordLabel.setBounds(250, 80, 300, 80);
        add(wordLabel);

        correctAnswerLabel = new JLabel("Correct Answer", SwingConstants.CENTER);
        correctAnswerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        correctAnswerLabel.setForeground(Color.RED);
        correctAnswerLabel.setOpaque(true);
        correctAnswerLabel.setVisible(false);
        correctAnswerLabel.setBounds(300, 180, 200, 20);
        add(correctAnswerLabel);

        inputField = new JTextField();
        inputField.setBounds(250, 440, 210, 40);
        inputField.setFocusable(true);
        inputField.requestFocusInWindow();
        add(inputField);

        checkButton = new JButton("Check");
        checkButton.setBounds(460, 440, 90, 40);
        checkButton.setBackground(new Color(177, 194, 158));
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.setBorderPainted(false);
        add(checkButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(460, 440, 90, 40);
        nextButton.setBackground(new Color(46, 134, 193));
        nextButton.setForeground(Color.WHITE);
        nextButton.setVisible(false);
        add(nextButton);

        setDefaultScreen();
    }

    // Add key binding for Enter key to trigger actions
    private void addEnterKeyBinding() {
        // For Check Button
        checkButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "checkAction");
        checkButton.getActionMap().put("checkAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkButton.doClick();
            }
        });

        // For Next Button (when it's visible)
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "nextAction");
        nextButton.getActionMap().put("nextAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButton.doClick();
            }
        });
    }

    // Load the next card from the database
    private void loadNextCard() {
        if (allCardsAnswered) {
            setFinishScreen();
            return;
        }

        currentCard = VokabeltrainerDB.getZufaelligeKarte(mainFrame.getCurrentLernkartei().getNummer(), mainFrame.getFachNummer());
        if (currentCard != null) {
            if (currentCard.getRichtung()) {
                wordLabel.setText(currentCard.getWortEins());
            } else {
                wordLabel.setText(currentCard.getWortZwei());
            }
            setDefaultScreen();
        } else {
            allCardsAnswered = true; // No more cards available
            setFinishScreen();
        }
    }

    // Set Default Screen
    private void setDefaultScreen() {
        setBackground(new Color(101, 146, 135)); // Default background: rgb(101, 146, 135)
        wordLabel.setBackground(new Color(177, 194, 158)); // Word Box: rgb(177, 194, 158)
        correctAnswerLabel.setVisible(false);
        nextButton.setVisible(false);
        checkButton.setVisible(true);
        inputField.setText("");
    }

    // Set Correct Screen
    private void setCorrectScreen() {
        setBackground(new Color(83, 124, 67)); // Correct background: rgb(92, 179, 56)
        wordLabel.setBackground(new Color(255, 230, 169)); // Light yellow for word box: rgb(255, 230, 169)
        correctAnswerLabel.setVisible(false);
        nextButton.setVisible(true);
        checkButton.setVisible(false);
    }

    // Set Wrong Screen
    private void setWrongScreen(String correctAnswer) {
        setBackground(new Color(251, 65, 65)); // Wrong background: rgb(251, 65, 65)
        wordLabel.setBackground(new Color(222, 170, 121)); // Light brown word box: rgb(222, 170, 121)
        correctAnswerLabel.setText("Correct Answer: " + correctAnswer);
        correctAnswerLabel.setVisible(true);
        nextButton.setVisible(true);
        checkButton.setVisible(false);
    }

    // Set Finish Screen
    private void setFinishScreen() {
        setBackground(new Color(85, 126, 148)); // Gold background
        removeAll(); // Clear all existing components
        repaint();
        revalidate();

        // Quiz Completion Label
        JLabel finishLabel = new JLabel("Quiz Completed!", SwingConstants.CENTER);
        finishLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finishLabel.setBounds(285, 150, 200, 50);
        finishLabel.setForeground(Color.BLACK);
        add(finishLabel);

        // Correct, Wrong, and Total Score Labels
        JLabel correctLabel = new JLabel("Correct: " + correct, SwingConstants.CENTER);
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        correctLabel.setBounds(285, 220, 200, 40);
        correctLabel.setForeground(Color.GREEN);
        add(correctLabel);

        JLabel wrongLabel = new JLabel("Wrong: " + wrong, SwingConstants.CENTER);
        wrongLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        wrongLabel.setBounds(285, 270, 200, 40);
        wrongLabel.setForeground(Color.RED);
        add(wrongLabel);

        JLabel totalLabel = new JLabel("Total: " + total, SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        totalLabel.setBounds(285, 320, 200, 40);
        totalLabel.setForeground(Color.BLACK);
        add(totalLabel);

        // Home Button
        JButton homeButton = new JButton("Home");
        homeButton.setBounds(285, 380, 200, 40);
        homeButton.setBackground(new Color(121, 141, 222));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(e -> mainFrame.switchToHomePanel());
        add(homeButton);
    }

    // Event Listeners
    private void addEventListeners() {
        // Check Button Logic
        checkButton.addActionListener(e -> {
            inputField.setEnabled(false);
            if (currentCard == null) return;

            String userInput = inputField.getText().trim();
            String correctAnswer = currentCard.getRichtung() ? currentCard.getWortZwei() : currentCard.getWortEins();
            int result = 0;

            if (currentCard.getRichtig(userInput)) {
                do {
                    result = VokabeltrainerDB.setKarteRichtig(currentCard); // Mark as correct in DB

                    if (result == -2) {
                        VokabeltrainerDB.hinzufuegenFach(mainFrame.getCurrentLernkartei().getNummer(), new Fach());
                    } else if (result == -1) {
                        JOptionPane.showMessageDialog(this, "Database error occurred while saving the card.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } while (result == -2);
                correct++;
                setCorrectScreen();
            } else {
                do {
                    result = VokabeltrainerDB.setKarteFalsch(currentCard); // Mark as incorrect in DB

                    if (result == -1) {
                        JOptionPane.showMessageDialog(this, "Database error occurred while saving the card.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } while (result == -1);
                wrong++;
                setWrongScreen(correctAnswer);
            }
        });

        // Next Button Logic
        nextButton.addActionListener(e -> {
            total++;
            inputField.setEnabled(true);
            loadNextCard();
        }); // Load a new question

        // Home Button Logic
        homeButton.addActionListener(e -> mainFrame.switchToHomePanel());
    }
}
