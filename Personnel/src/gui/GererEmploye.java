package gui;

import java.awt.*;
import javax.swing.*;

public class GererEmploye extends JFrame {

    public GererEmploye() {
    	
        // Configuration de la nouvelle fenêtre
        super("LDO - Ligues Dynamiques et Organisées");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création des composants principaux
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajout titre et sous-titre
        JLabel titleLabel = new JLabel("Gérer les employés : ", SwingConstants.CENTER);
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
        JButton buttonAdd = new JButton("Ajouter");
        buttonAdd.addActionListener(e -> {
            AddEmploye newFrame = new AddEmploye();
            newFrame.setVisible(true);
        });
        
        JButton buttonEdit = new JButton("Modifier");
        buttonEdit.addActionListener(e -> {
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

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        titlePanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);
        buttonPanel.setBackground(backgroundColor);
        table.getTableHeader().setForeground(backgroundColor);
        table.setForeground(backgroundColor);
    }
}
