package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;
import personnel.DateInvalide;
import personnel.Employe;

import java.awt.*;
import java.time.LocalDate;
import java.util.SortedSet;

public class GererEmploye extends MainFrame {

    private GestionPersonnel gestionPersonnel;
    private DefaultTableModel tableModel;
    private JTable table;
    private Ligue ligue;

    public GererEmploye(Ligue ligue) throws DateInvalide, SauvegardeImpossible {
        super("Gérer les employés", 600, 400);
        this.gestionPersonnel = GestionPersonnel.getGestionPersonnel();
        this.ligue = ligue;

        JPanel contentPane = getContentPanePanel();

        // Ajout titre
        JLabel titleLabel = GuiUtils.createLabel("Gérer les employés : " + ligue.getNom(), Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Création du tableau avec les colonnes
        String[] columnNames = {"Nom", "Prénom", "Email", "Date d'arrivée", "Date de départ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        // Charger les données des employés
        loadEmployesData();

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        GuiUtils.configurePanel(buttonPanel, new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton buttonAdd = GuiUtils.createButton("Ajouter", e -> {
            // Ajouter un nouvel employé a suivre
        });

        JButton buttonEdit = GuiUtils.createButton("Modifier", e -> {
        });

        JButton buttonDelete = GuiUtils.createButton("Supprimer", e -> {
        });

        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonDelete);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadEmployesData() {
        // Vider le modèle de table avant de le remplir à nouveau
        tableModel.setRowCount(0);

        try {
            SortedSet<Employe> employes = ligue.getEmployes();

            // Vérifier qu'il y a des employés à afficher
            if (employes != null && !employes.isEmpty()) {
                for (Employe employe : employes) {
                    try {
                        // Récupérer les données de l'employé
                        String nom = employe.getNom();
                        String prenom = employe.getPrenom();
                        String mail = employe.getMail();
                        LocalDate dateArrivee = employe.getDateArrivee();
                        LocalDate dateDepart = employe.getDateDepart();

                        // Ajouter une ligne au modèle de table
                        tableModel.addRow(new Object[]{nom, prenom, mail, dateArrivee, dateDepart});
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur à la récupération des données.", "Aucune donnée", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Aucun Employé à afficher.", "Ligue vide", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
