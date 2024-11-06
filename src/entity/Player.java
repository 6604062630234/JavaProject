package entity;

import input.KeyHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity {

    public int score = 0;
    public int hp;
    public final int jumpStrength = - 15;
    public boolean isOnTheGround = false;
    public int dy = 0;

    private long lastDamageTime = 0; // Time of last damage
    private final long damageCooldown = 2000; // 2 seconds cooldown
    public boolean isInvulnerable = false; // To check if player is invulnerable

    GamePanel gp;
    KeyHandler key;

    public Player(GamePanel gp, KeyHandler key) {

        this.gp = gp;
        this.key = key;

        setDefault();
        getPlayerImg();

        solidArea = new Rectangle(x, y, gp.TileSize, gp.TileSize);
    }

    public void setDefault() {

        x = 100;
        y = 200;
        hp = 999;
        speed = 5;
        score = 0;
        direction = "Right";

    }
    
    public void updatePlayerSolidArea(){
        this.solidArea.x = x;
        this.solidArea.y = y;
    }

    public void loseHP() {

        if (gp.currentState != gp.pauseState) {

            long currentTime = System.currentTimeMillis();
            if (!isInvulnerable && (currentTime - lastDamageTime >= damageCooldown)) {
                hp--;
                lastDamageTime = currentTime;
                isInvulnerable = true; // Set invulnerability state

                if (hp <= 0) {
                    gp.currentState = gp.diedState;
                }
            }
        }
    }

    public void getPlayerImg() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Right_Idle1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Right_Idle2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Left_Walk1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Left_Walk2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Right_Walk1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Right_Walk2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jump() {
        if (!TopCollision && isOnTheGround) {
            dy = jumpStrength;
            isOnTheGround = false;
        }
    }

    public void update() {
        
        updatePlayerSolidArea();

        long currentTime = System.currentTimeMillis();
        if (isInvulnerable && (currentTime - lastDamageTime >= damageCooldown)) {
            isInvulnerable = false; // Reset invulnerability
        }

        //Check tile collision
        collisionDetect = false;
        gp.cod.checkCollision(this);
        gp.cod.checkCoinCollision(this);
        gp.checkMonsterCollision();
        gp.cod.checkSinking(gp);

        if (!isOnTheGround) {
            dy += gravityForce;
            y += dy;

            // Top boundary check
            if (y < 0) {
                y = 0;
                dy = 0;
            }
            gp.cod.TopCollisionChecks(this);
        }

        if (dy >= 0) { // When falls
            gp.cod.bottomCollisionChecks(this);
        }

        if (key.Jump) {
            direction = "Up";
            jump();
        }

        if (key.Left == true || key.Right == true) {

            if (key.Left == true) {
                direction = "Left";
            }
            if (key.Right == true) {
                direction = "Right";
            }

            //Collision false
            if (collisionDetect == false) {

                switch (direction) {

                    case "Left":
                        x -= speed;
                        break;
                    case "Right":
                        x += speed;
                        break;
                }

                // Horizontal boundary checks
                if (x < 0) {
                    x = 0;
                }
                if (x > gp.WorldWidth - gp.TileSize) {
                    x = gp.WorldWidth - gp.TileSize;
                }
            }

            // Handle player sprites
            spriteCount++;
            if (spriteCount > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCount = 0;
            }
        }
        //update score
    }
    //Player animations

    public void draw(Graphics2D g2) {

        int scoreX = 50;
        int scoreY = 50;
        int hpX = 50;
        int hpY = 80;
        int remX = 20;
        int remY = 555;

        BufferedImage img = null;

        g2.setColor(Color.black);
        g2.setFont(new Font("Calibri", Font.BOLD, 20));
        g2.drawString("Score: " + score, scoreX + gp.cameraX, scoreY + gp.cameraY);
        g2.drawString("HP: " + hp, hpX + gp.cameraX, hpY + gp.cameraY);
        g2.setColor(Color.white);
        g2.drawString("Reminder: Everything happens in this game. It's not a bug, its feature", remX + gp.cameraX, remY + gp.cameraY);

        switch (direction) {

            case "Up":
                if (spriteNum == 1) {
                    img = up1;
                } else {
                    img = up2;
                }
                break;
            case "Left":
                if (spriteNum == 1) {
                    img = left1;
                } else {
                    img = left2;
                }
                break;
            case "Right":
                if (spriteNum == 1) {
                    img = right1;
                } else {
                    img = right2;
                }
                break;
        }
        g2.drawImage(img, x, y, gp.TileSize, gp.TileSize, null);
        g2.setColor(Color.red);
        g2.drawRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
    }
}
