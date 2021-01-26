package byow.lab12;
import byow.TileEngine.TETile;
import java.util.*;
import byow.Core.Position;

public class Hexagon {
    private TETile tile;
    private Position topLeft;
    private Position midLeft;
    private Position midRight;
    private int side;

    public Hexagon(TETile tile, Position topLeft) {
        this.tile = tile;
        this.topLeft = topLeft;
        this.side = side;
        this.midLeft = new Position(topLeft, -(side - 1), -(side -1));
        this.midRight =  new Position(midLeft, side - 1, 0);
    }
    public List<Position> getHexPositions() {
        List<Position> res = new ArrayList<>();
        int height = getHexHeight();
        for(int j = 0; j < height; j++) {
            int rowStart = getRowStart(j);
            int rowLength = getRowLength(j);
            for (int i = 0; i < rowLength; i++) {
                res.add(new Position(rowStart + i, topLeft.getY() - j));
            }

        }
        return res;

    }

    private int getHexHeight() {
        return side * 2;
    }

    private int getRowLength(int j) {
        if (j < this.side) {
            return this.side + j * 2;
        } else {
            return this.side + (this.side - 1) * 2 - (j % this.side) * 2;
        }

    }

    private int getRowStart(int j) {
        if (j < this.side) {
            return this.topLeft.getX() - j;


        } else {
            return this.side;
        }
    }
}
