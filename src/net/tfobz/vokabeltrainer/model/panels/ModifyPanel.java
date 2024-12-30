package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyPanel extends JPanel {

    private JList<String> vocabularyList;
    private DefaultListModel<String> listModel;
    private JTextField wordField, translationField;
    private JButton addButton, editButton, deleteButton, saveButton, backButton;
    private MainFrame mainFrame;

    public ModifyPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // Title Label
        JLabel titleLabel = new JLabel("Modify Vocabulary", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setBounds(0, 20, 800, 30);
        add(titleLabel);

        // Vocabulary List
        listModel = new DefaultListModel<>();
        vocabularyList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(vocabularyList);
        listScrollPane.setBounds(50, 70, 300, 400);
        add(listScrollPane);

        // Word Field
        JLabel wordLabel = new JLabel("Word:");
        wordLabel.setBounds(400, 100, 100, 25);
        add(wordLabel);

        wordField = new JTextField();
        wordField.setBounds(500, 100, 200, 25);
        add(wordField);

        // Translation Field
        JLabel translationLabel = new JLabel("Translation:");
        translationLabel.setBounds(400, 150, 100, 25);
        add(translationLabel);

        translationField = new JTextField();
        translationField.setBounds(500, 150, 200, 25);
        add(translationField);

        // Buttons
        addButton = new JButton("Add");
        addButton.setBounds(400, 200, 100, 30);
        addButton.addActionListener(e -> addVocabulary());
        add(addButton);

        editButton = new JButton("Edit");
        editButton.setBounds(510, 200, 100, 30);
        editButton.addActionListener(e -> editVocabulary());
        add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(620, 200, 100, 30);
        deleteButton.addActionListener(e -> deleteVocabulary());
        add(deleteButton);

        saveButton = new JButton("Save");
        saveButton.setBounds(400, 250, 150, 30);
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton);

        backButton = new JButton("Back");
        backButton.setBounds(560, 250, 150, 30);
        backButton.addActionListener(e -> mainFrame.switchToHomePanel());
        add(backButton);

        loadVocabulary();
    }

    private void loadVocabulary() {
        // Placeholder: Load data from DB or mockup
        listModel.addElement("Hello - Hallo");
        listModel.addElement("World - Welt");
        listModel.addElement("Computer - Computer");
    }

    private void addVocabulary() {
        String word = wordField.getText();
        String translation = translationField.getText();
        if (!word.isEmpty() && !translation.isEmpty()) {
            listModel.addElement(word + " - " + translation);
            wordField.setText("");
            translationField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill both fields!");
        }
    }

    private void editVocabulary() {
        int selectedIndex = vocabularyList.getSelectedIndex();
        if (selectedIndex != -1) {
            String word = wordField.getText();
            String translation = translationField.getText();
            if (!word.isEmpty() && !translation.isEmpty()) {
                listModel.set(selectedIndex, word + " - " + translation);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill both fields!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an item to edit!");
        }
    }

    private void deleteVocabulary() {
        int selectedIndex = vocabularyList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Select an item to delete!");
        }
    }

    private void saveChanges() {
        // Placeholder for saving to the database
        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
    }
}
