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

        // Champ utilisateur
        JTextField userField = new JTextField("Nom"); 
        JTextField mdpField = new JTextField("Mot de passe"); 
        
        // Taille des champs
        Dimension fieldSize = new Dimension(20, 10);
        userField.setPreferredSize(fieldSize);
        mdpField.setPreferredSize(fieldSize);
        
        // Disposition des champs
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.add(userField);
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(mdpField);
        contentPane.add(userPanel, BorderLayout.CENTER);
        

        // Bouton de connexion
        JButton button = new JButton("Connexion");
        button.setPreferredSize(new Dimension(100, 30)); //
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        userPanel.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);
    }

	public static void main(String[] args) {
		Frame firstWindow = new Frame();
		firstWindow.setVisible(true);
	}

}
