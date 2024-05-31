package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame {
	
	public Frame() {
		
		super("LDO - Ligues Dynamiques et organisées");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
	
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JTextField userField = new JTextField("Nom", 20);
        contentPane.add(userField);
        userField.setLayout(new FlowLayout(FlowLayout.CENTER));
        userField.setPreferredSize(new Dimension(100, 30)); 
        
        // creation et position du bouton
        JButton button = new JButton("Connexion");
        button.setPreferredSize(new Dimension(100, 30)); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        contentPane.setBackground(new Color(12, 23, 40));
        buttonPanel.setBackground(new Color(12, 23, 40));
   
	}
	
	public static void main(String[] args) {
		Frame firstWindow = new Frame();
		firstWindow.setVisible(true);
	}

}
