package main;

import entity.Entity;
import entity.Player;

public class CollisionDetector {

    GamePanel gp;

    public CollisionDetector(GamePanel gp) {

        this.gp = gp;
    }

    public void checkCoinCollision(Player player) {
        int leftCol = (player.x + player.solidArea.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.x + player.solidArea.width) / gp.TileSize;
        int topRow = (player.y + player.solidArea.y) / gp.TileSize;
        int bottomRow = (player.y + player.solidArea.y + player.solidArea.height) / gp.TileSize;

        // Check for coin collision
        if (gp.tile.mapTileNum[leftCol][topRow] == 15 || gp.tile.mapTileNum[rightCol][topRow] == 15
                || gp.tile.mapTileNum[leftCol][bottomRow] == 15 || gp.tile.mapTileNum[rightCol][bottomRow] == 15) {

            player.score++;

            if (gp.tile.mapTileNum[leftCol][topRow] == 15) {
                gp.tile.mapTileNum[leftCol][topRow] = 16;
            }
            if (gp.tile.mapTileNum[rightCol][topRow] == 15) {
                gp.tile.mapTileNum[rightCol][topRow] = 16;
            }
            if (gp.tile.mapTileNum[leftCol][bottomRow] == 15) {
                gp.tile.mapTileNum[leftCol][bottomRow] = 16;
            }
            if (gp.tile.mapTileNum[rightCol][bottomRow] == 15) {
                gp.tile.mapTileNum[rightCol][bottomRow] = 16;
            }
        }
    }

    public void checkCollision(Entity entity) {

        int LeftCollisionArea = entity.x + entity.solidArea.x;
        int RightCollisionArea = entity.x + entity.solidArea.x + entity.solidArea.width;
        int TopCollisionArea = entity.y + entity.solidArea.y;

        int LeftCol = LeftCollisionArea / gp.TileSize;
        int RightCol = RightCollisionArea / gp.TileSize;
        int TopRow = TopCollisionArea / gp.TileSize;

        int tile1;

        switch (entity.direction) {

            case "Left":
                LeftCol = (LeftCollisionArea - entity.speed) / gp.TileSize;
                tile1 = gp.tile.mapTileNum[LeftCol][TopRow];

                if (gp.tile.tiles[tile1].collision == true) {
                    entity.collisionDetect = true;
                }
                break;
            case "Right":
                RightCol = (RightCollisionArea + entity.speed) / gp.TileSize;
                tile1 = gp.tile.mapTileNum[RightCol][TopRow];

                if (gp.tile.tiles[tile1].collision == true) {
                    entity.collisionDetect = true;
                }
                break;
        }
    }

    public void TopCollisionChecks(Player player) {
        // Check for collision with tiles above
        int topCollisionArea = player.y + player.solidArea.y + player.dy;
        int topRow = topCollisionArea / gp.TileSize;
        int leftCol = (player.x + player.solidArea.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.x + player.solidArea.width) / gp.TileSize;

        int tileAboveLeft = gp.tile.mapTileNum[leftCol][topRow];
        int tileAboveRight = gp.tile.mapTileNum[rightCol][topRow];

        if (gp.tile.tiles[tileAboveLeft].collision || gp.tile.tiles[tileAboveRight].collision) {
            player.y = (topRow * gp.TileSize) + player.solidArea.height + player.solidArea.y;
            player.dy = 0;
        }
    }

    public void bottomCollisionChecks(Player player) {
        // Check for collision with tiles below
        int bottomCollisionArea = player.y + player.solidArea.y + player.solidArea.height + player.dy;
        int bottomRow = bottomCollisionArea / gp.TileSize;
        int leftCol = (player.x + player.solidArea.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.x + player.solidArea.width) / gp.TileSize;

        int tileBelowLeft = gp.tile.mapTileNum[leftCol][bottomRow];
        int tileBelowRight = gp.tile.mapTileNum[rightCol][bottomRow];

        if (gp.tile.tiles[tileBelowLeft].collision || gp.tile.tiles[tileBelowRight].collision) {
            player.y = (bottomRow * gp.TileSize) - player.solidArea.height - player.solidArea.y;
            player.dy = 0;
            player.isOnTheGround = true;
        } else {
            player.isOnTheGround = false;
        }
    }

    public void checkMonsterCollision(Player player) {
        int leftCol = (player.x + player.solidArea.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.x + player.solidArea.width) / gp.TileSize;
        int topRow = (player.y + player.solidArea.y) / gp.TileSize;
        int bottomRow = (player.y + player.solidArea.y + player.solidArea.height) / gp.TileSize;

        if (gp.tile.mapTileNum[leftCol][topRow] == 19 || gp.tile.mapTileNum[rightCol][topRow] == 19 ||
            gp.tile.mapTileNum[leftCol][bottomRow] == 19 || gp.tile.mapTileNum[rightCol][bottomRow] == 19) {
            player.loseHP();
        }
    }
    
    public void checkSinking(GamePanel gp){
        int leftCol = (gp.player.x + gp.player.solidArea.x) / gp.TileSize;
        int rightCol = (gp.player.x + gp.player.solidArea.x + gp.player.solidArea.width) / gp.TileSize;
        int bottomRow = (gp.player.y + gp.player.solidArea.y + gp.player.solidArea.height) / gp.TileSize;
        
        if (gp.tile.mapTileNum[leftCol][bottomRow] == 14 || gp.tile.mapTileNum[rightCol][bottomRow] == 14) {
            gp.currentState = gp.diedState;
        }
    }
}
