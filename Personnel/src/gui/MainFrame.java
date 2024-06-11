package gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import gui.GuiUtils;

public class MainFrame extends JFrame{
	
    public MainFrame(String title, int width, int height) {
        GuiUtils.configureMainFrame(this, title, width, height);
        JPanel contentPane = new JPanel(new BorderLayout());
        GuiUtils.configurePanel(contentPane, new BorderLayout());
        setContentPane(contentPane);
    }

    public JPanel getContentPanePanel() {
        return (JPanel) getContentPane();
    }
}
