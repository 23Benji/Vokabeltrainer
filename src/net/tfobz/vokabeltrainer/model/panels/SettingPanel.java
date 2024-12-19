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

    public SettingPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBackground(new Color(58, 78, 66)); // Background color from image

        // Home Button
        JButton homeButton = new JButton(new ImageIcon("icons/home.png")); // Unicode for home icon
        homeButton.setBounds(20, 20, 40, 40);
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
        titleLabel.setForeground(new Color(222, 170, 121));
        titleLabel.setBounds(320, 20, 200, 55);
        add(titleLabel);

        // Import Button
        JButton importButton = new JButton("Import");
        importButton.setBounds(260, 100, 130, 50);
        importButton.setBackground(new Color(84, 120, 94));
        importButton.setBorderPainted(false);
        importButton.setFocusPainted(false);
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

                // Fetch all Lernkarteien from the database
                ArrayList<Lernkartei> lernkarteienList = (ArrayList<Lernkartei>) VokabeltrainerDB.getLernkarteien();

                // If there are Lernkarteien, display them in an option dialog
                if (lernkarteienList != null && !lernkarteienList.isEmpty()) {
                    String[] options = new String[lernkarteienList.size()];

                    for (int i = 0; i < lernkarteienList.size(); i++) {
                        options[i] = lernkarteienList.get(i).toString();
                    }

                    // Show option dialog to select Lernkartei
                    int selectedIndex = JOptionPane.showOptionDialog(null, "Select the Lernkartei:", "Lernkartei Selection",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    VokabeltrainerDB.importierenKarten(selectedIndex,selectedFile.getAbsolutePath());
                    if (selectedIndex != -1) {
                        Lernkartei selectedLernkartei = lernkarteienList.get(selectedIndex); // Get the selected Lernkartei object
                        int lernkarteiId = selectedLernkartei.getNummer();

                        // Import Karten
                        VokabeltrainerDB.importierenKarten(lernkarteiId, selectedFile.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(null, "No Lernkartei selected.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No Lernkartei available.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "File selection canceled.");
            }
        });



        add(importButton);


        JButton exportButton = new JButton("Export");
        exportButton.setBounds(410, 100, 130, 50);
        exportButton.setBackground(new Color(84, 120, 94));
        exportButton.setBorderPainted(false);
        exportButton.setFocusPainted(false);
        exportButton.setFont(new Font("Arial", Font.BOLD, 18));
        exportButton.setForeground(Color.white);
        exportButton.setIcon(new ImageIcon("icons/export.png"));
        add(exportButton);

        JLabel toggleLabel = new JLabel("Groß/Kleinschreibung");
        toggleLabel.setBounds(260, 180, 200, 30);
        toggleLabel.setForeground(Color.WHITE);
        toggleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(toggleLabel);

        JToggleButton toggleSwitch = new JToggleButton();
        toggleSwitch.setBounds(490, 186, 60, 20);
        toggleSwitch.setFont(new Font("Arial", Font.PLAIN, 10));
        toggleSwitch.setBorder(BorderFactory.createLineBorder(new Color(222, 170, 121), 1));
        toggleSwitch.setSelected(false);
        toggleSwitch.setFocusPainted(false);
        toggleSwitch.addActionListener(e -> {
            System.out.println("Toggle Switch: " + toggleSwitch.isSelected());
        });
        add(toggleSwitch);

        // Optional Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(260, 450, 280, 30);
        saveButton.setBackground(new Color(39, 85, 107));
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved"));
        add(saveButton);
    }
}
