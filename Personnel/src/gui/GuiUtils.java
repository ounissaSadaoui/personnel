package gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GuiUtils {
	
	public static final Color BGcolor = new Color(12, 23, 40);
	
	public static void setComonentColors(JComponent component, Color background, Color foreground) {
		component.setBackground(background);
		component.setForeground(foreground);
	}
	
	public static JLabel createLabel(String text, Color foreground) {
		JLabel label = new JLabel(text);
		label.setForeground(foreground);
		return label;
	}
	
	public static JTextField createTextField(int columns) {
		return new JTextField(columns);
	}
	
	public static JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    public static void configureMainFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
    }

    public static void configurePanel(JPanel panel, LayoutManager layout) {
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(BGcolor);
    }
	

}
