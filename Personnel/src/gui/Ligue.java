package gui;

import java.awt.*;
import javax.swing.*;

public class Ligue extends JFrame {

    public Ligue() {
    	
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
        JLabel titleLabel = new JLabel("Editer : ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel("Administrée par :", SwingConstants.CENTER);
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
        
        JButton buttonEmploye = new JButton("Gérer les employés");
        buttonEmploye.addActionListener(e -> {
            Employe newFrame = new Employe();
            newFrame.setVisible(true);
        });
        
        JButton buttonRenameLigue = new JButton("Renommer la ligue");
        buttonRenameLigue.addActionListener(e -> {
            GererLigue newFrame = new GererLigue();
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

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        titlePanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);
        subtitleLabel.setForeground(Color.WHITE);
        buttonPanel.setBackground(backgroundColor);
        table.getTableHeader().setForeground(backgroundColor);
        table.setForeground(backgroundColor);
    }
}
