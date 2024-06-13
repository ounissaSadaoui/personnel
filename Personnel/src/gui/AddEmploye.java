package gui;

import java.awt.*;
import javax.swing.*;

import personnel.DateInvalide;
import personnel.SauvegardeImpossible;

public class AddEmploye extends MainFrame {

    public AddEmploye() {

        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        // Récupération du panneau de contenu
        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Ajouter un nouvel employé : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Champs de formulaire
        JTextField nameField = GuiUtils.createTextField(20);
        JTextField lastNameField = GuiUtils.createTextField(20);
        JTextField mailField = GuiUtils.createTextField(20);
        JTextField pwdField = GuiUtils.createTextField(20);
        JTextField dateArrField = GuiUtils.createTextField(20);

        // Label des champs 
        JLabel nameLabel = GuiUtils.createLabel("Nom: ", Color.WHITE);
        JLabel lastNameLabel = GuiUtils.createLabel("Prénom: ", Color.WHITE);
        JLabel mailLabel = GuiUtils.createLabel("Mail: ", Color.WHITE);
        JLabel pwdLabel = GuiUtils.createLabel("Mot de passe: ", Color.WHITE);
        JLabel dateArrLabel = GuiUtils.createLabel("Date d'arrivée: ", Color.WHITE);
        
        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = GuiUtils.createButton("Ajouter l'employé", e -> {
            GererEmploye newFrame = null;
			try {
				newFrame = new GererEmploye(null);
			} catch (DateInvalide | SauvegardeImpossible e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            newFrame.setVisible(true);
        });

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(mailLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(mailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(pwdLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(pwdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPanel.add(dateArrLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(dateArrField, gbc);

        // Bouton
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(button, gbc);

        // Ajout des composants au contentPane
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
