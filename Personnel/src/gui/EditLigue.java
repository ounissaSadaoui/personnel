package gui;

import java.awt.*;
import javax.swing.*;

public class EditLigue extends JFrame {

    public EditLigue() {
    	
        // Configuration de la nouvelle fenêtre
        super("LDO - Ligues Dynamiques et Organisées");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création des composants principaux
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajout titre et sous-titre
        JLabel titleLabel = new JLabel("Modifier la ligue : ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JTextField ligueField = new JTextField("Nom", 20);

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton buttonLigue = new JButton("Valider les modifications");
        buttonLigue.addActionListener(e -> {
            Ligue newFrame = new Ligue();
            newFrame.setVisible(true);
        });

        buttonPanel.add(buttonLigue);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(ligueField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        
        // Ajout des composants au contentPane
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        titlePanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);
        buttonPanel.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);
    }
}
