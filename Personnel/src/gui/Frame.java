package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	
	public Frame() {
		
		super("Connexion LDO");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		
		JPanel connexion = (JPanel) this.getContentPane();
		connexion.setLayout(new FlowLayout());
		connexion.add(new JButton("Connexion"));
		
		connexion.setBackground(new Color(12, 23, 40));
		
	}
	
	public static void main(String[] args) {
		Frame firstWindow = new Frame();
		firstWindow.setVisible(true);
	}

}
