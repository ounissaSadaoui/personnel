package gui;

import java.awt.*;
import javax.swing.*;

public class GererEmploye extends MainFrame {

    public GererEmploye() {
    	
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        // Récupération du panneau de contenu
        JPanel contentPane = getContentPanePanel();

        // Ajout titre et sous-titre
        JLabel titleLabel = GuiUtils.createLabel("Gérer les employés : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Création du tableau avec 5 colonnes
        String[] columnNames = {"Nom", "Prénom", "Mail", "Date d'arrivée", "Date de départ"};
        Object[][] data = {
            {"Donnée 1", "Donnée 2", "Donnée 3", "Donnée 4", "Donnée 5"},
            {"Donnée 1", "Donnée 2", "Donnée 3", "Donnée 4", "Donnée 5"},
            {"Donnée 1", "Donnée 2", "Donnée 3", "Donnée 4", "Donnée 5"},
            {"Donnée 1", "Donnée 2", "Donnée 3", "Donnée 4", "Donnée 5"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton buttonAdd = GuiUtils.createButton("Ajouter", e -> {
            AddEmploye newFrame = new AddEmploye();
            newFrame.setVisible(true);
        });

        JButton buttonEdit = GuiUtils.createButton("Modifier", e -> {
            EditEmploye newFrame = new EditEmploye();
            newFrame.setVisible(true);
        });
        
        JButton buttonDelete = new JButton("Supprimer");
        
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonDelete);

        // Ajout des composants au contentPane
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        buttonPanel.setBackground(GuiUtils.BGcolor);
        titlePanel.setBackground(GuiUtils.BGcolor);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
