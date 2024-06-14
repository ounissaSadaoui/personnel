/*
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

import java.awt.*;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;
import personnel.DateInvalide;
import personnel.Employe;

public class GererLigue extends MainFrame {

    private GestionPersonnel gestionPersonnel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JDBC jdbc;
	private boolean isRoot;
	private String username;

    
    
    public GererLigue(GestionPersonnel gestionPersonnel, String username) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.gestionPersonnel = gestionPersonnel;
        this.username = username;

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

        JButton buttonEdit = GuiUtils.createButton("Renommer", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);
                if (selectedLigue != null) {
                    EditLigue editFrame = new EditLigue(selectedLigue);
                    editFrame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à renommer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
     // Gérer les employés de la ligue
        JButton buttonGestion = GuiUtils.createButton("Gérer les employés", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);
                Employe admin = selectedLigue.getAdministrateur();
                if (selectedLigue != null ) {
                    try {
                        GererEmploye gererEmployeFrame = new GererEmploye(selectedLigue);
                        gererEmployeFrame.setVisible(true);
                    } catch (DateInvalide | SauvegardeImpossible e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        JButton buttonGestion = GuiUtils.createButton("Gérer les employés", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);

                if (selectedLigue != null) {
                    // Vérification des droits de l'utilisateur
                    Employe admin = selectedLigue.getAdministrateur() ; // Adapter selon votre structure

                    if (admin.getNom().equals(this.username)) {
                        try {
                            GererEmploye gererEmployeFrame = new GererEmploye(selectedLigue);
                            gererEmployeFrame.setVisible(true);
                        } catch (DateInvalide | SauvegardeImpossible e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Droits insuffisants. Vous n'avez pas accès à cette fonctionnalité.", "Accès refusé", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ligue non trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
//suppression d'une ligue
        JButton deleteButton = new JButton("Supprimer ");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); 
                Ligue selectedLigue = null;

                // Recherche de la ligue par son nom
                for (Ligue ligue : gestionPersonnel.getLigues()) {
                    if (ligue.getNom().equals(nomLigue)) {
                        selectedLigue = ligue;
                        break;
                    }
                }

                if (selectedLigue != null) {
                    int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer la ligue " + selectedLigue.getNom() + " ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        try {
                            selectedLigue.remove(); // Utilisation directe de la méthode remove() de Ligue
                            JOptionPane.showMessageDialog(this, "Ligue supprimée avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();

                        } catch (SauvegardeImpossible ex) {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à supprimer.", "Aucune ligue sélectionnée", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.add(buttonAdd);
        buttonPanel1.add(buttonEdit);
        buttonPanel1.add(deleteButton);
        buttonPanel1.add(buttonGestion);
        contentPane.add(buttonPanel1, BorderLayout.SOUTH);
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
}*/

package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;
import personnel.DateInvalide;
import personnel.Employe;

import java.awt.*;
import java.util.List;
import java.util.SortedSet;

public class GererLigue extends MainFrame {

    private GestionPersonnel gestionPersonnel;
    private DefaultTableModel tableModel;
    private JTable table;
    private String username;
    private JDBC jdbc = new JDBC(); 

    public GererLigue(GestionPersonnel gestionPersonnel, String username) {
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        this.gestionPersonnel = gestionPersonnel;
        this.username = username;

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
            if (checkDroitsUtilisateur(null)) {
                AddLigue newFrame = new AddLigue(gestionPersonnel);
                newFrame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Seul le root peut créer des ligues", "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
            }
        });


        JButton buttonEdit = GuiUtils.createButton("Renommer", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);
                if (checkDroitsUtilisateur(selectedLigue)) {
                    EditLigue editFrame = new EditLigue(selectedLigue);
                    editFrame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à renommer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton buttonGestion = GuiUtils.createButton("Gérer les employés", e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0); // Nom de la ligue sélectionnée
                Ligue selectedLigue = findLigueByName(nomLigue);

                if (checkDroitsUtilisateur(selectedLigue)) {
                    try {
                        GererEmploye gererEmployeFrame = new GererEmploye(selectedLigue);
                        gererEmployeFrame.setVisible(true);
                    } catch (DateInvalide | SauvegardeImpossible e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton deleteButton = new JButton("Supprimer ");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nomLigue = (String) table.getValueAt(selectedRow, 0);
                Ligue selectedLigue = findLigueByName(nomLigue);
                if (checkDroitsUtilisateur(selectedLigue)) {
                    int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer la ligue " + selectedLigue.getNom() + " ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        try {
                            selectedLigue.remove();
                            JOptionPane.showMessageDialog(this, "Ligue supprimée avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        } catch (SauvegardeImpossible ex) {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à supprimer.", "Aucune ligue sélectionnée", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.add(buttonAdd);
        buttonPanel1.add(buttonEdit);
        buttonPanel1.add(deleteButton);
        buttonPanel1.add(buttonGestion);
        contentPane.add(buttonPanel1, BorderLayout.SOUTH);
    }

    private boolean checkDroitsUtilisateur(Ligue selectedLigue) {
        if (selectedLigue == null) {
            // Pour les actions qui ne nécessitent pas une ligue sélectionnée ::: Ajouter 
            return gestionPersonnel.getRoot().getNom().equals(username);
        } else {
            // les autres actions
            Employe admin = selectedLigue.getAdministrateur();
            if (admin != null && admin.getNom().equals(username)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Droits insuffisants. Vous n'avez pas accès à cette fonctionnalité.", "Accès refusé", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    private void loadLigueData() {
        if (gestionPersonnel != null) {
            SortedSet<Ligue> ligues = gestionPersonnel.getLigues();
            for (Ligue ligue : ligues) {
                Employe admin = ligue.getAdministrateur();
                String adminName = (admin != null) ? admin.getNom() : "Aucun";
                tableModel.addRow(new Object[]{ligue.getNom(), adminName});
            }
        } else {
            System.out.println("gestionPersonnel est null");
        }
    }

    private Ligue findLigueByName(String nomLigue) {
        SortedSet<Ligue> ligues = gestionPersonnel.getLigues();
        for (Ligue ligue : ligues) {
            if (ligue.getNom().equals(nomLigue)) {
                return ligue;
            }
        }
        return null;
    }
}

