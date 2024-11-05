package main;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args){
        
        //Create game window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mark The Emperor");
        
        //Add game panel to window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.StartGameThreads();
    }
}