package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Employe;

public class GererLigue extends MainFrame {
    
    private GestionPersonnel gestionPersonnel;
    private DefaultTableModel tableModel;

    public GererLigue(GestionPersonnel gestionPersonnel) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);

        this.gestionPersonnel = gestionPersonnel;

        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Gérer les ligues : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Création du tableau avec 2 colonnes
        String[] columnNames = {"Nom de la ligue", "Administrée par"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        // Charger les données des ligues
        loadLigueData();

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

    private void loadLigueData() {
        if (gestionPersonnel != null) {
            for (Ligue ligue : gestionPersonnel.getLigues()) {
                Employe admin = ligue.getAdministrateur();
                String adminName = (admin != null) ? admin.getNom() : "Aucun";
                tableModel.addRow(new Object[]{ligue.getNom(), adminName});
            }
        } else {
            System.out.println("gestionPersonnel est null");
        }
    }
}
