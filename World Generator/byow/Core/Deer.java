package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

public class Deer implements Serializable {
    private Position position;
    private World world;
    private TETile tile;


    public Deer(World world, Position position) {
        this.world = world;
        this.position = position;
        this.tile = TilesetAlt.DEER;
        world.putChar(position, tile);
    }

    public void get() {
        this.tile = TilesetAlt.FLOOR;
    }

    public Position getPosition() {
        return this.position;
    }
}
