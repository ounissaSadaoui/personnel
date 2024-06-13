package gui;

import java.awt.*;
import javax.swing.*;

import jdbc.JDBC;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class EditLigue extends MainFrame {

    private JTextField nameField;
    private Ligue ligueToUpdate;

    public EditLigue(Ligue ligue) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.ligueToUpdate = ligue;

        JPanel contentPane = getContentPanePanel();

        // Ajout titre et sous-titre
        JLabel titleLabel = GuiUtils.createLabel("Modifier la ligue : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Creation champ
        JLabel nameLabel = GuiUtils.createLabel("Nom :", Color.WHITE);
        nameField = GuiUtils.createTextField(20);
        nameField.setText(ligue.getNom()); // Pré-remplir avec le nom actuel de la ligue

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton buttonLigue = GuiUtils.createButton("Valider les modifications", e -> {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                // Mettre à jour la ligue avec le nouveau nom
                try {
					ligueToUpdate.setNom(newName);
				} catch (SauvegardeImpossible e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                // Appeler la méthode update de JDBC pour sauvegarder les modifications
                try {
                    JDBC jdbc = new JDBC(); // creation d'une instance de JDBC
                    jdbc.update(ligueToUpdate); // Appel de la méthode update avec la ligue à mettre à jour
                    jdbc.close(); 
                    JOptionPane.showMessageDialog(this, "Ligue mise à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose(); // on ferme la fenetre, on laisse ou pas ?
                } catch (SauvegardeImpossible ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de la ligue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un nom pour la ligue.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(buttonLigue);

        // Creation du tableau
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridy++;
        fieldsPanel.add(nameField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPane.add(fieldsPanel, BorderLayout.CENTER);

        // Ajout des composants au contentPane
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        fieldsPanel.setBackground(GuiUtils.BGcolor);
        titlePanel.setBackground(GuiUtils.BGcolor);
        buttonPanel.setBackground(GuiUtils.BGcolor);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
