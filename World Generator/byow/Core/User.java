package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.io.Serializable;

public class User implements Serializable {
    private Position position;
    private World world;
    private TETile tile;
    private int health;
    private boolean win;


    public User(World world, Position position) {
        this.world = world;
        this.position = position;
        this.tile = TilesetAlt.SANTA;
        this.health = 1;
        this.win = false;
        world.putChar(position, tile);
    }

    public void move(char c) {
        Position temp;
        if (c == 'W') {
            temp = new Position(position.getX(), position.getY() + 1);
        } else if (c == 'S') {
            temp = new Position(position.getX(), position.getY() - 1);
        } else if (c == 'A') {
            temp = new Position(position.getX() - 1, position.getY());
        } else if (c == 'D') {
            temp = new Position(position.getX() + 1, position.getY());
        } else {
            temp = position;
        }
        if (world.getPosition(temp) == TilesetAlt.SNOWMAN) {
            health += 2;
        } else if (world.getPosition(temp) == TilesetAlt.RING) {
            this.win = true;
        } else if (world.getPosition(temp) == TilesetAlt.DEER) {
            health += 1;
        } else if (world.getPosition(temp) == TilesetAlt.DEVIL) {
            health -= 1;
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
        if (world.walkable(temp) && temp != position) {
            world.putChar(this.position, Tileset.FLOOR);
            world.putChar(temp, this.tile);
            position = temp;
        }
    }

    public Position getPosition() {
        return this.position;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean wins() {
        return this.win;
    }

    public void hurts() {
        health -= 1;
    }

}
