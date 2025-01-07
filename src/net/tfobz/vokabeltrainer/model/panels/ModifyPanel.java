package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.model.Karte;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;

public class ModifyPanel extends JPanel {
    private JList<Lernkartei> lernkarteiList;
    private DefaultListModel<Lernkartei> lernkarteiListModel;
    private JTree fachTree;
    private JTextField wordField, translationField;
    private JButton addButton, editButton, deleteButton, saveButton, backButton;
    private MainFrame mainFrame;
    private VokabeltrainerDB database;

    public ModifyPanel(MainFrame mainFrame, VokabeltrainerDB database) {
        this.mainFrame = mainFrame;
        this.database = database;
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // Title Label
        JLabel titleLabel = new JLabel("Modify Vocabulary", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setBounds(0, 20, 800, 30);
        add(titleLabel);

        // Lernkartei List
        lernkarteiListModel = new DefaultListModel<>();
        lernkarteiList = new JList<>(lernkarteiListModel);
        lernkarteiList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Lernkartei) {
                    Lernkartei lernkartei = (Lernkartei) value;
                    setText(lernkartei.getName());
                }
                return this;
            }
        });
        JScrollPane lernkarteiScrollPane = new JScrollPane(lernkarteiList);
        lernkarteiScrollPane.setBounds(50, 70, 300, 200); // Größe und Position angepasst
        add(lernkarteiScrollPane);

        // Fach Tree (Directly under Lernkartei list)
        fachTree = new JTree();
        fachTree.setVisible(false);
        JScrollPane fachScrollPane = new JScrollPane(fachTree);
        fachScrollPane.setBounds(50, 280, 300, 200);  // Direkt unter der Lernkartei List
        add(fachScrollPane);

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

        loadLernkarteien();
    }

    private void loadLernkarteien() {
        try {
            List<Lernkartei> lernkarteien = database.getLernkarteien();
            lernkarteiListModel.clear();
            for (Lernkartei lernkartei : lernkarteien) {
                lernkarteiListModel.addElement(lernkartei);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading Lernkarteien: " + e.getMessage());
        }

        lernkarteiList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Lernkartei selectedLernkartei = lernkarteiList.getSelectedValue();
                if (selectedLernkartei != null) {
                    loadFaecher(selectedLernkartei.getNummer());
                }
            }
        });
    }

    private void loadFaecher(int nummerLernkartei) {
        try {
            List<Fach> faecher = database.getFaecher(nummerLernkartei);

            // Creating the root node for the tree
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fächer");

            // Adding Fach nodes as children
            for (Fach fach : faecher) {
                DefaultMutableTreeNode fachNode = new DefaultMutableTreeNode(fach.getBeschreibung());
                root.add(fachNode);
                loadKartenForFach(fach, fachNode); // Add Karten to each Fach node
            }

            // Create a tree model and set the tree
            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            fachTree.setModel(treeModel);
            fachTree.setVisible(true);  // Show Fach tree after Lernkartei is selected
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading Fächer: " + e.getMessage());
        }
    }

    private void loadKartenForFach(Fach fach, DefaultMutableTreeNode fachNode) {
        try {
            List<Karte> karten = database.getKarten(fach.getNummer());
            for (Karte karte : karten) {
                fachNode.add(new DefaultMutableTreeNode(karte.getFrage() + " - " + karte.getAntwort()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading Karten for Fach: " + e.getMessage());
        }
    }

    private void addVocabulary() {
        String word = wordField.getText();
        String translation = translationField.getText();
        if (!word.isEmpty() && !translation.isEmpty()) {
            Karte karte = new Karte();
            karte.setFrage(word);
            karte.setAntwort(translation);
            try {
                // Assuming we need to pass the selected Lernkartei number and Fach number
                Lernkartei selectedLernkartei = lernkarteiList.getSelectedValue();
                Fach selectedFach = (Fach) fachTree.getLastSelectedPathComponent();

                // Hinzufügen der Karte zur Liste des ausgewählten Fachs
                selectedFach.addKarte(karte);  // Methode zum Hinzufügen einer Karte zum Fach

                // Optional: Wenn die Karte auch der Lernkartei hinzugefügt werden soll
                selectedLernkartei.addKarteToFach(selectedFach, karte);  // Methode, um Karte zur Lernkartei hinzuzufügen

                wordField.setText("");
                translationField.setText("");
                JOptionPane.showMessageDialog(this, "Vocabulary added successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding vocabulary: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill both fields!");
        }
    }

    private void editVocabulary() {
        // Edit Vocabulary logic can stay the same
    }

    private void deleteVocabulary() {
        // Delete Vocabulary logic can stay the same
    }

    private void saveChanges() {
        try {
            // Save changes logic
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving changes: " + e.getMessage());
        }
    }
}
