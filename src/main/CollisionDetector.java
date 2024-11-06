package main;

import entity.Entity;
import entity.Player;

public class CollisionDetector {

    GamePanel gp;

    public CollisionDetector(GamePanel gp) {

        this.gp = gp;
    }

    public void checkCoinCollision(Player player) {
        int leftCol = (player.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.width) / gp.TileSize;
        int topRow = (player.y) / gp.TileSize;
        int bottomRow = (player.y + player.solidArea.height) / gp.TileSize;

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

        int LeftCollisionArea = entity.x;
        int RightCollisionArea = entity.x + entity.solidArea.width;
        int TopCollisionArea = entity.y;

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
        int topCollisionArea = player.y + player.dy;
        int topRow = topCollisionArea / gp.TileSize;
        int leftCol = (player.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.width) / gp.TileSize;

        int tileAboveLeft = gp.tile.mapTileNum[leftCol][topRow];
        int tileAboveRight = gp.tile.mapTileNum[rightCol][topRow];

        if (gp.tile.tiles[tileAboveLeft].collision || gp.tile.tiles[tileAboveRight].collision) {
            player.y = (topRow * gp.TileSize) + player.solidArea.height;
            player.dy = 0;
        }
    }

    public void bottomCollisionChecks(Player player) {
        // Check for collision with tiles below
        int bottomCollisionArea = player.y + player.solidArea.height + player.dy;
        int bottomRow = bottomCollisionArea / gp.TileSize;
        int leftCol = (player.x) / gp.TileSize;
        int rightCol = (player.x + player.solidArea.width) / gp.TileSize;

        // Ensure indices do not go out of bounds
        bottomRow = Math.min(bottomRow, gp.tile.mapTileNum[0].length - 1);  // Clamp bottomRow
        leftCol = Math.min(Math.max(leftCol, 0), gp.tile.mapTileNum.length - 1);  // Clamp leftCol
        rightCol = Math.min(Math.max(rightCol, 0), gp.tile.mapTileNum.length - 1); // Clamp rightCol

        // Access tiles and check for collision
        int tileBelowLeft = gp.tile.mapTileNum[leftCol][bottomRow];
        int tileBelowRight = gp.tile.mapTileNum[rightCol][bottomRow];

        if (gp.tile.tiles[tileBelowLeft].collision || gp.tile.tiles[tileBelowRight].collision) {
            player.y = (bottomRow * gp.TileSize) - player.solidArea.height;
            player.dy = 0;
            player.isOnTheGround = true;
        } else {
            player.isOnTheGround = false;
        }
    }

    public void checkSinking(GamePanel gp) {
        int leftCol = (gp.player.x) / gp.TileSize;
        int rightCol = (gp.player.x + gp.player.solidArea.width) / gp.TileSize;
        int bottomRow = (gp.player.y + gp.player.solidArea.height) / gp.TileSize;

        if (gp.tile.mapTileNum[leftCol][bottomRow] == 14 || gp.tile.mapTileNum[rightCol][bottomRow] == 14) {
            gp.currentState = gp.diedState;
        }
    }
}
