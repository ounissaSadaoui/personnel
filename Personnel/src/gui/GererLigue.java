package gui;

import java.awt.*;
import javax.swing.*;

public class GererLigue extends MainFrame {

    public GererLigue() {

        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Gérer les ligues : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Création du tableau avec 2 colonnes
        String[] columnNames = {"Nom de la ligue", "Administrée par : "};
        Object[][] data = {
            {"Donnée 1", "Donnée 2"},
            {"Donnée 1", "Donnée 2"},
            {"Donnée 1", "Donnée 2"},
            {"Donnée 1", "Donnée 2"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        GuiUtils.configurePanel(buttonPanel, new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton buttonAdd = GuiUtils.createButton("Ajouter", e -> {
            AddLigue newFrame = new AddLigue();
            newFrame.setVisible(true);
        });
        JButton buttonEdit = GuiUtils.createButton("Editer", e -> {
            ShowLigue newFrame = new ShowLigue();
            newFrame.setVisible(true);
        });
        
        JButton buttonDelete = new JButton("Supprimer");
        
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonDelete);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
