package byow.Core;

import byow.TileEngine.TETile;
import java.util.ArrayList;
import java.util.List;

public class Hallway {
    //generates hallway of random length
    private TETile tile;
    private Position origin;
    private int length;
    private String direction;
    private List<Position> middlePositions;


    public Hallway(Position origin, int length, String direction) {
        this.middlePositions = new ArrayList<>();
        this.origin = origin;
        this.length = length;
        this.direction = direction;
        this.tile = tile;
    }

    public List<Position> getHallwayPositions() {
        List<Position> allpos = new ArrayList<>();
        for (int i = 0; i < this.length; i++) {
            if (this.direction.equals("North")) {
                allpos.add(new Position(origin.getX(), origin.getY() + i));
                if (i > 0 && i < this.length - 1) {
                    this.middlePositions.add(new Position(origin.getX(), origin.getY() + i));
                }
            } else if (this.direction.equals("South")) {
                allpos.add(new Position(origin.getX(), origin.getY() - i));
                if (i > 0 && i < this.length - 1) {
                    this.middlePositions.add(new Position(origin.getX(), origin.getY() - i));
                }
            } else if (this.direction.equals("East")) {
                allpos.add(new Position(origin.getX() + i, origin.getY()));
                if (i > 0 && i < this.length - 1) {
                    this.middlePositions.add(new Position(origin.getX() + i, origin.getY()));
                }
            } else if (this.direction.equals("West")) {
                allpos.add(new Position(origin.getX() - i, origin.getY()));
                if (i > 0 && i < this.length - 1) {
                    this.middlePositions.add(new Position(origin.getX() - i, origin.getY()));
                }
            }
        }
        return allpos;
    }

    public TETile getTile() {
        return tile;

    }
    public Position getExitPosition() { //first position outside of hallway
        if (this.direction.equals("North")) {
            return new Position(origin.getX(), origin.getY() + this.length);

        } else if (this.direction.equals("South")) {
            return new Position(origin.getX(), origin.getY() - this.length);

        } else if (this.direction.equals("East")) {
            return new Position(origin.getX() + this.length, origin.getY());

        } else {
            return new Position(origin.getX() - this.length, origin.getY());
        }

    }
    public Position getOrigin() {
        return this.origin;
    }
    public List<Position> getMiddlePositions() { //list of positions exlcuding origin and end
        List<Position> allpos = new ArrayList<>();
        for (int i = 0; i < this.length; i++) {
            if (this.direction.equals("North")) {
                if (i > 0 && i < this.length - 1) {
                    allpos.add(new Position(origin.getX(), origin.getY() + i));
                }
            } else if (this.direction.equals("South")) {
                if (i > 0 && i < this.length - 1) {
                    allpos.add(new Position(origin.getX(), origin.getY() - i));
                }
            } else if (this.direction.equals("East")) {
                if (i > 0 && i < this.length - 1) {
                    allpos.add(new Position(origin.getX() + i, origin.getY()));
                }
            } else if (this.direction.equals("West")) {
                if (i > 0 && i < this.length - 1) {
                    allpos.add(new Position(origin.getX() - i, origin.getY()));
                }
            }
        }
        return allpos;
    }
    public Integer getlength() {
        return this.length;
    }
}
