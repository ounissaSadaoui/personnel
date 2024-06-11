package gui;

import java.awt.*;
import javax.swing.*;

public class Frame extends MainFrame {

    public Frame() {

        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        
        JPanel contentPane = getContentPanePanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        // Ajout logo
        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("/Users/cynthia/Documents/itic_paris/dev/personnel/Personnel/docs/logo.png")
                .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Champs de formulaire
        JTextField userField = GuiUtils.createTextField(20);
        JTextField mdpField = GuiUtils.createTextField(20);
        
        // Label des champs
        JLabel userLabel = GuiUtils.createLabel("Nom :", Color.WHITE);
        JLabel mdpLabel = GuiUtils.createLabel("Mot de passe :", Color.WHITE);

        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = GuiUtils.createButton("Connexion", e -> {
            GererLigue newFrame = new GererLigue();
            newFrame.setVisible(true);
        });

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(GuiUtils.BGcolor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(mdpLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(mdpField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        
        gbc.gridy++;
        fieldsPanel.add(button, gbc);
        
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
}
