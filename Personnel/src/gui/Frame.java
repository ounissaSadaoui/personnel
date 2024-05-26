package gui;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	public Frame() {
		
		super("Connexion LDO");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args) {
		Frame firstWindow = new Frame();
		firstWindow.setVisible(true);
	}

}
