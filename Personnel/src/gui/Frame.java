package gui;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {

    public Frame() {

        // Configuration de la fenêtre
        super("LDO - Ligues Dynamiques et Organisées");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création des composants
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        // Ajout logo
        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("/Users/cynthia/Documents/itic_paris/dev/personnel/Personnel/docs/logo.png")
                .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Champs de connexion
        JTextField userField = new JTextField("Nom", 20);
        JTextField mdpField = new JTextField("Mot de passe", 20);

        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = new JButton("Connexion");
        button.addActionListener(e -> {
            GererLigue newFrame = new GererLigue();
            newFrame.setVisible(true);
        });

        button.setPreferredSize(new Dimension(100, 30));
        contentPane.add(button, BorderLayout.CENTER);
        setContentPane(contentPane);

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(userField, gbc);
        gbc.gridy++;
        fieldsPanel.add(mdpField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(button, gbc);

        contentPane.add(logoLabel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
}
