package gui;

import java.awt.*;
import javax.swing.*;

public class ShowLigue extends MainFrame {

    public ShowLigue() {

        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        
        JPanel contentPane = getContentPanePanel();

        // Ajout titre et sous-titre
        JLabel titleLabel = GuiUtils.createLabel("Editer : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel subtitleLabel = GuiUtils.createLabel("Administrée par :", Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Création du tableau avec 2 colonnes
        String[] columnNames = {"Nom de l'employé", "Prénom de l'employé"};
        Object[][] data = {
            {"Donnée 1", "Donnée 2"},
            {"Donnée 3", "Donnée 4"},
            {"Donnée 5", "Donnée 6"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton buttonEmploye = GuiUtils.createButton("Gérer les employés", e -> {
            GererEmploye newFrame = new GererEmploye();
            newFrame.setVisible(true);
        });

        JButton buttonRenameLigue = GuiUtils.createButton("Modifier la ligue", e -> {
            EditLigue newFrame = new EditLigue(null);
            newFrame.setVisible(true);
        });
        
        JButton buttonDeleteLigue = new JButton("Supprimer la ligue");
        buttonPanel.add(buttonEmploye);
        buttonPanel.add(buttonRenameLigue);
        buttonPanel.add(buttonDeleteLigue);

        // Ajout des composants au contentPane
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.setBackground(GuiUtils.BGcolor);
        buttonPanel.setBackground(GuiUtils.BGcolor);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
    }
}
