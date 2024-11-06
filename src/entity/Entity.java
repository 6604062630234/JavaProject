package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int x, y, dy;
    public int speed;
    public int gravityForce = 1;
    
    public BufferedImage up1, up2, left1, left2, right1, right2;
    
    public String direction;
    public int spriteCount = 0;
    public int spriteNum = 1;
    
    public Rectangle solidArea;
    public boolean collisionDetect;
    public boolean TopCollision;
    public boolean BottomCollision;
}
