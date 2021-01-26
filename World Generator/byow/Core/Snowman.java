package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

public class Snowman implements Serializable {
    private Position position;
    private World world;
    private TETile tile;


    public Snowman(World world, Position position) {
        this.world = world;
        this.position = position;
        this.tile = TilesetAlt.SNOWMAN;
        world.putChar(position, tile);
    }

    public void get() {
        this.tile = TilesetAlt.FLOOR;
    }

    public Position getPosition() {
        return this.position;
    }
}
