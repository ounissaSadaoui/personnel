package gui;

import java.awt.*;
import javax.swing.*;

public class AddLigue extends MainFrame {

    public AddLigue() {

        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Ajouter une nouvelle ligue : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Champs de connexion
        JTextField ligueField = GuiUtils.createTextField(20);
        
        // Label du champs
        JLabel nomLabel = GuiUtils.createLabel("Nom :", Color.WHITE);

        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = GuiUtils.createButton("Ajouter la ligue", e -> {
            GererLigue newFrame = new GererLigue();
            newFrame.setVisible(true);
        });

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nomLabel, gbc);
        gbc.gridx++;
        fieldsPanel.add(ligueField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(button, gbc);

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        
        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
}
