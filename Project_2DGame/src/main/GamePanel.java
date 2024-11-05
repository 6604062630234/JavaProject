package main;

import entity.Monster;
import entity.Player;
import input.KeyHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int OriginalTileSize = 16; //Default Objects are 16x16 pixels per tile
    final int Scale = 3;
    public final int TileSize = OriginalTileSize * Scale; //Scale to 48*48 pixels per tile

    // World setting
    public final int maxWorldCol = 150;
    public final int maxWorldRow = 12;
    public final int WorldWidth = TileSize * maxWorldCol; //768 pixels
    public final int WorldHeight = TileSize * maxWorldRow; //576 pixels

    // Create Player Views
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int ScreenWidth = TileSize * maxScreenCol; //768 pixels
    public final int ScreenHeight = TileSize * maxScreenRow; //576 pixels

    public int cameraX, cameraY = 0;

    // Create game FPS
    int FPS = 60;
    Thread gameThread;

    //Game state
    public final int playState = 1;
    public final int pauseState = 2;
    public final int diedState = 3;

    public int currentState = playState;

    public TileManager tile = new TileManager(this); //Instance create tile manager
    KeyHandler key = new KeyHandler(this); //Instance create key handler
    public Player player = new Player(this, key); //Instance create player
    public CollisionDetector cod = new CollisionDetector(this); //Instance create collision detector;
    public ArrayList<Monster> monsters = new ArrayList<>(); //Instance create monsters

    public void setupMonsters() {
        monsters.clear(); // Clear existing monsters

        for (int row = 0; row < maxWorldRow; row++) {
            for (int col = 0; col < maxWorldCol; col++) {
                // Check if the current tile is a spawn point for monsters
                if (tile.mapTileNum[col][row] == 19) {
                    monsters.add(new Monster(col * TileSize, row * TileSize, TileSize));
                }
            }
        }
    }

    public void resetCoins() {

        for (int row = 0; row < maxWorldRow; row++) {
            for (int col = 0; col < maxWorldCol; col++) {
                if (tile.mapTileNum[col][row] == 16) {
                    tile.mapTileNum[col][row] = 15;
                }
            }
        }
    }

    public GamePanel() {

        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight)); //Set the size of JPanel class
        this.setBackground(Color.black); //Set background color
        this.setDoubleBuffered(true); //This can improve game rendering performance
        this.addKeyListener(key); //Add key handler to game panel
        this.setFocusable(true); //Make this game panel can be focused to receive key input

        this.setupMonsters();
    }

    public void StartGameThreads() {

        gameThread = new Thread(this); //Pass this class to Thread
        gameThread.start();
    }

    public void updateCamera() {
        int playerCenterX = player.x + (TileSize / 2);
        int playerCenterY = player.y + (TileSize / 2);

        // Calculate cameraX
        cameraX = playerCenterX - (ScreenWidth / 2);
        cameraY = playerCenterY - (ScreenHeight / 2);

        // Clamp the camera position
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraX > (WorldWidth - ScreenWidth)) {
            cameraX = WorldWidth - ScreenWidth;
        }
        if (cameraY > (WorldHeight - ScreenHeight)) {
            cameraY = WorldHeight - ScreenHeight;
        }
    }

    public void update() {

        if (currentState == playState) {
            player.update();
            updateCamera();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.translate(-cameraX, -cameraY);

        if (currentState == playState) {
            tile.draw(g2);
            for (Monster monster : monsters) {
                monster.draw(g2);
            }
            player.draw(g2);
        } else if (currentState == pauseState) {
            tile.draw(g2);
            for (Monster monster : monsters) {
                monster.draw(g2);
            }
            player.draw(g2);
            popupOverlay(g2);
            drawPauseScreen(g2);
        } else if (currentState == diedState) {
            tile.draw(g2);
            for (Monster monster : monsters) {
                monster.draw(g2);
            }
            player.draw(g2);
            popupOverlay(g2);
            drawGameOverScreen(g2);
        }

        g2.dispose();
    }

    private void popupOverlay(Graphics2D g2) {
        Color overlayColor = new Color(0, 0, 0, 155);  // RGBA, where A (Alpha) controls transparency

        g2.setColor(overlayColor);
        g2.fillRect(0, 0, ScreenWidth + cameraX, ScreenHeight + cameraY);
    }

    private void drawPauseScreen(Graphics2D g2) {
        String message = "Game Paused. Press P to Resume.";
        g2.setColor(Color.white);  
        g2.setFont(new Font("Calibri", Font.BOLD, 30));

        FontMetrics metrics = g2.getFontMetrics();
        int x = (ScreenWidth - metrics.stringWidth(message)) / 2;
        int y = ScreenHeight / 2;

        g2.drawString(message, x + cameraX , y + cameraY);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        String message = "Game Over! Press R to Restart.";
        g2.setColor(Color.white);
        g2.setFont(new Font("Calibri", Font.BOLD, 30));
        
        FontMetrics metrics = g2.getFontMetrics();
        int x = (ScreenWidth - metrics.stringWidth(message)) / 2;
        int y = ScreenHeight / 2;
        
        g2.drawString(message, x + cameraX, y + cameraY);
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; //Draw every 0.01666 sec
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            //Update game informations
            update();

            //Draw the screen with update informations
            repaint();

            //Sleep method
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
