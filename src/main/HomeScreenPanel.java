package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreenPanel extends JPanel {

    private JFrame window;
    private Image backgroundImage;
    private JButton startButton;

    public HomeScreenPanel(JFrame window) {
        this.window = window;
        this.setPreferredSize(new Dimension(960, 576));

        // Load background image (Ensure the path is correct)
        backgroundImage = new ImageIcon(getClass().getResource("/UI/HomeScreen.png")).getImage();

        // Load start button image and create JButton
        ImageIcon startIcon = new ImageIcon(getClass().getResource("/UI/StartBT.png"));
        startButton = new JButton(startIcon);
        startButton.setContentAreaFilled(false);  // Remove button background
        startButton.setBorderPainted(false);       // Remove button border

        // Set button position and add to panel
        setLayout(null); // Use absolute positioning
        startButton.setBounds(320, 380, startIcon.getIconWidth(), startIcon.getIconHeight());
        this.add(startButton);

        // Add action listener to start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    private void startGame() {
        // Remove home screen and switch to GamePanel
        window.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.revalidate();
        gamePanel.requestFocusInWindow();
        gamePanel.StartGameThreads();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

