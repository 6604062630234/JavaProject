package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Monster extends Entity {

    private BufferedImage image;

    private int hitboxWidth;
    private int hitboxHeight;
    private int hitboxOffsetX;
    private int hitboxOffsetY;

    public Monster(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;

        // Set hitbox dimensions and position
        this.hitboxWidth = tileSize; 
        this.hitboxHeight = tileSize; 
        this.hitboxOffsetX = 0; 
        this.hitboxOffsetY = 0; 

        this.solidArea = new Rectangle(hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight);
        loadMonsterImage();
    }

    private void loadMonsterImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/monsters/Rimuru_AFK.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Implement monster behavior (e.g., moving, patrolling)
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, solidArea.width, solidArea.height, null);
        g2.setColor(Color.red);
        g2.drawRect(x, y, solidArea.width, solidArea.height);
    }
}