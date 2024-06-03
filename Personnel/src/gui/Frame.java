package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Frame extends JFrame {
	
	public Frame() {
		
		// Configuration de la fenêtre
        super("LDO - Ligues Dynamiques et Organisées");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        // Création des composants
        JPanel contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(150, 60, 0, 60));

        // Champs de connexion
        JTextField userField = new JTextField("Nom"); 
        JTextField mdpField = new JTextField("Mot de passe"); 
        
        // Bouton de connexion
        JButton button = new JButton("Connexion");
        
        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Champ utilisateur
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        userField.setPreferredSize(new Dimension(200, 30));
        fieldsPanel.add(userField, gbc);

        // Champ mot de passe
        gbc.gridy = 1;
        mdpField.setPreferredSize(new Dimension(200, 30));
        fieldsPanel.add(mdpField, gbc);

        // Espacement vertical
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);

        // Bouton de connexion
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        button.setPreferredSize(new Dimension(100, 30));
        fieldsPanel.add(button, gbc);

        contentPane.add(fieldsPanel, BorderLayout.CENTER);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);

    }

	public static void main(String[] args) {
		Frame firstWindow = new Frame();
		firstWindow.setVisible(true);
	}

}
