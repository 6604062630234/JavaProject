package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Coins extends Entity{
    
    private BufferedImage image;

    public Coins(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;

        this.solidArea = new Rectangle(x, y, tileSize, tileSize);
        loadCoinImage();
    }

    private void loadCoinImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/Coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Update for coins
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, solidArea.width, solidArea.height, null);
        g2.drawRect(x, y, solidArea.width, solidArea.height);
    }
}
