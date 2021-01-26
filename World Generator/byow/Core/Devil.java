package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Devil implements Serializable {
    private Position position;
    private World world;
    private TETile tile;
    private ArrayList<Position> path;
    private Set<Position> visited = new HashSet();
    private int index = 0;
    private User santa;
    private boolean alive;


    public Devil(World world, Position position, User santa) {
        this.world = world;
        this.position = position;
        this.tile = TilesetAlt.DEVIL;
        world.putChar(position, tile);
        this.santa = santa;
        this.alive = true;
    }

    public Position getPosition() {
        return this.position;
    }

    public void disappear() {
        this.tile = TilesetAlt.FLOOR;
        this.alive = false;
        world.putChar(position, this.tile);
    }

    private void moveEW() {
        Position temp = new Position(position.getX(), position.getY());
        if (world.walkable(position.tryMove("up"))) {
            temp.move("up"); //go up
        } else if (world.walkable(position.tryMove("right"))) {
            temp.move("right"); //go right
        } else if (world.walkable(position.tryMove("down"))) {
            temp.move("down"); //go down
        } else if (world.walkable(position.tryMove("left"))) {
            temp.move("left"); //go left
        }
        if (world.getPosition(temp) == TilesetAlt.SANTA) {
            santa.hurts();
            disappear();
        } else if (world.getPosition(temp) == TilesetAlt.DEVIL) {
        } else {
            world.putChar(position, Tileset.FLOOR);
            world.putChar(temp, this.tile);
            position = temp;
        }
    }

    private void moveSE() {
        Position temp = new Position(position.getX(), position.getY());
        if (world.walkable(position.tryMove("right"))) {
            temp.move("right"); //go right
        } else if (world.walkable(position.tryMove("down"))) {
            temp.move("down"); //go down
        } else if (world.walkable(position.tryMove("up"))) {
            temp.move("up"); //go up
        } else if (world.walkable(position.tryMove("left"))) {
            temp.move("left"); //go left
        }
        if (world.getPosition(temp) == TilesetAlt.SANTA) {
            santa.hurts();
            disappear();
        } else if (world.getPosition(temp) == TilesetAlt.DEVIL) {
        } else {
            world.putChar(position, Tileset.FLOOR);
            world.putChar(temp, this.tile);
            position = temp;
        }
    }

    private void moveNW() {
        Position temp = new Position(position.getX(), position.getY());
        if (world.walkable(position.tryMove("left"))) {
            temp.move("left"); //go left
        } else if (world.walkable(position.tryMove("up"))) {
            temp.move("up"); //go up
        } else if (world.walkable(position.tryMove("down"))) {
            temp.move("down"); //go down
        } else if (world.walkable(position.tryMove("right"))) {
            temp.move("right"); //go right
        }
        if (world.getPosition(temp) == TilesetAlt.SANTA) {
            santa.hurts();
            disappear();
        } else if (world.getPosition(temp) == TilesetAlt.DEVIL) {
        } else {
            world.putChar(position, Tileset.FLOOR);
            world.putChar(temp, this.tile);
            position = temp;
        }
    }

    private void moveSW() {
        Position temp = new Position(position.getX(), position.getY());
        if (world.walkable(position.tryMove("left"))) {
            temp.move("left"); //go left
        } else if (world.walkable(position.tryMove("down"))) {
            temp.move("down"); //go down
        } else if (world.walkable(position.tryMove("up"))) {
            temp.move("up"); //go up
        } else if (world.walkable(position.tryMove("right"))) {
            temp.move("right"); //go right
        }
        if (world.getPosition(temp) == TilesetAlt.SANTA) {
            santa.hurts();
            disappear();
        } else if (world.getPosition(temp) == TilesetAlt.DEVIL) {
        } else {
            world.putChar(position, Tileset.FLOOR);
            world.putChar(temp, this.tile);
            position = temp;
        }
    }

    public void move(Position p, char c) {
        if (alive) {
            if (c == 'W' || c == 'S' || c == 'A' || c == 'D') {
                if (position.getX() <= p.getX() && position.getY() <= p.getY()) {

                    moveEW();
                } else if (position.getX() <= p.getX() && position.getY() > p.getY()) {

                    moveSE();
                } else if (position.getX() > p.getX() && position.getY() > p.getY()) {

                    moveSW();
                } else if (position.getX() > p.getX() && position.getY() <= p.getY()) {

                    moveNW();
                }
            }
        }
    }
}
