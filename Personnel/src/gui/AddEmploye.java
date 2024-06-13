package gui;

import java.awt.*;
import javax.swing.*;

import jdbc.JDBC;

import java.time.LocalDate;
import personnel.*;

public class AddEmploye extends MainFrame {
    private Ligue ligue;
    private GererEmploye parentFrame;

    public AddEmploye(Ligue ligue, GererEmploye parentFrame) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.ligue = ligue;
        this.parentFrame = parentFrame;

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
        
        // Création d'un bouton pour ajouter l'employé
        
            // Bouton "Ajouter l'employé"
            JButton button = GuiUtils.createButton("Ajouter l'employé", e -> {
                String nom = nameField.getText();
                String prenom = lastNameField.getText();
                String mail = mailField.getText();
                String password = pwdField.getText();
                LocalDate dateArrivee = LocalDate.parse(dateArrField.getText());

                try {
                    Employe nouvelEmploye = ligue.addEmploye(nom, prenom, mail, password, dateArrivee, null);
                  /*  parentFrame.loadEmployesData();*/
                    JOptionPane.showMessageDialog(this, "Employé ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (DateInvalide | SauvegardeImpossible ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'employé : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
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

