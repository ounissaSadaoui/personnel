package gui;

import java.awt.*;
import javax.swing.*;

public class EditEmploye extends JFrame {

    public EditEmploye() {

        // Configuration de la fenêtre
        super("LDO - Ligues Dynamiques et Organisées");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création des composants
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        // Ajout logo
        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("/Users/cynthia/Documents/itic_paris/dev/personnel/Personnel/docs/logo.png")
                .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Ajout titre
        JLabel titleLabel = new JLabel("Modifier : ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Champs de formulaire
        JTextField nameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField mailField = new JTextField(20);
        JTextField pwdField = new JTextField(20);
        JTextField dateArrField = new JTextField(20);
        JTextField dateDepField = new JTextField(20);

        // Label des champs 
        JLabel nameLabel = new JLabel("Nom: ");
        JLabel lastNameLabel = new JLabel("Prénom: ");
        JLabel mailLabel = new JLabel("Mail: ");
        JLabel pwdLabel = new JLabel("Mot de passe: ");
        JLabel dateArrLabel = new JLabel("Date d'arrivée: ");
        JLabel dateDepLabel = new JLabel("Date de départ: ");
        
        // Mettre en blanc
        nameLabel.setForeground(Color.WHITE);
        lastNameLabel.setForeground(Color.WHITE);
        mailLabel.setForeground(Color.WHITE);
        pwdLabel.setForeground(Color.WHITE);
        dateArrLabel.setForeground(Color.WHITE);
        dateDepLabel.setForeground(Color.WHITE);
        
        // Création d'un bouton pour ouvrir la nouvelle fenêtre
        JButton button = new JButton("Valider les modifications");
        button.addActionListener(e -> {
            GererEmploye newFrame = new GererEmploye();
            newFrame.setVisible(true);
        });
        

        // Panneau pour les champs de texte et le bouton
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajouter les labels et les champs de texte
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(mailLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(mailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(pwdLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(pwdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPanel.add(dateArrLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(dateArrField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        fieldsPanel.add(dateDepLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(dateDepField, gbc);

        // Bouton
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(button, gbc);

        // Ajout des composants au contentPane
        contentPane.add(logoLabel, BorderLayout.NORTH);
        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        
        // Configuration des couleurs
        Color backgroundColor = new Color(12, 23, 40);
        contentPane.setBackground(backgroundColor);
        fieldsPanel.setBackground(backgroundColor);
        titleLabel.setForeground(Color.WHITE);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Frame().setVisible(true);
        });
    }
}
