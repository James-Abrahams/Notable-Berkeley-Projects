package byow.Core;

import byow.TileEngine.TETile;

import java.awt.*;
import java.io.Serializable;

public class Ring implements Serializable {
    private Position position;
    private World world;
    private TETile tile;


    public Ring(World world, Position position) {
        this.world = world;
        this.position = position;
        this.tile = TilesetAlt.RING;
        world.putChar(position, tile);
    }

    public Position getPosition() {
        return this.position;
    }

}
