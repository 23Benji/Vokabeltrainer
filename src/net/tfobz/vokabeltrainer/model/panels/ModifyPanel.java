package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ModifyPanel extends JPanel {
    private MainFrame mainFrame;

    public ModifyPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Set layout and background
        setLayout(null);
        setBackground(new Color(58, 78, 66));

        // Create buttons
        JButton addLernkarteiButton = createButton("Add");
        JButton addFachButton = createButton("Add");
        JButton addKarteButton = createButton("Add");
        JButton removeLernkarteiButton = createButton("Remove");
        JButton removeFachButton = createButton("Remove");
        JButton removeKarteButton = createButton("Remove");

        // Create home button with icon
        JButton homeButton = createHomeButton();

        // Create labels
        JLabel ModifyLabel = createLabel("Modify");
        ModifyLabel.setBounds(250, 50, 250, 70);
        ModifyLabel.setFont(new Font("Verdana", Font.BOLD, 64));
        JLabel lernkarteiLabel = createLabel("Lernkartei");
        JLabel fachLabel = createLabel("Fach");
        JLabel karteLabel = createLabel("Karte");

        // Calculate centered positions
        int panelWidth = 800;
        int panelHeight = 600;

        int buttonWidth = 160;
        int buttonHeight = 60;
        int labelHeight = 30;
        int verticalSpacing = 20;

        int columnSpacing = 80;
        int firstColumnX = (panelWidth - (3 * buttonWidth + 2 * columnSpacing)) / 2;
        int secondColumnX = firstColumnX + buttonWidth + columnSpacing;
        int thirdColumnX = secondColumnX + buttonWidth + columnSpacing;

        int topY = (panelHeight - (2 * buttonHeight + labelHeight + verticalSpacing)) / 2;

        // Set bounds for home button
        homeButton.setBounds(10, 10, 50, 50); // Adjusted for size and placement

        // Set bounds for labels
        lernkarteiLabel.setBounds(firstColumnX, topY, buttonWidth, labelHeight);
        fachLabel.setBounds(secondColumnX, topY, buttonWidth, labelHeight);
        karteLabel.setBounds(thirdColumnX, topY, buttonWidth, labelHeight);

        // Set bounds for buttons
        addLernkarteiButton.setBounds(firstColumnX, topY + labelHeight + verticalSpacing, buttonWidth, buttonHeight);
        removeLernkarteiButton.setBounds(firstColumnX, topY + labelHeight + buttonHeight + 2 * verticalSpacing, buttonWidth, buttonHeight);

        addFachButton.setBounds(secondColumnX, topY + labelHeight + verticalSpacing, buttonWidth, buttonHeight);
        removeFachButton.setBounds(secondColumnX, topY + labelHeight + buttonHeight + 2 * verticalSpacing, buttonWidth, buttonHeight);

        addKarteButton.setBounds(thirdColumnX, topY + labelHeight + verticalSpacing, buttonWidth, buttonHeight);
        removeKarteButton.setBounds(thirdColumnX, topY + labelHeight + buttonHeight + 2 * verticalSpacing, buttonWidth, buttonHeight);

        // Add components to panel
        add(ModifyLabel);
        add(homeButton);
        add(lernkarteiLabel);
        add(fachLabel);
        add(karteLabel);
        add(addLernkarteiButton);
        add(removeLernkarteiButton);
        add(addFachButton);
        add(removeFachButton);
        add(addKarteButton);
        add(removeKarteButton);

        // Action Listeners
        addFachButton.addActionListener(e -> showAddFachDialog());
        addLernkarteiButton.addActionListener(e -> showAddLernkarteiDialog());
        addKarteButton.addActionListener(e -> showAddKarteDialog());
        removeFachButton.addActionListener(e -> showRemoveAllFaecherDialog());
        removeLernkarteiButton.addActionListener(e -> showRemoveLernkarteiDialog());
        removeKarteButton.addActionListener(e -> showRemoveKarteDialog());
        homeButton.addActionListener(e -> navigateHome());
    }

    // Helper method to create buttons with consistent style
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(101, 146, 135));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(39, 85, 107), 2));
        return button;
    }

    // Helper method to create labels with consistent style
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // Helper method to create the home button with an icon
    private JButton createHomeButton() {
        JButton button = new JButton(new ImageIcon("icons/home.png"));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        return button;
    }

    // --- Home Navigation ---
    private void navigateHome() {
        mainFrame.switchToHomePanel(); // Assumes MainFrame has a method to switch to the home panel
    }

    private void showAddFachDialog() {
        JTextField beschreibungField = new JTextField();
        JTextField intervallField = new JTextField();

        // Create JComboBox for selecting FachNummer from available options
        JComboBox<String> nummerComboBox = new JComboBox<>(mainFrame.getLanguageLabels()); // Use preloaded labels

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nummer:"));
        panel.add(nummerComboBox);
        panel.add(new JLabel("Beschreibung:"));
        panel.add(beschreibungField);
        panel.add(new JLabel("Erinnerungsintervall:"));
        panel.add(intervallField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Fach", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Get the selected FachNummer from the JComboBox
                String selectedFachLabel = (String) nummerComboBox.getSelectedItem();
                System.out.println("Selected: " + selectedFachLabel);
                int selectedIndex = nummerComboBox.getSelectedIndex(); // Get the index of selected item
                System.out.println("Selected Index: " + selectedIndex);

                // Get the corresponding Lernkartei ID from the list (based on the selected index)
                int lernkarteiId = mainFrame.getLernkarteiIdForIndex(selectedIndex); // Use method in MainFrame to get the Lernkartei ID
                System.out.println("Lernkartei ID: " + lernkarteiId);

                // Create the Fach object
                Fach fach = new Fach();
                fach.setBeschreibung(beschreibungField.getText());
                fach.setErinnerungsIntervall(Integer.parseInt(intervallField.getText()));
                fach.setGelerntAm(new Date());

                // Add Fach to the database with the corresponding Lernkartei ID
                int fachId = VokabeltrainerDB.hinzufuegenFach(lernkarteiId, fach);
                JOptionPane.showMessageDialog(this, "Fach added successfully! ID: " + fachId);

                // After adding, you can verify if the Fach is correctly added
                fach = VokabeltrainerDB.getFach(fachId); // Get the Fach back from the DB
                if (fach != null) {
                    System.out.println("Fach added: " + fach.getNummer() + " - " + fach.getBeschreibung());
                } else {
                    JOptionPane.showMessageDialog(this, "Fach could not be retrieved from the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }





    // --- Add Lernkartei ---
    private void showAddLernkarteiDialog() {
        JTextField beschreibungField = new JTextField();
        JTextField wortEinsBeschreibungField = new JTextField();
        JTextField wortZweiBeschreibungField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Beschreibung:"));
        panel.add(beschreibungField);
        panel.add(new JLabel("Wort Eins Beschreibung:"));
        panel.add(wortEinsBeschreibungField);
        panel.add(new JLabel("Wort Zwei Beschreibung:"));
        panel.add(wortZweiBeschreibungField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Lernkartei", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Lernkartei lernkartei = new Lernkartei();
                lernkartei.setBeschreibung(beschreibungField.getText());
                lernkartei.setWortEinsBeschreibung(wortEinsBeschreibungField.getText());
                lernkartei.setWortZweiBeschreibung(wortZweiBeschreibungField.getText());

                int status = VokabeltrainerDB.hinzufuegenLernkartei(lernkartei);

                if (status == 0) {
                    JOptionPane.showMessageDialog(this, "Lernkartei erfolgreich hinzugefügt! ID: " + lernkartei.getNummer());
                } else {
                    String fehlerMeldung = String.join("\n", (CharSequence) lernkartei.getFehler());
                    JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen:\n" + fehlerMeldung, "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ungültiges Zahlenformat.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void showAddKarteDialog() {
        JTextField wortEinsField = new JTextField();
        JTextField wortZweiField = new JTextField();
        JCheckBox richtungCheck = new JCheckBox("Richtung?");
        JCheckBox grossKleinCheck = new JCheckBox("Groß-/Kleinschreibung?");

        // Create JComboBox for selecting Lernkartei from available options
        JComboBox<String> lernkarteiComboBox = new JComboBox<>(mainFrame.getLanguageLabels()); // Use preloaded Lernkartei labels


        JPanel panel = new JPanel(new GridLayout(8, 2));  // Adjust grid layout to add Lernkartei and Fach selection
        panel.add(new JLabel("Lernkartei:"));
        panel.add(lernkarteiComboBox);
        panel.add(new JLabel("Wort Eins:"));
        panel.add(wortEinsField);
        panel.add(new JLabel("Wort Zwei:"));
        panel.add(wortZweiField);
        panel.add(new JLabel("Richtung:"));
        panel.add(richtungCheck);
        panel.add(new JLabel("Groß-/Kleinschreibung:"));
        panel.add(grossKleinCheck);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Karte", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Karte karte = new Karte();
                karte.setWortEins(wortEinsField.getText());
                karte.setWortZwei(wortZweiField.getText());
                karte.setRichtung(richtungCheck.isSelected());
                karte.setGrossKleinschreibung(grossKleinCheck.isSelected());

                // Get the selected Lernkartei ID from the JComboBox
                int selectedLernkarteiIndex = lernkarteiComboBox.getSelectedIndex();
                int lernkarteiId = mainFrame.getLernkarteiIdForIndex(selectedLernkarteiIndex); // Fetch Lernkartei ID


                // Add Karte to the database with the correct Lernkartei ID and Fach ID
                int karteId = VokabeltrainerDB.hinzufuegenKarte(lernkarteiId, karte);

                // Show confirmation message
                JOptionPane.showMessageDialog(this, "Karte added successfully! ID: " + karteId);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

//    // Helper method to update the Fach JComboBox based on selected Lernkartei
//    private void updateFachComboBox(int lernkarteiId, JComboBox<String> fachComboBox) {
//        // Assuming getFacherByLernkartei is a method that retrieves Fach options based on Lernkartei ID
//        ArrayList<Fach> availableFacher = (ArrayList<Fach>) VokabeltrainerDB.getFaecher(lernkarteiId);
//
//        // Clear current Fach options
//        fachComboBox.removeAllItems();
//
//        for (int i = 0; i < availableFacher.size(); i++) {
//            fachComboBox.addItem(availableFacher.get(i).toString().split(",")[0]);  // Add only the left part of the string
//        }
//    }




    // --- Remove Fach ---
    private void showRemoveAllFaecherDialog() {
        // Create a JComboBox to display the list of available Facher (using Fach labels)
        JComboBox<String> lernKarteiComboBox = new JComboBox<>(mainFrame.getLanguageLabels()); // Use preloaded labels

        // Create a panel with the combo box
        JPanel panel = new JPanel();
        panel.add(new JLabel("Remove all Facher from Lernkartei:"));
        panel.add(lernKarteiComboBox);

        // Show the dialog with the combo box
        int result = JOptionPane.showConfirmDialog(this, panel, "Remove All Facher", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {


            int lernkarteiId = mainFrame.getLernkarteiIdForIndex(lernKarteiComboBox.getSelectedIndex());
            // Get the selected Fach ID

            // Remove the Fach from the list and database
            int status = VokabeltrainerDB.loeschenAlleFaecher(lernkarteiId); // Assuming this method removes the Fach by ID

            if (status != -1) {
                JOptionPane.showMessageDialog(this, "All Faecher removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error removing Fach.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            mainFrame.preloadLanguageLabels();
        }
    }


    // --- Remove Lernkartei ---
    private void showRemoveLernkarteiDialog() {
        // Create a JComboBox to display the list of Lernkarteien (using language labels)
        JComboBox<String> lernkarteiComboBox = new JComboBox<>(mainFrame.getLanguageLabels());

        // Create a panel with the combo box
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Lernkartei to remove:"));
        panel.add(lernkarteiComboBox);

        // Show the dialog with the combo box
        int result = JOptionPane.showConfirmDialog(this, panel, "Remove Lernkartei", JOptionPane.OK_CANCEL_OPTION);

        int  lernkarteiId = mainFrame.getLernkarteiIdForIndex(lernkarteiComboBox.getSelectedIndex());

        if (result == JOptionPane.OK_OPTION) {

                // Remove the Lernkartei from the list and database
                int status = VokabeltrainerDB.loeschenLernkartei(lernkarteiId); // Assuming this method removes the Lernkartei by ID

                if (status!=-1) {
                    JOptionPane.showMessageDialog(this, "Lernkartei with ID " + lernkarteiId + " removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error removing Lernkartei.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                mainFrame.preloadLanguageLabels();

        }
    }


    // --- Remove Karte ---
    private void showRemoveKarteDialog() {
        // Create a JComboBox to display the list of Lernkarteien (using language labels)
        JComboBox<String> lernkarteiComboBox = new JComboBox<>(mainFrame.getLanguageLabels());

        // Create a panel with the combo box
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Karte to remove:"));
        panel.add(lernkarteiComboBox);

        // Show the dialog with the combo box
        int result = JOptionPane.showConfirmDialog(this, panel, "Remove Lernkartei", JOptionPane.OK_CANCEL_OPTION);

        int  lernkarteiId = mainFrame.getLernkarteiIdForIndex(lernkarteiComboBox.getSelectedIndex());

        if (result == JOptionPane.OK_OPTION) {

            // Remove the Lernkartei from the list and database
            int status = VokabeltrainerDB.loeschenLernkartei(lernkarteiId); // Assuming this method removes the Lernkartei by ID

            if (status!=-1) {
                JOptionPane.showMessageDialog(this, "Lernkartei with ID " + lernkarteiId + " removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error removing Lernkartei.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            mainFrame.preloadLanguageLabels();

        }
    }
//    private void updateKartenComboBox(int lernkarteiId, JComboBox<String> kartenComboBox) {
//
//        ArrayList<Fach> availableKarten = (ArrayList<Fach>) VokabeltrainerDB.getKarten();
//
//        kartenComboBox.removeAllItems();
//
//        for (int i = 0; i < availableFacher.size(); i++) {
//            fachComboBox.addItem(availableFacher.get(i).toString();
//        }
//    }
    //Kann nicht functionieren...DB fragt nach Fach...loeschmethode fragt nach Karten Nummer!!
    // Es gibt keine Methode die alle Karten der LErnkartei nimmt
}
