package net.tfobz.vokabeltrainer.model.panels;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.MainFrame;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class SettingPanel extends JPanel {
    MainFrame mainFrame;
    Color darkGreen = new Color(101, 146, 135);
    Color lightGreen = new Color(58, 78, 66);
    Color creeme = new Color(255, 230, 169);
    Color lightBrown = new Color(222, 170, 121);
    Color darkBlue = new Color(39, 85, 107);

    public SettingPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBackground(lightGreen); // Background color from image

        // Home Button
        JButton homeButton = new JButton(new ImageIcon("icons/home.png")); // Unicode for home icon
        homeButton.setBounds(20, 20, 64, 64);
        homeButton.setFont(new Font("Arial", Font.BOLD, 24));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> {
            mainFrame.switchToHomePanel();
        });
        add(homeButton);

        // Title Label
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(lightBrown);
        titleLabel.setBounds(320, 20, 200, 55);
        add(titleLabel);

        // Import Button
        JButton importButton = new JButton("Import");
        importButton.setBounds(260, 100, 130, 50);
        importButton.setBackground(lightGreen);
        importButton.setBorderPainted(false);
        importButton.setFocusPainted(false);
        importButton.setContentAreaFilled(false);
        importButton.setFont(new Font("Arial", Font.BOLD, 18));
        importButton.setForeground(Color.white);
        importButton.setIcon(new ImageIcon("icons/import.png"));
        importButton.addActionListener(e -> {
            // Create a JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a File");

            // Show the dialog and get the result
            int result = fileChooser.showOpenDialog(null); // Pass null for parent component

            // Process the result
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    // Fetch all Lernkarteien from the database
                    ArrayList<Lernkartei> lernkarteienList = (ArrayList<Lernkartei>) VokabeltrainerDB.getLernkarteien();

                    // If there are Lernkarteien, display them in an option dialog
                    if (lernkarteienList != null && !lernkarteienList.isEmpty()) {
                        String[] options = new String[lernkarteienList.size()];

                        for (int i = 0; i < lernkarteienList.size(); i++) {
                            options[i] = lernkarteienList.get(i).toString();
                        }

                        // Show option dialog to select Lernkartei
                        int selectedIndex = JOptionPane.showOptionDialog(
                                null,
                                "!Warning! All the Fächer and Karten of the chosen Lernkartei will be deleted befor the import!\nChoose Lernkartei:",
                                "Lernkartei Selection",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                        if (selectedIndex != -1) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to proceed? This action cannot be undone.",
                                    "Confirm Deletion",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                            if (confirm == JOptionPane.OK_OPTION) {
                                Lernkartei selectedLernkartei = lernkarteienList.get(selectedIndex);
                                int lernkarteiId = selectedLernkartei.getNummer();
                                System.out.println("Selected Lernkartei ID: " + lernkarteiId);

                                // Import Karten
                                int importResult = VokabeltrainerDB.importierenKarten(lernkarteiId, selectedFile.getAbsolutePath());
                                switch (importResult) {
                                    case -1:
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "Import failed due to an import error.",
                                                "Import Error",
                                                JOptionPane.ERROR_MESSAGE
                                        );
                                        break;
                                    case -2:
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "The specified file was not found.",
                                                "File Not Found",
                                                JOptionPane.ERROR_MESSAGE
                                        );
                                        break;
                                    case -3:
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "The selected Lernkartei does not exist.",
                                                "Lernkartei Not Found",
                                                JOptionPane.ERROR_MESSAGE
                                        );
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "Import successful!",
                                                "Success",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                        break;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Import cancelled.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No Lernkartei selected.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No Lernkartei available.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred during the import process: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "File selection canceled.");
            }
        });


        add(importButton);


        JButton exportButton = new JButton("Export");
        exportButton.setBounds(410, 100, 130, 50);
        exportButton.setBackground(lightGreen);
        exportButton.setBorderPainted(false);
        exportButton.setFocusPainted(false);
        exportButton.setContentAreaFilled(false);
        exportButton.setFont(new Font("Arial", Font.BOLD, 18));
        exportButton.setForeground(Color.white);
        exportButton.setIcon(new ImageIcon("icons/export.png"));
        exportButton.addActionListener(e -> {
            // Create a JFileChooser for export
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Export Location");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // Show the save dialog
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File exportFile = fileChooser.getSelectedFile();

                try {
                    // Fetch Lernkarteien
                    ArrayList<Lernkartei> lernkarteienList = (ArrayList<Lernkartei>) VokabeltrainerDB.getLernkarteien();
                    if (lernkarteienList != null && !lernkarteienList.isEmpty()) {
                        String[] options = new String[lernkarteienList.size()];

                        for (int i = 0; i < lernkarteienList.size(); i++) {
                            options[i] = lernkarteienList.get(i).toString();
                        }

                        // Show option dialog to select Lernkartei
                        int selectedIndex = JOptionPane.showOptionDialog(
                                null,
                                "Select the Lernkartei for export:",
                                "Lernkartei Selection",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                        if (selectedIndex != -1) {
                            Lernkartei selectedLernkartei = lernkarteienList.get(selectedIndex);
                            int lernkarteiId = selectedLernkartei.getNummer();

                            // Ask whether to include "Fächer" in export
                            int includeFaechern = JOptionPane.showConfirmDialog(
                                    null,
                                    "Include Fächer in the export?",
                                    "Export Option",
                                    JOptionPane.YES_NO_OPTION
                            );

                            boolean mitFaechern = (includeFaechern == JOptionPane.YES_OPTION);

                            // Perform export
                            int exportResult = VokabeltrainerDB.exportierenKarten(
                                    lernkarteiId,
                                    exportFile.getAbsolutePath(),
                                    mitFaechern
                            );

                            if (exportResult == 0) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Export successful!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            } else if (exportResult == -3) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Selected Lernkartei does not exist.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Export failed. Please try again.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No Lernkartei selected.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No Lernkartei available.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred during the export process: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Export canceled.");
            }
        });


        add(exportButton);

        JLabel toggleLabel = new JLabel("Groß/Kleinschreibung");
        toggleLabel.setBounds(260, 180, 200, 30);
        toggleLabel.setForeground(Color.WHITE);
        toggleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(toggleLabel);

        JToggleButton toggleSwitch = new JToggleButton();
        toggleSwitch.setBounds(490, 186, 60, 20);
        toggleSwitch.setFont(new Font("Arial", Font.PLAIN, 10));
        toggleSwitch.setBorder(BorderFactory.createLineBorder(lightBrown, 1));
        toggleSwitch.setSelected(false);
        toggleSwitch.setFocusPainted(false);
        toggleSwitch.addActionListener(e -> {
            mainFrame.setGrossKleinschreibung(toggleSwitch.isSelected());
        });
        add(toggleSwitch);

        // Optional Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(260, 450, 280, 30);
        saveButton.setBackground(darkBlue);
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved"));
        add(saveButton);
    }


    public Color getLightBrown() {
        return lightBrown;
    }


    public Color getCreeme() {
        return creeme;
    }


    public Color getLightGreen() {
        return lightGreen;
    }


    public Color getDarkGreen() {
        return darkGreen;
    }

    public Color getDarkBlue() {
        return darkBlue;
    }
}
