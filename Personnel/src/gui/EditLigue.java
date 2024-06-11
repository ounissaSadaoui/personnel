package gui;

import java.awt.*;
import javax.swing.*;

public class EditLigue extends MainFrame {

    public EditLigue() {
    	
        super("LDO - Ligues Dynamiques et Organisées", 600, 400);
        
        JPanel contentPane = getContentPanePanel();

        // Ajout titre et sous-titre
        JLabel titleLabel = GuiUtils.createLabel("Modifier la ligue : ", Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Creation champ
        JLabel nameLabel = GuiUtils.createLabel("Nom :", Color.WHITE);
        JTextField nameField = GuiUtils.createTextField(20);

        // Création des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton buttonLigue = GuiUtils.createButton("Valider les modifications", e -> {
            ShowLigue newFrame = new ShowLigue();
            newFrame.setVisible(true);
        });

        buttonPanel.add(buttonLigue);

        // Creation du tableau
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridy++;
        fieldsPanel.add(nameField, gbc);
        gbc.gridy++;
        fieldsPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        
        // Ajout des composants au contentPane
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        
        fieldsPanel.setBackground(GuiUtils.BGcolor);
        titlePanel.setBackground(GuiUtils.BGcolor);
        buttonPanel.setBackground(GuiUtils.BGcolor);

        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
}
