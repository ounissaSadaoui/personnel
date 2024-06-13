/*package gui;

import java.awt.*;
import javax.swing.*;

import personnel.DateInvalide;
import personnel.SauvegardeImpossible;

public class EditEmploye extends MainFrame {

    public EditEmploye() {
    	
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        // Récupération du panneau de contenu
        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Modifier : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Champs de formulaire
        JTextField nameField = GuiUtils.createTextField(20);
        JTextField lastNameField = GuiUtils.createTextField(20);
        JTextField mailField = GuiUtils.createTextField(20);
        JTextField pwdField = GuiUtils.createTextField(20);
        JTextField dateArrField = GuiUtils.createTextField(20);
        JTextField dateDepField = GuiUtils.createTextField(20);

        // Label des champs 
        JLabel nameLabel = GuiUtils.createLabel("Nom: ", Color.WHITE);
        JLabel lastNameLabel = GuiUtils.createLabel("Prénom: ", Color.WHITE);
        JLabel mailLabel = GuiUtils.createLabel("Mail: ", Color.WHITE);
        JLabel pwdLabel = GuiUtils.createLabel("Mot de passe: ", Color.WHITE);
        JLabel dateArrLabel = GuiUtils.createLabel("Date d'arrivée: ", Color.WHITE);
        JLabel dateDepLabel = GuiUtils.createLabel("Date de départ: ", Color.WHITE);

        // Création bouton 
        JButton button = GuiUtils.createButton("Valider les modifications", e -> {
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

        // Ajouter les labels et les champs de texte
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
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        fieldsPanel.add(dateDepLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(dateDepField, gbc);

        // Bouton
        gbc.gridx = 0;
        gbc.gridy = 6;
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
}*/
package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;

import personnel.DateInvalide;
import personnel.Employe;
import personnel.SauvegardeImpossible;
import jdbc.JDBC;

public class EditEmploye extends MainFrame {

    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField mailField;
    private JTextField pwdField;
    private JTextField dateArrField;
    private JTextField dateDepField;
    private Employe employe;

    public EditEmploye(Employe employe) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.employe = employe;

        // Récupération du panneau de contenu
        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Modifier : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Champs de formulaire
        nameField = GuiUtils.createTextField(20);
        lastNameField = GuiUtils.createTextField(20);
        mailField = GuiUtils.createTextField(20);
        pwdField = GuiUtils.createTextField(20);
        dateArrField = GuiUtils.createTextField(20);
        dateDepField = GuiUtils.createTextField(20);

        // Pré-remplir les champs avec les informations de l'employé
        nameField.setText(employe.getNom());
        lastNameField.setText(employe.getPrenom());
        mailField.setText(employe.getMail());
        pwdField.setText(employe.getPassword());
        dateArrField.setText(employe.getDateArrivee() != null ? employe.getDateArrivee().toString() : "");
        dateDepField.setText(employe.getDateDepart() != null ? employe.getDateDepart().toString() : "");

        // Label des champs 
        JLabel nameLabel = GuiUtils.createLabel("Nom: ", Color.WHITE);
        JLabel lastNameLabel = GuiUtils.createLabel("Prénom: ", Color.WHITE);
        JLabel mailLabel = GuiUtils.createLabel("Mail: ", Color.WHITE);
        JLabel pwdLabel = GuiUtils.createLabel("Mot de passe: ", Color.WHITE);
        JLabel dateArrLabel = GuiUtils.createLabel("Date d'arrivée: ", Color.WHITE);
        JLabel dateDepLabel = GuiUtils.createLabel("Date de départ: ", Color.WHITE);

        // Création bouton 
        JButton button = GuiUtils.createButton("Valider les modifications", e -> {
            try {
				employe.setNom(nameField.getText());
			} catch (SauvegardeImpossible | DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				employe.setPrenom(lastNameField.getText());
			} catch (SauvegardeImpossible | DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				employe.setMail(mailField.getText());
			} catch (SauvegardeImpossible | DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				employe.setPassword(pwdField.getText());
			} catch (SauvegardeImpossible | DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            try {
                employe.setDateArrivee(LocalDate.parse(dateArrField.getText()));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Date d'arrivée invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SauvegardeImpossible e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            try {
                employe.setDateDepart(dateDepField.getText().isEmpty() ? null : LocalDate.parse(dateDepField.getText()));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Date de départ invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (DateInvalide e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SauvegardeImpossible e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            try {
                JDBC jdbc = new JDBC();
                jdbc.update(employe);
                JOptionPane.showMessageDialog(this, "Employé modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (SauvegardeImpossible ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'employé : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajouter les labels et les champs de texte
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
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        fieldsPanel.add(dateDepLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(dateDepField, gbc);

        // Bouton
        gbc.gridx = 0;
        gbc.gridy = 6;
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
