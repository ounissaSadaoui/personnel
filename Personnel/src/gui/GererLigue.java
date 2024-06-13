
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Employe;

public class GererLigue extends MainFrame {

    private GestionPersonnel gestionPersonnel;
    private DefaultTableModel tableModel;
    private JTable table;

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
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        // Charger les données des ligues
        loadLigueData();

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        GuiUtils.configurePanel(buttonPanel, new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton buttonAdd = GuiUtils.createButton("Ajouter", e -> {
            AddLigue newFrame = new AddLigue(gestionPersonnel);
            newFrame.setVisible(true);
            this.dispose();
        });

        JButton buttonEdit = GuiUtils.createButton("Editer", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);
                if (selectedLigue != null) {
                    EditLigue editFrame = new EditLigue(selectedLigue);
                    editFrame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à éditer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
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

    private Ligue findLigueByName(String nomLigue) {
        for (Ligue ligue : gestionPersonnel.getLigues()) {
            if (ligue.getNom().equals(nomLigue)) {
                return ligue;
            }
        }
        return null;
    }
}
