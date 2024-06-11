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

        // Champs de formulaire
        JTextField userField = new JTextField(20);
        JTextField mdpField = new JTextField(20);
        
        // Label des champs
        JLabel userLabel = new JLabel("Nom :");
        JLabel mdpLabel = new JLabel("Mot de passe :");
        
        // Mettre en blanc
        userLabel.setForeground(Color.WHITE);
        mdpLabel.setForeground(Color.WHITE);

        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = new JButton("Connexion");
        button.addActionListener(e -> {
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

        // Ajout des composants au contentPane
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
