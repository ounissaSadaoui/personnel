
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import personnel.GestionPersonnel;
import personnel.SauvegardeImpossible;

public class AddLigue extends MainFrame {

    private GestionPersonnel gestionPersonnel;
    private JTextField ligueField;

    public AddLigue(GestionPersonnel gestionPersonnel) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.gestionPersonnel = gestionPersonnel;

        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Ajouter une nouvelle ligue : ", Color.WHITE);

        // Champs de connexion
        ligueField = GuiUtils.createTextField(20);

        // Label du champ
        JLabel nomLabel = GuiUtils.createLabel("Nom :", Color.WHITE);

        // Création d'un bouton pour ajouter la ligue
        JButton button = GuiUtils.createButton("Ajouter la ligue", this::handleAddLigue);

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nomLabel, gbc);
        gbc.gridx++;
        fieldsPanel.add(ligueField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(button, gbc);

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);
    }

    private void handleAddLigue(ActionEvent e) {
        String ligueName = ligueField.getText();

        if (ligueName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom de la ligue ne peut pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            gestionPersonnel.addLigue(ligueName);

            JOptionPane.showMessageDialog(this, "Ligue ajoutée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            new GererLigue(gestionPersonnel, ligueName).setVisible(true);
            this.dispose();
        } catch (SauvegardeImpossible ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la ligue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
}
