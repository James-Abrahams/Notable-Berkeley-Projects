package byow.Core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position(Position ref, int xOff, int yoff) {
        this(ref.getX() + xOff, ref.getY() + yoff);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }


//    @Marsellus Wallace
//    @Stack Overflow hashcode for point
//    https://stackoverflow.com/questions/9135759/java-hashcode-for-a-point-class
    @Override
    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        return result;
    }

    //    @Marsellus Wallace
    //    Stack Overflow equals for point
    //    https://stackoverflow.com/questions/9135759/java-hashcode-for-a-point-class
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return (this.getX() == ((Position) o).getX() && (this.y == ((Position) o).getY()));
    }

    public List<Position>  adjacentPositions() {
        List<Position> posList = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                posList.add(new Position((this.x + i), this.y + j));
            }
        }
        return posList;
    }

    public Position move(String direction) {
        Position position;
        switch (direction) {
            case "up":
                this.y += 1;
                return this;
            case "down":
                this.y -= 1;
                return this;
            case "left":
                this.x -= 1;
                return this;
            case "right":
                this.x += 1;
                return this;
            default:
                return this;
        }
    }

    public Position tryMove(String direction) {
        Position temp;
        if (direction == "up") {
            temp = new Position(x, y + 1);
        } else if (direction == "down") {
            temp = new Position(x, y - 1);
        } else if (direction == "left") {
            temp = new Position(x - 1, y);
        } else {
            temp = new Position(x + 1, y);
        }
        return temp;
    }

}
